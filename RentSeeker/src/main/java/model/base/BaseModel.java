package model.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseModel {
     UUID id;
     protected boolean isActive;
     String createdDate;

    public BaseModel() {
        this.id = UUID.randomUUID();
        this.isActive=true;
        this.createdDate = LocalDateTime.now().toString();
    }
}
