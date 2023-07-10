package com.BikkadIT.services.Impl;

import com.BikkadIT.exception.BadRequestException;
import com.BikkadIT.services.FileServices;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileImpl implements FileServices {
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullpath = path+fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")){
            File folder = new File(path);

            if(!folder.exists()){
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullpath));
            return fileNameWithExtension;
        }else{
            throw new BadRequestException("Please Upload only jpg and png file");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path + File.separator + name;
        FileInputStream fileInputStream = new FileInputStream(fullpath);
        return fileInputStream;
    }
}
