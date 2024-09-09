package tw.justin.springbootvue.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tw.justin.springbootvue.entity.Menu;
import tw.justin.springbootvue.mapper.MenuMapper;
import tw.justin.springbootvue.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author justin
 * @since 2024-02-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper =new QueryWrapper<>();

        if(StrUtil.isNotBlank(name)){
        queryWrapper.like(" name", name.trim());
        }

        // 查詢所有數據  menuService.list(queryWrapper); => list(queryWrapper);
        List<Menu> list = list(queryWrapper);

        // 找出 pid 為 null 的一級菜單
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());

        // 找出一級菜單的子菜單
        for(Menu menu:parentNodes){
            //  篩選所有數據中 pid = 父級 id 的數據就是二級菜單
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
