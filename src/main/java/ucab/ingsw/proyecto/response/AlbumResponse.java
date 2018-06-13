package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AlbumResponse {
    private long id;
    private String titulo;
    private String description;
    private List<Long> Media;
}
