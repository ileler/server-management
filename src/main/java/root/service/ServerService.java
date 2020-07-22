package root.service;

import root.model.Server;
import root.model.Streams;

import java.util.List;

public interface ServerService {

    Boolean add(Server server);

    Boolean del(String ip);

    Boolean mod(Server server);

    List<Server> get(String group);

    String valid(String ip);

    Streams services(String ip);

    Streams kill(String ip, Long pid);

    Streams operLogs(String ip);

    Streams loginLogs(String ip);

}
