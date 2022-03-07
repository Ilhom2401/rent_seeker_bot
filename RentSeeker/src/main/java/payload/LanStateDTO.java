package payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import util.enums.BotState;
import util.enums.Language;
import util.enums.Role;


@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LanStateDTO {
    Language language;
    BotState state;
    Role role;
    boolean isAdmin;
}
