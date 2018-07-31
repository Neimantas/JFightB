package main.Models.BL;

// TODO this will have more fields in the future - items, levels, skills ...
public class User {
    public String name;
    public long id;
    public String uuid;

    public User(String _name, long _id, String _uuid) {
        name = _name;
        id = _id;
        uuid = _uuid;
    }
}
