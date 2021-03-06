package root.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import root.dao.ServiceDAO;
import root.model.Service;
import root.util.ObjectDB;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ServiceDAOImpl implements ServiceDAO {

    private ObjectDB<Service> objectDB;

    public ServiceDAOImpl() {
        objectDB = new ObjectDB<>("service");
    }

    @Override
    public Boolean add(Service service) {
        if (get(service.getName()) != null) return false;
        objectDB.addData(service);
        return true;
    }

    @Override
    public Boolean del(String name) {
        if (StringUtils.isEmpty(name)) return false;
        Service service = get(name);
        if (service != null) objectDB.delData(service);
        return true;
    }

    @Override
    public Boolean mod(Service service) {
        if (service == null) return false;
        del(service.getName());
        objectDB.addData(service);
        return true;
    }

    @Override
    public List<Service> getByServer(String server) {
        List<Service> list = objectDB.getData();
        if (!StringUtils.isEmpty(server)) {
            return list.stream().filter(data -> data.getServer().equals(server)).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Service get(String name) {
        if (StringUtils.isEmpty(name)) return null;
        List<Service> services = getByServer(null);
        if (services == null) return null;
        for (Service service : services) {
            if (service != null && name.equals(service.getName())) {
                return service;
            }
        }
        return null;
    }

}
