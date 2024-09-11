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

        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size =file.getSize();
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid =uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists() ){
            parentFile.mkdirs();
        }
        String url;
        file.transferTo(uploadFile);
        String md5 = SecureUtil.md5(uploadFile);
        Files dbFiles = getFileByMd5(md5);
        if(dbFiles != null){
            url = dbFiles.getUrl();
            uploadFile.delete();
        }else {
            url="http://localhost:9090/file/"+ fileUuid;
        }
        Files saveFile =new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }

    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid , HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath +fileUuid);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null:filesList.get(0);
    }

    @GetMapping("/front/all")
    public Result frontAll(@RequestBody Files files){
        return Result.success(fileMapper.selectList(null));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Files files){
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
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name.trim());
        }

        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("========================獲取當前用戶信息========================" + currentUser.getNickname());
        return Result.success(fileMapper.selectPage(new Page< >(pageNum,pageSize),queryWrapper));

    }

}
