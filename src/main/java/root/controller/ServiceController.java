package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import root.model.Group;
import root.model.Service;
import root.service.ServiceService;

import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(method = RequestMethod.POST, path = "/service")
    @ResponseBody
    public Boolean add(@RequestBody Service service) {
        return serviceService.add(service);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/service")
    @ResponseBody
    public Boolean del(String name) {
        return serviceService.del(name);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/service")
    @ResponseBody
    public Boolean mod(@RequestBody Service service) {
        return serviceService.mod(service);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/services")
    @ResponseBody
    public List<Service> get() {
        return serviceService.get(null);
    }

}