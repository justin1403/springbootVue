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

//    @Autowired
//    private UserMapper userMapper;



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
//        return userService.saveOrUpdate(user);
        return Result.success(userService.saveOrUpdate(user));
    }


    @GetMapping
//    public List<User> findAll(){
//        return userService.list();

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
//    private integer delete(@PathVariable Integer id){
//        return userMapper.deleteById(id);

    private Result delete(@PathVariable Integer id){
//        return userService.removeById(id);
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
        // select * from sys_user where username = #(username)
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    @PostMapping("/del/batch")
    private Result deleteBatch(@RequestBody List<Integer> ids){
//        return userService.removeByIds(ids);
        return Result.success(userService.removeByIds(ids));
    }

    // 分頁查詢
    // 接口路徑,/user/page?pageNum=1&pageSize=10
    // Limit 第一個參數 =（pageNum -1）*pageSize
    // @RequestParam 接受
//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum
//                               ,@RequestParam Integer pageSize
////    ){
//                               ,@RequestParam String username        ){
//
//        pageNum = (pageNum - 1) * pageSize;
////        return   userMapper.selectPage(pageNum,pageSize);
//
////        username = '%'+username+'%';
//        List<User> data =userMapper.selectPage(pageNum,pageSize,username);
//        Integer total= Integer.valueOf(userMapper.selectTotal(username));
//
////        List<User> data =userMapper.selectPage(pageNum,pageSize );
////        Integer total= Integer.valueOf(userMapper.selectTotal( ));
//        Map<String, Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);
//        return res;
//    }


// 分頁查詢-Mybatis-plus

    @GetMapping("/page")
//    public Page<User> findPage(@RequestParam Integer pageNum
    public Result findPage(@RequestParam Integer pageNum
                               , @RequestParam Integer pageSize
                               , @RequestParam(defaultValue = "") String username
                               , @RequestParam(defaultValue = "") String email
                               , @RequestParam(defaultValue = "") String address
//    ){
    ){

//        IPage<User> page=new Page<>(pageNum,pageSize);
//
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        if(!"".equals(username)){
//            queryWrapper.like("username",username.trim());
//        }
//        if(!"".equals(email)) {
//            queryWrapper.like("email", email.trim());
//        }
//        if(!"".equals(address)) {
//            queryWrapper.like("address", address.trim());
//        }
//
//        queryWrapper.orderByDesc("id");       // 修改成 id 值大到小順序

        // 獲取當前用戶信息
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("========================獲取當前用戶信息========================" + currentUser.getNickname());

//        return userService.page(page,queryWrapper);
//        return userService.page(new Page<>(pageNum,pageSize),queryWrapper);
        return Result.success(userService.findpage(new Page<>(pageNum,pageSize),username,email,address));

    }












    /**
     * 導出接口
     *
     */

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        // 從數據庫查詢出所有的數據
        List <User> list=userService.list();
        // 通過工具類創建 writer 寫出的磁盤路徑
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用戶信息.xlsx");
        // 在內存操作，寫出到瀏覽器
//        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.setSheet("表2");
        // 能設定分頁表名稱
        ExcelWriter writer = ExcelUtil.getWriterWithSheet("用戶名稱表");
        // 自定義標題別名
//        writer.addHeaderAlias("username","用戶名");
//        writer.addHeaderAlias("password","密碼");
//        writer.addHeaderAlias("nickname","暱稱");
//        writer.addHeaderAlias("email","郵箱");
//        writer.addHeaderAlias("phone","電話");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("createTime","創建時間");
//        writer.addHeaderAlias("avatar","頭像");

        // 一次性寫出 list 內的對象到 excel 使用默認樣式，強制輸出標題
        writer.write(list,true);

        // 設置瀏覽器響應的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用戶信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }


//    /**
//     * excel 導入
//     *
//     */
//
//    @PostMapping("/import")
//    public Boolean imp(MultipartFile file) throws Exception{
//        InputStream inputStream=file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
////        List<User> list=reader.readAll(User.class);
//        List<User> list=reader.read(0,1, User.class);
//        userService.saveBatch(list);
//        System.out.println(list);
//        return true;
//    }



    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list=reader.readAll(User.class);

        return Result.success(userService.saveBatch(list));
    }



//    @PostMapping("/import")
//    public void imp(MultipartFile file) throws Exception{
//        InputStream inputStream=file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        List<User> list=reader.readAll(User.class);
//        userService.saveBatch(list);
//    }


//    public Result imp(MultipartFile file) throws Exception{
//        InputStream inputStream=file.getInputStream();
//        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        List<List<Object>> list =reader.read(1);
//
//        List<User> users= CollUtil.newArrayList();
//        for(List<Object>row:list){
//            User user=new User();
//            user.setUsername(row.get(0).toString());
//            user.setPassword(row.get(1).toString());
//            user.setNickname(row.get(2).toString());
//            user.setEmail(row.get(3).toString());
//            user.setPhone(row.get(4).toString());
//            user.setAddress(row.get(5).toString());
//            user.setAvatarUrl(row.get(6).toString());
//            users.add(user);
//        }
//        userService.saveBatch(users);
//        return Result.success(true);
//    }


}