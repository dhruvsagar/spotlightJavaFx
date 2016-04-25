package modell;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Dhruv Sagar on 21-Apr-16.
 */
public class Person {
    private String name;
    private String major;
    private String username;
    private String password;

    public Person(String name, String major, String username, String password) {
        this.name = name;
        this.major = major;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + major + username;
    }


}
