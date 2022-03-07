package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.base.BaseModel;
import util.enums.BotState;
import util.enums.Language;
import util.enums.Role;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseModel {
    String name;
    String phoneNumber;
    String username;
    String chatId;
    Language language;
    BotState state;
    Role role;
    boolean isAdmin=false;
}
