package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import root.dao.ServiceDAO;
import root.model.Service;
import root.service.ServiceService;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Override
    public Boolean add(Service service) {
        return serviceDAO.add(service);
    }

    @Override
    public Boolean del(String name) {
        return serviceDAO.del(name);
    }

    @Override
    public Boolean mod(Service service) {
        return serviceDAO.mod(service);
    }

    @Override
    public List<Service> get(String server) {
        return serviceDAO.get();
    }
}
