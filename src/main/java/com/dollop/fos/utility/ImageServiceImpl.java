package com.dollop.fos.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageServiceImpl implements IImageService{
	
	  @Value("${project.image}")
	    private String path;

	@Override
	public String uploadImage(MultipartFile file , String Dir) throws IOException {
		// TODO Auto-generated method stub
		String currentDir =  System.getProperty("user.dir")+path+File.separator+Dir;
	    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
	    String uuid = UUID.randomUUID().toString();
		String randomName =  uuid.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		try {
			Files.copy(file.getInputStream(),Paths.get(currentDir , randomName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Dir+File.separator+randomName;
	}

	@Override
	public InputStream getImages(String fileName) {
		
		try {
			return new FileInputStream(System.getProperty("user.dir")+path+fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
		return null;
	}

}
