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
public class Album implements Serializable {

    @Id
    private long id;
    private long accountId;
    private String title;
    private String description;
    private List<Long> media = new ArrayList<>();
}
