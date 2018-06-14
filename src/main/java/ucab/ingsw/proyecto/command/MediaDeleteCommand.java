package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class MediaDeleteCommand implements Serializable {
    @NotNull(message = "Se requiere un usuario")
    @NotEmpty(message = "Se requiere un usuario")
    private String accountId;

    @NotNull(message = "Se requiere un album")
    @NotEmpty(message = "Se requiere un album")
    private String albumId;

    @NotNull(message = "Se requiere un media")
    @NotEmpty(message = "Se requiere un media")
    private String mediaId;
}