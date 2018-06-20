package ucab.ingsw.proyecto.service.instagramData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video implements Serializable {
    private Resolution standard_resolution;
}