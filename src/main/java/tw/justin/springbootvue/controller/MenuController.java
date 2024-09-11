package tw.justin.springbootvue.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import tw.justin.springbootvue.common.Constants;
import tw.justin.springbootvue.common.Result;
import tw.justin.springbootvue.entity.Dict;
import tw.justin.springbootvue.entity.Role;
import tw.justin.springbootvue.mapper.DictMapper;
import tw.justin.springbootvue.service.IMenuService;
import tw.justin.springbootvue.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author justin
 * @since 2024-02-23
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Menu menu){
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @DeleteMapping("{id}")
    private Result delete(@PathVariable Integer id){
        menuService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        menuService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/ids")
    public Result findAll(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String  name){
        return Result.success(menuService.findMenus(name));
    }

    @GetMapping ("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result finPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String  name   ){
        QueryWrapper<Menu> queryWrapper =new QueryWrapper<>();
        queryWrapper.like(" name", name.trim());
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum,pageSize),queryWrapper));
        }

    @GetMapping ("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }
}

