package tw.justin.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author justin
 * @since 2024-04-01
 */
@Getter
@Setter
  @ApiModel(value = "Course对象", description = "")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("課程名稱")
      private String name;

      @ApiModelProperty("學分")
      private Integer score;

      @ApiModelProperty("上課時間")
      private String times;

      @ApiModelProperty("是否開課")
      private Integer state;

      @ApiModelProperty("授課老師 id")
      private Integer teacherId;

      @TableField(exist = false)
      private String teacher;
}
