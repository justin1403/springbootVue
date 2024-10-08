package tw.justin.springbootvue.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "sys_user")
public class User {

    @TableId(type= IdType.AUTO)
    private Integer id;

    @Alias("用戶名")
    private String username;

    @Alias("密碼")
    private String password;

    @Alias("暱稱")
    private String nickname;

    @Alias("郵箱")
    private String email;

    @Alias("電話")
    private String phone;

    @Alias("地址")
    private String address;

    @Alias("頭像")
    private String avatarUrl;

    @Alias("創建時間")
    private Date createTime;

    @Alias("角色")
    private String role;

    @TableField(exist = false)
    private List<Course> courses;


    @TableField(exist = false)
    private List<Course> stuCourses;

}
