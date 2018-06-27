package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SpotifyListResponse {

    private List<SpotifyResponse> tracks;
}
