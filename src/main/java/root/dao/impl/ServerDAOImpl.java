package root.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import root.dao.ServerDAO;
import root.model.Server;
import root.model.Streams;
import root.util.JschUtil;
import root.util.ObjectDB;

import java.util.List;

@Repository
public class ServerDAOImpl implements ServerDAO {

    private ObjectDB<Server> objectDB;

    public ServerDAOImpl() {
        objectDB = new ObjectDB<>("server");
    }

    @Override
    public Boolean add(Server server) {
        objectDB.addData(server);
        return true;
    }

    @Override
    public Boolean del(String ip) {
        if (StringUtils.isEmpty(ip)) return false;
        Server server = get(ip);
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
    public List<Server> get() {
        return objectDB.getData();
    }

    @Override
    public Server get(String ip) {
        if (StringUtils.isEmpty(ip)) return null;
        List<Server> servers = get();
        if (servers == null) return null;
        for (Server server : servers) {
            if (server != null && ip.equals(server.getIp())) {
                return server;
            }
        }
        return null;
    }

    @Override
    public String valid(String ip) {
        Server server = get(ip);
        if (server == null) return null;
        return JschUtil.connect(server);
    }

    @Override
    public Streams services(String ip) {
        Server server = get(ip);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "bash --login -c 'jps -mlv'");
    }

    @Override
    public Streams kill(String ip, Long pid) {
        Server server = get(ip);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "kill -9 " + pid);
    }

    @Override
    public Streams operLogs(String ip) {
        Server server = get(ip);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "cd /var/log && cat syslog");
    }

    @Override
    public Streams loginLogs(String ip) {
        Server server = get(ip);
        if (server == null) return null;
        return JschUtil.executeCommand(server, "last -50");
    }

}
