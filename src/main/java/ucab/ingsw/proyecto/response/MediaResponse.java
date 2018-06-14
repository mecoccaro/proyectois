package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MediaResponse {
    private long id;
    private String url;
    private int type;
}
