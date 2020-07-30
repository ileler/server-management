package root.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Service implements Serializable {

    private static final long serialVersionUID = 5320175101570573623L;

    private String name;

    private String server;

    private String desc;

    private String type;

    private String user;

}
