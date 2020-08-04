package root.dao;

import root.model.Service;

import java.util.List;

public interface ServiceDAO {

    Boolean add(Service service);

    Boolean del(String name);

    Boolean mod(Service service);

    List<Service> getByServer(String server);

    Service get(String name);

}
