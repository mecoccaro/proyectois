package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class InstagramVideoResponse extends InstagramResponse{
    private String videoUrl;
}
