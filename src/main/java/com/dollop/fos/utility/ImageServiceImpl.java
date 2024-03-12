package com.dollop.fos.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class ImageServiceImpl implements IImageService{
	
	@Autowired
	public Cloudinary  cloudinary;
	
	
	  @Value("${project.image}")
	    private String path;

	@Override
	public String uploadImage(MultipartFile file , String Dir) throws IOException {
		// TODO Auto-generated method stub
		String currentDir =  System.getProperty("user.dir")+path+File.separator+Dir;
	    String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
	    String uuid = UUID.randomUUID().toString();
		String randomName =  uuid.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
		Map uploadResponse;
		try {
//			Files.copy(file.getInputStream(),Paths.get(currentDir , randomName), StandardCopyOption.REPLACE_EXISTING);
			uploadResponse = cloudinary.uploader().upload(file.getBytes(),
					  ObjectUtils.asMap("public_id", Dir +"/"+randomName));
			return (String)uploadResponse.get("secure_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
//		return Dir+File.separator+randomName;
//		
//		String randomName= (UUID.randomUUID().toString() + myFile.getOriginalFilename());
//
//		String fileName = StringUtils.cleanPath(randomName);
//		Map uploadResponse;
//		try {
//			uploadResponse = cloudinary.uploader().upload(myFile.getBytes(),
//					  ObjectUtils.asMap("public_id", destinationPath +"/"+fileName)); 
//			return (String)uploadResponse.get("secure_url");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
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
