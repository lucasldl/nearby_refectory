package cn.lucasldl.nearby_refectory.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "refectory")
@Data
@Accessors(chain = true)
@ApiModel("餐厅信息实体")
public class Refectory {
    @ApiModelProperty(name = "id", value = "餐厅唯一标识", dataType = "long", example = "201803131245")
    @Id
    private Long id;

    @ApiModelProperty(name = "title", value = "餐厅名", dataType = "String")
    private String title;

    @ApiModelProperty(name = "address", value = "详细地址", dataType = "String")
    private String address;

    @ApiModelProperty(name = "phone", value = "联系电话", dataType = "String")
    private String phone;

    @ApiModelProperty(name = "location", value = "坐标", dataType = "double[]",example = "{11.11,22.133}")
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location;

    @ApiModelProperty(name = "introduce", value = "简介", dataType = "String")
    private String introduce;

}
