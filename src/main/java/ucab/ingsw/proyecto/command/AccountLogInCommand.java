package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@ToString
public class AccountLogInCommand implements Serializable {

    @NotNull(message = "Se requiere un correo electronico")
    @NotEmpty(message = "Se requiere un correo electronico")
    @Size(min = Validation.EMAIL_MIN_SIZE, message = "El correo debe tener al menos 6 caracteres")
    @Email(message = "error.format.email")
    private String email;

    @NotNull(message = "Se requiere una contraseña")
    @NotEmpty(message = "Se requiere una contraseña")
    @Size(min = Validation.PASSWORD_MIN_SIZE, message = "La contraseña debe tener 6 caracteres o mas")
    private String password;

}

