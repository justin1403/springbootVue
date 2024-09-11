package tw.justin.springbootvue.controller.dto;


import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tw.justin.springbootvue.entity.Menu;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    @Alias("暱稱")
    private String nickname;
    @Alias("頭像")
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
