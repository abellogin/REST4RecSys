package es.uam.eps.rest.resource;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.uam.eps.ir.ranksys.core.Recommendation;
import es.uam.eps.ir.ranksys.fast.index.FastItemIndex;
import es.uam.eps.ir.ranksys.fast.index.FastUserIndex;
import es.uam.eps.ir.ranksys.fast.index.SimpleFastItemIndex;
import es.uam.eps.ir.ranksys.fast.index.SimpleFastUserIndex;
import es.uam.eps.ir.ranksys.fast.preference.FastPreferenceData;
import es.uam.eps.ir.ranksys.fast.preference.SimpleFastPreferenceData;
import es.uam.eps.ir.ranksys.mf.Factorization;
import es.uam.eps.ir.ranksys.mf.als.HKVFactorizer;
import es.uam.eps.ir.ranksys.mf.rec.MFRecommender;
import es.uam.eps.ir.ranksys.nn.item.ItemNeighborhoodRecommender;
import es.uam.eps.ir.ranksys.nn.item.neighborhood.CachedItemNeighborhood;
import es.uam.eps.ir.ranksys.nn.item.neighborhood.ItemNeighborhood;
import es.uam.eps.ir.ranksys.nn.item.neighborhood.TopKItemNeighborhood;
import es.uam.eps.ir.ranksys.nn.item.sim.ItemSimilarity;
import es.uam.eps.ir.ranksys.nn.item.sim.VectorCosineItemSimilarity;
import es.uam.eps.ir.ranksys.nn.user.UserNeighborhoodRecommender;
import es.uam.eps.ir.ranksys.nn.user.neighborhood.TopKUserNeighborhood;
import es.uam.eps.ir.ranksys.nn.user.neighborhood.UserNeighborhood;
import es.uam.eps.ir.ranksys.nn.user.sim.UserSimilarity;
import es.uam.eps.ir.ranksys.nn.user.sim.VectorCosineUserSimilarity;
import es.uam.eps.ir.ranksys.rec.Recommender;
import es.uam.eps.ir.ranksys.rec.fast.basic.PopularityRecommender;
import es.uam.eps.ir.ranksys.rec.fast.basic.RandomRecommender;
import es.uam.eps.rest.model.Event;
import es.uam.eps.rest.model.Item;
import es.uam.eps.rest.model.ModelStorageIF;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import javax.ws.rs.QueryParam;
import org.jooq.lambda.tuple.Tuple3;
import org.ranksys.core.util.tuples.Tuple2od;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
@Path("/v1/")
public class APIRankSysResource extends APIResource {

    private Recommender<String, String> rec;

    public APIRankSysResource(ModelStorageIF<String, String, String> model) {
        super(model);
    }

    @GET
    @Path("/user/get/{uid}/recommendations")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<Item> getUserRecommendations(@PathParam("uid") String userId) {
        List<Item> recs = new ArrayList<>();
        if (rec == null) {
            train(null);
        }
        Recommendation<String, String> r = rec.getRecommendation(userId);
        for (Tuple2od<String> t : r.getItems()) {
            Item ii = model.getItem(t.v1());
            Item i = new Item();
            i.setIid(t.v1());
            i.setContent(ii.getContent());
            i.setFeatures(ii.getFeatures());
            i.setName(ii.getName());
            i.setValue(t.v2());
            recs.add(i);
        }
        return recs;
    }

    @GET
    @Path("/train")
    @Override
    public void train(@QueryParam("method") String recMethod) {
        // create models
        List<String> listUsers = model.getAllUsers().stream().map(u -> u.getUid()).collect(Collectors.toList());
        List<String> listItems = model.getAllItems().stream().map(i -> i.getIid()).collect(Collectors.toList());
        FastUserIndex<String> userIndex = SimpleFastUserIndex.load(listUsers.stream());
        FastItemIndex<String> itemIndex = SimpleFastItemIndex.load(listItems.stream());
        List<Tuple3<String, String, Double>> tuples = new ArrayList<>();
        for (Event e : model.getAllEvents()) {
            tuples.add(new Tuple3<>(e.getUid(), e.getIid(), e.getValue()));
        }
        FastPreferenceData<String, String> trainData = SimpleFastPreferenceData.load(tuples.stream(), userIndex, itemIndex);
        // do train
        if ((recMethod == null) || (recMethod.isEmpty())) {
            recMethod = "pop";
        }
        switch (recMethod) {
            case "rnd": {
                rec = new RandomRecommender<>(trainData, trainData);
            }
            break;
            case "pop": {
                rec = new PopularityRecommender<>(trainData);
            }
            break;
            case "ibcos10": {
                int k = 10;
                double alpha = 0.5;
                int q = 1;
                ItemSimilarity<String> sim = new VectorCosineItemSimilarity<>(trainData, alpha, true);
                ItemNeighborhood<String> neighborhood = new TopKItemNeighborhood<>(sim, k);
                neighborhood = new CachedItemNeighborhood<>(neighborhood);

                rec = new ItemNeighborhoodRecommender<>(trainData, neighborhood, q);
            }
            break;
            case "ubcos10": {
                int k = 10;
                double alpha = 0.5;
                int q = 1;
                UserSimilarity<String> sim = new VectorCosineUserSimilarity<>(trainData, alpha, true);
                UserNeighborhood<String> neighborhood = new TopKUserNeighborhood<>(sim, k);

                rec = new UserNeighborhoodRecommender<>(trainData, neighborhood, q);
            }
            break;
            case "hkv": {
                int k = 50;
                double lambda = 0.1;
                double alpha = 1.0;
                DoubleUnaryOperator confidence = x -> 1 + alpha * x;
                int numIter = 20;

                Factorization<String, String> factorization = new HKVFactorizer<String, String>(lambda, confidence, numIter).factorize(k, trainData);

                rec = new MFRecommender<>(userIndex, itemIndex, factorization);
            }
            break;
            default:
                throw new AssertionError();
        }
    }

}
