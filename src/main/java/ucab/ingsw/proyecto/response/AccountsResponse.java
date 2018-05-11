package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountsResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
