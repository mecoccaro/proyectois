package ucab.ingsw.proyecto.service.SpotifyData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items implements Serializable{

    private  Album album;
    private List<Artist> artists;
    private Urls external_urls;
    private String name;
}

