package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.base.BaseModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Interest extends BaseModel{
    UUID homeId;
    UUID userId;
    boolean visible;

    {
        visible=false;
        isActive=false;
    }
}
