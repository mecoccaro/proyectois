package ucab.ingsw.proyecto.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Data
@ToString
public class Accounts implements Serializable {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String password;
    private String profilePicture;
    private String uuid = UUID.randomUUID().toString();
    private List<Long> friends = new ArrayList<>();
    private List<Long> albums = new ArrayList<>();
}