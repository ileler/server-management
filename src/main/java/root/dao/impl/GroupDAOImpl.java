package root.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import root.dao.GroupDAO;
import root.model.Group;
import root.util.ObjectDB;

import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {

    private ObjectDB<Group> objectDB;

    public GroupDAOImpl() {
        objectDB = new ObjectDB<>("group");
    }

    @Override
    public Boolean add(Group env) {
        if (get(env.getName()) != null) return false;
        objectDB.addData(env);
        return true;
    }

    @Override
    public Boolean del(String name) {
        if (StringUtils.isEmpty(name)) return false;
        Group group = get(name);
        if (group != null) objectDB.delData(group);
        return true;
    }

    @Override
    public Boolean mod(Group group) {
        if (group == null) return false;
        del(group.getName());
        objectDB.addData(group);
        return true;
    }

    @Override
    public List<Group> get() {
        return objectDB.getData();
    }

    @Override
    public Group get(String name) {
        if (StringUtils.isEmpty(name)) return null;
        List<Group> groups = get();
        if (groups == null) return null;
        for (Group group : groups) {
            if (group != null && name.equals(group.getName())) {
                return group;
            }
        }
        return null;
    }

}
