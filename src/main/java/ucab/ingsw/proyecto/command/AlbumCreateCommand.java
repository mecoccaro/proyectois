package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@ToString
public class AlbumCreateCommand implements Serializable {


    @NotNull(message = "Se requiere un usuario")
    @NotEmpty(message = "Se requiere un usuario")
    private String accountId;


    @NotNull(message = "Se requiere un titulo para el album")
    @NotEmpty(message = "Se requiere un titulo para el album")
    private String title;

    @NotNull(message = "Se requiere una descripcion del album")
    @NotEmpty(message = "Se requiere una descripcion del album")
    private String description;
}
