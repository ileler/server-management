package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import root.model.Group;
import root.model.Server;
import root.service.ServerService;

import java.util.List;

@RestController
public class ServerController {

    @Autowired
    private ServerService serverService;

    @RequestMapping(method = RequestMethod.POST, path = "/server")
    @ResponseBody
    public Boolean add(@RequestBody Server server) {
        return serverService.add(server);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/server")
    @ResponseBody
    public Boolean del(String name) {
        return serverService.del(name);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/server")
    @ResponseBody
    public Boolean mod(@RequestBody Server server) {
        return serverService.mod(server);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/servers")
    @ResponseBody
    public List<Server> get() {
        return serverService.get(null);
    }

}