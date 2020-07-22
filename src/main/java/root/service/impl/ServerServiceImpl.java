package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.dao.ServerDAO;
import root.model.Server;
import root.model.Streams;
import root.service.ServerService;

import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ServerDAO serverDAO;

    @Override
    public Boolean add(Server server) {
        return serverDAO.add(server);
    }

    @Override
    public Boolean del(String ip) {
        return serverDAO.del(ip);
    }

    @Override
    public Boolean mod(Server server) {
        return serverDAO.mod(server);
    }

    @Override
    public List<Server> get(String group) {
        return serverDAO.get();
    }

    @Override
    public String valid(String ip) {
        return serverDAO.valid(ip);
    }

    @Override
    public Streams services(String ip) {
        return serverDAO.services(ip);
    }

    @Override
    public Streams kill(String ip, Long pid) {
        return serverDAO.kill(ip, pid);
    }

    @Override
    public Streams operLogs(String ip) {
        return serverDAO.operLogs(ip);
    }

    @Override
    public Streams loginLogs(String ip) {
        return serverDAO.loginLogs(ip);
    }
}
