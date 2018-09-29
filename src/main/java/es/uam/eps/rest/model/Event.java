package es.uam.eps.rest.model;

import java.io.Serializable;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class Event implements Serializable {

    public static final String RATING_TYPE = "rating";
    public static final String COUNT_TYPE = "count";

    private String eid;
    private String type;
    private String uid;
    private String iid;
    private double value;
    private long timestamp;

    public Event() {
    }

    public String getEid() {
        return eid;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public String getIid() {
        return iid;
    }

    public double getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimestamp(long l) {
        this.timestamp = l;
    }
}
