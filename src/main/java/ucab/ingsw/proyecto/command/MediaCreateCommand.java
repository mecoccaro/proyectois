package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class MediaCreateCommand {

    @NotNull(message = "Se requiere tipo.")
    @NotEmpty(message = "Se requiere tipo.")
    private String type;

    @NotNull(message = "Se requiere un album")
    private Long albumId;

    @NotNull(message = "Se requiere url.")
    @NotEmpty(message = "Se requiere url.")
    private String url;

    @NotNull(message = "Se requiere enlace.")
    @NotEmpty(message = "Se requiere enlace.")
    private String link;

}
