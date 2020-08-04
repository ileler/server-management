package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import root.dao.GroupDAO;
import root.model.Group;
import root.model.Server;
import root.service.GroupService;
import root.service.ServerService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private ServerService serverService;

    @Override
    public Boolean add(Group group) {
        return groupDAO.add(group);
    }

    @Override
    public Boolean del(String name, boolean forced) {
        if (forced) {
            serverService.del(null, name, true);
        } else {
            if (!CollectionUtils.isEmpty(serverService.get(name))) return false;
        }
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
