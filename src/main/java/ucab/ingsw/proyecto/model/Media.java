package ucab.ingsw.proyecto.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@ToString
public class Media implements Serializable {

    @Id
    private long id;
    private long albumId;
    private String url;
    private String link;
    private String type;

}