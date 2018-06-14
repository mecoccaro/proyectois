package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class AlbumCreateCommand implements Serializable {
    @NotNull(message = "Se requiere un usuario")
    @NotEmpty(message = "Se requiere un usuario")
    private String userId;

    @NotNull(message = "Se requiere un titulo para el album")
    @NotEmpty(message = "Se requiere un titulo para el album")
    private String titulo;

    @NotNull(message = "Se requiere una descripcion del album")
    @NotEmpty(message = "Se requiere una descripcion del album")
    private String description;
}
