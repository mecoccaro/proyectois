package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@ToString
public class AccountSignUpCommand implements Serializable {

    @NotNull(message = "Se requiere un correo electronico")
    @NotEmpty(message = "Se requiere un correo electronico")
    @Size(min = Validation.EMAIL_MIN_SIZE, message = "El correo debe tener al menos 6 caracteres")
    @Email(message = "error.format.email")
    private String email;

    @NotNull(message = "Se requiere una contraseña")
    @NotEmpty(message = "Se requiere una contraseña")
    @Size(min = Validation.PASSWORD_MIN_SIZE, message = "La contraseña debe tener 6 caracteres o mas")
    private String password;

    @NotNull(message = "Se requiere una contraseña de confirmacion")
    @NotEmpty(message = "Se requiere una contraseña de confirmacion")
    @Size(min = Validation.PASSWORD_MIN_SIZE, message = "La contraseña debe 6 caracteres o mas ")
    private String confirmationPassword;

    @Size(max = Validation.FIRST_LAST_NAME_SIZE, message = "El nombre debe tener menos de 50 caracteres")
    @Pattern(regexp = Validation.FIRST_LAST_NAME_REGEX, message = "Caracteres invalidos")
    private String firstName;

    @Size(max = Validation.FIRST_LAST_NAME_SIZE, message = "El apellido debe tener menos de 50 caracteres")
    @Pattern(regexp = Validation.FIRST_LAST_NAME_REGEX, message = "Caracteres invalidos")
    private String lastName;
}
