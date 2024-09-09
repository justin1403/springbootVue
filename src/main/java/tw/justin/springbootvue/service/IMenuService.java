package tw.justin.springbootvue.service;

import tw.justin.springbootvue.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author justin
 * @since 2024-02-23
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
