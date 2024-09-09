package tw.justin.springbootvue.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import tw.justin.springbootvue.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author justin
 * @since 2024-04-01
 */
public interface CourseMapper extends BaseMapper<Course> {


    Page<Course> findPage(Page<Course> page,@Param("name") String name);

    void deleteStudentCourse(@Param("courseId") Integer courseId , @Param("studentId") Integer studentId);
    void setStudentCourse(@Param("courseId") Integer courseId , @Param("studentId") Integer studentId);
}