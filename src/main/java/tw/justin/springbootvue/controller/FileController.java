package tw.justin.springbootvue.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.justin.springbootvue.common.Result;
import tw.justin.springbootvue.entity.Files;
import tw.justin.springbootvue.entity.User;
import tw.justin.springbootvue.mapper.FileMapper;
import tw.justin.springbootvue.utils.TokenUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();  // 獲取名稱
        String type = FileUtil.extName(originalFilename);       // 獲取類型
        long size =file.getSize();          // 獲取文件大小

        // 定義一個文件唯一的標識碼
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid =uuid + StrUtil.DOT + type;

        //先存儲到磁盤
        File uploadFile = new File(fileUploadPath + fileUuid);

        //判斷配置的文件目錄是否存在，若不存在則創建一個新的文件目錄
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists() ){
            parentFile.mkdirs();
        }

        String url;

        // 上傳文件到磁盤
        file.transferTo(uploadFile);

        // 獲取文件的 md5
        String md5 = SecureUtil.md5(uploadFile);

        // 從數據庫查詢是否存在相同的記錄
        Files dbFiles = getFileByMd5(md5);

        if(dbFiles != null){
            url = dbFiles.getUrl();
            // 由於文件已存在，所以刪除剛才上傳的重複文件
            uploadFile.delete();
        }else {
            // 把獲得到的文件存儲到磁盤目錄
            url="http://localhost:9090/file/"+ fileUuid;
        }


        //存儲數據庫
        Files saveFile =new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;


//        return "";
    }

    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid , HttpServletResponse response) throws IOException {

        // 根據文件的唯一標識碼獲取文件
        File uploadFile = new File(fileUploadPath +fileUuid);
        // 設置輸出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");

        // 讀取文件的字節流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();


    }


    // 通過文件的 md5 查詢文件

    private Files getFileByMd5(String md5){
        // 查詢文件的 md5 是否存在
//        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("md5",md5);
//        return fileMapper.selectOne(queryWrapper);

        QueryWrapper<Files> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null:filesList.get(0);
    }


    @GetMapping("/front/all")
    public Result frontAll(@RequestBody Files files){
        // 新增或者更新
        return Result.success(fileMapper.selectList(null));
    }


    @PostMapping("/update")
    public Result update(@RequestBody Files files){
        // 新增或者更新
        return Result.success(fileMapper.updateById(files));
    }


    @DeleteMapping("{id}")
    private Result delete(@PathVariable Integer id){
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(fileMapper.updateById(files));
    }

    @PostMapping("/del/batch")
    private Result deleteBatch(@RequestBody List<Integer> ids){
        // select * from sys_file where id in (id,id,id....)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for(Files file:files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success( );
    }







    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam(defaultValue = "") String name
    ){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        // 查詢未刪除的記錄
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");       // 修改成 id 值大到小順序
        if(!"".equals(name)){
            queryWrapper.like("name", name.trim());
        }


        // 獲取當前用戶信息
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("========================獲取當前用戶信息========================" + currentUser.getNickname());

        return Result.success(fileMapper.selectPage(new Page< >(pageNum,pageSize),queryWrapper));

    }

}
