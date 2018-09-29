package es.uam.eps.rest.client.views;

import es.uam.eps.rest.model.Statistics;

import io.dropwizard.views.View;

/**
 *
 * @author Alejandro Bellogin
 */
public class StatisticsView extends View {

    private final Statistics stats;

    public StatisticsView(Statistics stats) {
        super("statistics.ftl");
        this.stats = stats;
    }

    public Statistics getStats() {
        return stats;
    }

}
