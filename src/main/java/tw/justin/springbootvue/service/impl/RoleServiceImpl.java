package tw.justin.springbootvue.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import tw.justin.springbootvue.entity.Menu;
import tw.justin.springbootvue.entity.Role;
import tw.justin.springbootvue.entity.RoleMenu;
import tw.justin.springbootvue.mapper.RoleMapper;
import tw.justin.springbootvue.mapper.RoleMenuMapper;
import tw.justin.springbootvue.service.IMenuService;
import tw.justin.springbootvue.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author justin
 * @since 2024-02-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    //    第一種方式
//    @Override
//    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("role_id",roleId);
//        roleMenuMapper.delete(queryWrapper);
//    }


    // 第二種方

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {

        // 先刪除當前角色 id 所有的綁定關係
        roleMenuMapper.deleteByRoleId(roleId);

        // 再把前端傳過來的菜單 id 數組綁定到當前的這個角色 id 上去
        List<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Integer menuId:menuIds) {
            Menu menu = menuService.getById(menuId);
            if(menu.getPid()!=null && !menuIdsCopy.contains(menu.getPid())){    // 二級菜單，並且傳過來的 menuId 數組裡面沒有它的父級 id
                // 那麼我們就得補上這個父級 id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }




//        // 再把前端傳過來的菜單 id 數組綁定到當前的這個角色 id 上去
//        for (Integer menuId:menuIds) {
//            RoleMenu roleMenu = new RoleMenu();
//            roleMenu.setRoleId(roleId);
//            roleMenu.setMenuId(menuId);
//            roleMenuMapper.insert(roleMenu);
//        }




    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }


}
