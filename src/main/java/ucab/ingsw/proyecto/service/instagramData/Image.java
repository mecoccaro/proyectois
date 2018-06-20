package ucab.ingsw.proyecto.service.instagramData;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Serializable {
    private Resolution standard_resolution;
}
