package tw.justin.springbootvue.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_role_menu")
@Data
public class RoleMenu {

    public Integer roleId;
    public Integer menuId;


}
