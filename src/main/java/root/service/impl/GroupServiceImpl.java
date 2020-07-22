package root.service.impl;

import root.dao.GroupDAO;
import root.model.Group;
import root.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public Boolean add(Group group) {
        return groupDAO.add(group);
    }

    @Override
    public Boolean del(String name) {
        return groupDAO.del(name);
    }

    @Override
    public Boolean mod(Group env) {
        return groupDAO.mod(env);
    }

    @Override
    public List<Group> get() {
        return groupDAO.get();
    }

}
