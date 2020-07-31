package root.dao;

import root.model.Server;
import root.model.Streams;

import java.util.List;

public interface ServerDAO {

    Boolean add(Server server);

    Boolean del(String name);

    Boolean mod(Server server);

    List<Server> get();

    Server get(String name);

    String valid(String name);

    Streams services(String name);

    Streams kill(String name, Long pid);

    Streams operLogs(String name);

    Streams loginLogs(String name);

}
