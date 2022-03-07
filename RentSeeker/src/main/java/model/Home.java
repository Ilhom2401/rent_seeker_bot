package model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import model.base.BaseModel;
import util.enums.District;
import util.enums.HomeStatus;
import util.enums.HomeType;
import util.enums.Region;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Home extends BaseModel {
    UUID ownerId;
    HomeStatus status;
    HomeType homeType;
    Region region;
    District district;
    String address;
    int numberOfRooms;
    double area;
    double price;
    String description;
    long interests;
    String mapUrl;
    String fileId;
    int fileSize;
    boolean isBan;
    long likes;

    {
        this.isActive=false;
    }
}
