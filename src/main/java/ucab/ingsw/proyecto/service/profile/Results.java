package ucab.ingsw.proyecto.service.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Results implements Serializable {
    private Picture picture;
}
