package tw.justin.springbootvue.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import tw.justin.springbootvue.common.Result;
import tw.justin.springbootvue.service.ICourseService;
import tw.justin.springbootvue.entity.Course;


import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author justin
 * @since 2024-04-01
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Course course){
        courseService.saveOrUpdate(course);
        return Result.success();
    }

    @PostMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer courseId,@PathVariable Integer studentId){
        courseService.setStudentCourse(courseId,studentId);
        return Result.success();
    }

    @DeleteMapping("{id}")
    private Result delete(@PathVariable Integer id){

        return Result.success(courseService.removeById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        courseService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll(){
        return Result.success(courseService.list());
    }

    @GetMapping ("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(courseService.getById(id));
    }

    @GetMapping("/page")
    public Result finPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam String name)   {
        Page<Course> page=courseService.findPage(new Page<>(pageNum,pageSize),name);
        return Result.success(page);
        }
}

