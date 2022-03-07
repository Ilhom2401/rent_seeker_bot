package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.base.BaseModel;
import util.enums.*;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Search extends BaseModel {
    UUID userId;
    Region region;
    District district;
    HomeStatus status;
    HomeType homeType;
    int numberOfRooms=-1;
    double minPrice=-1;
    double maxPrice=-1;
}
