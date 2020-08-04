package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import root.dao.ServerDAO;
import root.model.Server;
import root.model.Streams;
import root.service.ServerService;
import root.service.ServiceService;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerDAO serverDAO;

    @Autowired
    private ServiceService serviceService;

    @Override
    public Boolean add(Server server) {
        return serverDAO.add(server);
    }

    @Override
    public Boolean del(String name, String group, boolean forced) {
        if (!StringUtils.isEmpty(group)) {
            List<Server> servers = get(group);
            for (Server server : servers) {
                if (forced) {
                    serviceService.del(null, server.getName());
                } else {
                    if (!CollectionUtils.isEmpty(serviceService.get(name))) continue;
                }
                serverDAO.del(server.getName());
            }
            return true;
        }
        if (forced) {
            serviceService.del(null, name);
        } else {
            if (!CollectionUtils.isEmpty(serviceService.get(name))) return false;
        }
        return serverDAO.del(name);
    }

    @Override
    public Boolean mod(Server server) {
        return serverDAO.mod(server);
    }

    @Override
    public List<Server> get(String group) {
        return serverDAO.getByGroup(group);
    }

    @Override
    public String valid(String name) {
        return serverDAO.valid(name);
    }

    @Override
    public Streams services(String name) {
        return serverDAO.services(name);
    }

    @Override
    public Streams kill(String name, Long pid) {
        return serverDAO.kill(name, pid);
    }

    @Override
    public Streams operLogs(String name) {
        return serverDAO.operLogs(name);
    }

    @Override
    public Streams loginLogs(String name) {
        return serverDAO.loginLogs(name);
    }
}
