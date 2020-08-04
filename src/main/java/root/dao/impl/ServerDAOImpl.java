package root.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import root.dao.ServerDAO;
import root.model.Server;
import root.model.Streams;
import root.util.JschUtil;
import root.util.ObjectDB;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServerDAOImpl implements ServerDAO {

    private ObjectDB<Server> objectDB;

    public ServerDAOImpl() {
        objectDB = new ObjectDB<>("server");
    }

    @Override
    public Boolean add(Server server) {
        if (get(server.getName()) != null) return false;
        objectDB.addData(server);
        return true;
    }

    @Override
    public Boolean del(String name) {
        if (StringUtils.isEmpty(name)) return false;
        Server server = get(name);
        if (server != null) objectDB.delData(server);
        return true;
    }

    @Override
    public Boolean mod(Server server) {
        if (server == null) return false;
        del(server.getIp());
        objectDB.addData(server);
        return true;
    }

    @Override
    public List<Server> getByGroup(String group) {
        List<Server> list = objectDB.getData();
        if (!StringUtils.isEmpty(group)) {
            return list.stream().filter(data -> data.getGroup().equals(group)).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Server get(String name) {
        if (StringUtils.isEmpty(name)) return null;
        List<Server> servers = getByGroup(null);
        if (servers == null) return null;
        for (Server server : servers) {
            if (server != null && name.equals(server.getName())) {
                return server;
            }
        }
        return null;
    }

    @Override
    public String valid(String name) {
        Server server = get(name);
        if (server == null) return null;
        return JschUtil.connect(server);
    }

    @Override
    public Streams services(String name) {
        Server server = get(name);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "bash --login -c 'jps -mlv'");
    }

    @Override
    public Streams kill(String name, Long pid) {
        Server server = get(name);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "kill -9 " + pid);
    }

    @Override
    public Streams operLogs(String name) {
        Server server = get(name);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "cd /var/log && cat syslog");
    }

    @Override
    public Streams loginLogs(String name) {
        Server server = get(name);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "last -50");
    }

}
