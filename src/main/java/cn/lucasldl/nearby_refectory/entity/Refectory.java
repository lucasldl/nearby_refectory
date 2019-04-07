package cn.lucasldl.nearby_refectory.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refectory")
@Data
@Accessors(chain = true)
public class Refectory {
    @Id
    private Long id;

    private String title;

    private String address;

    private String phone;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location;

    private String introduce;

}
