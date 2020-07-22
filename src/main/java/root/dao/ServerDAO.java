package root.dao;

import root.model.Server;
import root.model.Streams;

import java.util.List;

public interface ServerDAO {

    Boolean add(Server server);

    Boolean del(String ip);

    Boolean mod(Server server);

    List<Server> get();

    Server get(String ip);

    String valid(String ip);

    Streams services(String ip);

    Streams kill(String ip, Long pid);

    Streams operLogs(String ip);

    Streams loginLogs(String ip);

}
