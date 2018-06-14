package ucab.ingsw.proyecto.command;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.nio.channels.SeekableByteChannel;

@Data
@ToString
public class AccountAddFriendCommand implements Serializable {

    @NotNull(message = "Se requiere Id")
    private Long friendId;

}