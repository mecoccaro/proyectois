package ucab.ingsw.proyecto.service.SpotifyData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album implements Serializable {
    private String name;
    private List<Image> images;
}
