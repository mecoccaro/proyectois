package ucab.ingsw.proyecto.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Data
@ToString
public class AccountsResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String password;
    private List<Long> friends = new ArrayList<>();

}
