package com.itcast.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {
    /**
     * 传统文件上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/fileUpload")
    public String fileUpload(HttpServletRequest request) throws Exception {
        String path = request.getSession().getServletContext().getRealPath("/update/");
        System.out.println(path);
        File file = new File(path);
        //判断文件夹是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        //解析request对象 获取上传文件项
        DiskFileItemFactory factory = new DiskFileItemFactory();//磁盘文件项
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析request  返回文件项集合
        List<FileItem> fileItems = upload.parseRequest(request);
        for (FileItem fileItem : fileItems) {
            //判断当前文件项是否是上传文件项
            if (fileItem.isFormField()) {
                //普通表单项
            } else {
                //上传文件项
                String fileItemName = fileItem.getName();
                //生成随机标识
                fileItemName = UUID.randomUUID().toString().replace("-","") + fileItemName;
                System.out.println(fileItemName);
                fileItem.write(new File(path, fileItemName));
                //删除临时文件
                fileItem.delete();
            }
        }
        return "success";
    }
}
