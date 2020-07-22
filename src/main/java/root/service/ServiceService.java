package root.service;

import root.model.Service;

import java.util.List;

public interface ServiceService {

    Boolean add(Service service);

    Boolean del(String name);

    Boolean mod(Service service);

    List<Service> get(String server);

}
