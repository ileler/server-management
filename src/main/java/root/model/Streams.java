package root.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Streams {
    private Integer exitCode = 0;

    private String out = "";

    private String err = "";
}
