package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Data
@ToString
public class AlbumResponse {
    private long id;
    private long userId;
    private String title;
    private String description;
    private List<Long> media = new ArrayList<>();
}
