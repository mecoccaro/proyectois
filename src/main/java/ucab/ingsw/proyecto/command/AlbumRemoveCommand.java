package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class AlbumRemoveCommand implements Serializable {
    @NotNull(message = "Se requiere un usuario")
    @NotEmpty(message = "Se requiere un usuario")
    private String userId;

    @NotNull(message = "Se requiere un album")
    @NotEmpty(message = "Se requiere un album")
    private String albumId;
}
