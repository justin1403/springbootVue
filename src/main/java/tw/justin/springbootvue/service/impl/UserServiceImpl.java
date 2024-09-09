package tw.justin.springbootvue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.justin.springbootvue.common.Constants;
import tw.justin.springbootvue.controller.dto.UserDTO;
import tw.justin.springbootvue.controller.dto.UserPasswordDTO;
import tw.justin.springbootvue.entity.Menu;
import tw.justin.springbootvue.entity.User;
import tw.justin.springbootvue.exception.ServiceException;
import tw.justin.springbootvue.mapper.RoleMapper;
import tw.justin.springbootvue.mapper.RoleMenuMapper;
import tw.justin.springbootvue.mapper.UserMapper;
import tw.justin.springbootvue.service.IMenuService;
import tw.justin.springbootvue.service.IUserService;
import tw.justin.springbootvue.utils.TokenUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {

//    private static final Log LOG= LogFactory.get();
    private static final Log log = LogFactory.get();

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one !=null){
            BeanUtil.copyProperties(one,userDTO,true);    // 第三個參數 true 是 不在乎大小寫都複製

            //設置 token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();    // ROLE_ADMIN

            // 設置用戶的菜單列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;

        }else {
            throw new ServiceException(Constants.CODE_600,"用戶名或密碼錯誤");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if(one ==null){
            one =new User();
            BeanUtil.copyProperties(one,userDTO,true);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_600,"用戶名或密碼錯誤");
        }
        return  one;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600,"密碼錯誤");
        }
    }

    @Override
    public Page<User> findpage(Page<User> page, String username, String email, String address) {
        return userMapper.findPage(page,username,email,address);
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
//            LOG.info(e);
            log.error(e);
            throw new ServiceException(Constants.CODE_500,"系統錯誤2");
        }
        return one;
    }





//    public boolean saveUser(User user) {
//        if(user.getId()==null){
//            return save(user);  // mybatis-plus 提供的方法，表示插入數據
//        }else{
//            return updateById(user);
//        }

//        return saveOrUpdate(user);
//    }


//    @Autowired
//    private UserMapper userMapper;
//
//    public int save (User user) {
//        if (user.getId() == null) { // user 沒有 id，則表示是新增
//            return userMapper.insert(user);
//        } else {    //否則為更新
//            return userMapper.update(user);
//        }
//
//    }


    /**
     * 獲取當前角色的菜單列表
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 當前角色的所有菜單 id 集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系統所有的菜單
        List<Menu> menus = menuService.findMenus("");

        // new 一個最後篩選完成之後的 list
        List<Menu> roleMenus = new ArrayList<>();

        // 篩選所當前用戶角色的菜單
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf 移除 children 裡面不在 menuIds 集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }




}
