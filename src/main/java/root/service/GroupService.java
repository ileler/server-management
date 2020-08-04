package root.service;

import root.model.Group;

import java.util.List;

public interface GroupService {

    Boolean add(Group env);

    Boolean del(String name, boolean forced);

    Boolean mod(Group env);

    List<Group> get();

}
