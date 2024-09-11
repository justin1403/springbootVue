package tw.justin.springbootvue.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.justin.springbootvue.common.Constants;
import tw.justin.springbootvue.common.Result;
import tw.justin.springbootvue.controller.dto.UserDTO;
import tw.justin.springbootvue.controller.dto.UserPasswordDTO;
import tw.justin.springbootvue.entity.User;
import tw.justin.springbootvue.service.IUserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"參數錯誤");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }

    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"參數錯誤");
        }
        return Result.success(userService.register(userDTO));
    }

    @PostMapping
    public Result save(@RequestBody User user){
        // 新增或者更新
        return Result.success(userService.saveOrUpdate(user));
    }

    @GetMapping
        public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/role/{role}")
        public Result findUserByRole(@PathVariable String role){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role);
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    @DeleteMapping("{id}")

    private Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }

    /**
     * 根據用戶的用戶名獲取數據庫表這個用戶的所有屬性
     * @param username
     * @return
     */
    @GetMapping("/username/{username}")
    private Result fineOne(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    private Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
                               , @RequestParam Integer pageSize
                               , @RequestParam(defaultValue = "") String username
                               , @RequestParam(defaultValue = "") String email
                               , @RequestParam(defaultValue = "") String address ){
        return Result.success(userService.findpage(new Page<>(pageNum,pageSize),username,email,address));

    }


    /**
     * 導出接口
     *
     */

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List <User> list=userService.list();
        ExcelWriter writer = ExcelUtil.getWriterWithSheet("用戶名稱表");
        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用戶信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list=reader.readAll(User.class);

        return Result.success(userService.saveBatch(list));
    }


}