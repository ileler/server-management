package root.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Server implements Serializable {

    private static final long serialVersionUID = -2114174612355982255L;

    private String name;

    private String group;

    private String ip;

    private Integer port;

    private String username;

    private String password;

    private String desc;

    public String getId() {
        if (ip == null && username == null) return null;
        return username + "@" + ip + ":" + getPort();
    }

}
