package ucab.ingsw.proyecto.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class Accounts implements Serializable {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String uuid = UUID.randomUUID().toString();
}