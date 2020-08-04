package root.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public Boolean del(String name, String server) {
        if (!StringUtils.isEmpty(server)) {
            List<Service> services = get(server);
            for (Service service : services) {
                serviceDAO.del(service.getName());
            }
            return true;
        }
        return serviceDAO.del(name);
    }

    @Override
    public Boolean mod(Service service) {
        return serviceDAO.mod(service);
    }

    @Override
    public List<Service> get(String server) {
        return serviceDAO.getByServer(server);
    }
}
