package alpha.common.base.model;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * Created by chengmo on 2018/5/3.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "排序规则", description = "按指定字段进行升序或降序排列")
public class SortOrder {

    @JSONField(ordinal = 1)
    @ApiModelProperty(value = "排序字段属性名称", example = "id")
    private String property;

    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "排序方向：ASC(升序)；DESC(降序)", example = "DESC")
    private Sort.Direction direction;
}
