package modell;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dhruv Sagar on 21-Apr-16.
 */
public class DataBase {
    private Map<String, Person> users;

    public DataBase() {
        users = new HashMap<String, Person>();
    }

    public void addPerson(Person person, String id) {
        users.put(id, person);
    }

    public Person getPerson(String id) {
        return users.get(id);
    }

    public Map getUsers() {
        return users;
    }

    @Override
    public String toString() {
        String output = null;
        for (Person person : users.values()) {
            output = output + person.toString() + "\n";
        }
        return output;
    }

}
