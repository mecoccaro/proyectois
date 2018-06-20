package ucab.ingsw.proyecto.service.instagramData;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstagramList  implements Serializable{

    private List<Values> data;

}
