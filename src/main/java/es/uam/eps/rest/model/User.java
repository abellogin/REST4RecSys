package es.uam.eps.rest.model;

import java.io.Serializable;

/**
 *
 * @author Ivan Garcia
 * @author Alejandro Bellogin
 */
public class User implements Serializable {

    private String uid;
    private String age;
    private String email;
    private String name;
    private String location;

    public User() {
        this("", "", "", "", "");
    }

    public User(String uid, String age, String email, String name, String location) {
        this.uid = uid;
        this.age = age;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
