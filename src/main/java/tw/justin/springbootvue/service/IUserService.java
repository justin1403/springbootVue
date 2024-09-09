package tw.justin.springbootvue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import tw.justin.springbootvue.common.Result;
import tw.justin.springbootvue.controller.dto.UserDTO;
import tw.justin.springbootvue.controller.dto.UserPasswordDTO;
import tw.justin.springbootvue.entity.User;

public interface IUserService extends IService<User>  {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Page<User> findpage(Page<User> objectPage,  String username, String email, String address);
}
