package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Data
@ToString
public class MediaResponse {
    private long id;
    private long albumId;
    private int type;
    private String url;
    private String link;
}
