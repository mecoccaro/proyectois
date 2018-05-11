package ucab.ingsw.proyecto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AccountResponse {
    private String id;
    private String name;
    private String lastName;
    private String email;
}

