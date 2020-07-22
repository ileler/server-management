package root.service;

import root.model.Group;

import java.util.List;

public interface GroupService {

    Boolean add(Group env);

    Boolean del(String name);

    Boolean mod(Group env);

    List<Group> get();

}
