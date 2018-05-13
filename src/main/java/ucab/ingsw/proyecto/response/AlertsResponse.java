package ucab.ingsw.proyecto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class AlertsResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String message;

}
