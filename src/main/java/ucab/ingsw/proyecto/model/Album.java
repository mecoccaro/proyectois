package ucab.ingsw.proyecto.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Album implements Serializable{
    @Id
    private long id;
    private long userId;
    private String titulo;
    private String description;
    private List<Long> media = new ArrayList<>();
}