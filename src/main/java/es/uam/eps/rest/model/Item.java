package es.uam.eps.rest.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class Item implements Serializable {

    private String iid;
    private String name;
    private String content;
    private Set<String> features;
    private double value = 0.0;
    private int count = 0;
    private int numValoraciones = 0;

    public Item() {
        this("", "", "");
        this.features = new HashSet<>();
    }

    public Item(String iid, String name, String content) {
        this.iid = iid;
        this.name = name;
        this.content = content;
    }

    public String getIid() {
        return iid;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public double getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getNumValoraciones() {
        return numValoraciones;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNumValoraciones(int numValoraciones) {
        this.numValoraciones = numValoraciones;
    }
}
