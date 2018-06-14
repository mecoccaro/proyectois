package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class MediaCreateCommand {

    @NotNull(message = "Se requiere un album")
    private Long albumId;

    @NotNull(message = "Se requiere un url")
    @NotEmpty(message = "Se requiere un url")
    private String url;

    private String type;
}
