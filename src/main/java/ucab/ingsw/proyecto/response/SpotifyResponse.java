package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SpotifyResponse {

    private String name;
    private List<String> artists;
    private String albumImageUrl;
    private String url;
}
