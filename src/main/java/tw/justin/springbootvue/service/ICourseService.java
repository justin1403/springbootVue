package tw.justin.springbootvue.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tw.justin.springbootvue.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author justin
 * @since 2024-04-01
 */
public interface ICourseService extends IService<Course> {
    Page<Course> findPage(Page<Course>  Page, String name);


    void setStudentCourse(Integer courseId , Integer studentId);
}