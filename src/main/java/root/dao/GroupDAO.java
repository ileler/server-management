package root.dao;

import root.model.Group;

import java.util.List;

public interface GroupDAO {

    Boolean add(Group env);

    Boolean del(String name);

    Boolean mod(Group env);

    List<Group> get();

    Group get(String name);

}
