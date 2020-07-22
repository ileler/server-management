package root.controller;

import root.model.Group;
import root.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService envService;

    @RequestMapping(method = RequestMethod.POST, path = "/group")
    @ResponseBody
    public Boolean add(@RequestBody Group group) {
        return envService.add(group);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/group")
    @ResponseBody
    public Boolean del(String name) {
        return envService.del(name);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/group")
    @ResponseBody
    public Boolean mod(@RequestBody Group group) {
        return envService.mod(group);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    @ResponseBody
    public List<Group> get() {
        return envService.get();
    }

}