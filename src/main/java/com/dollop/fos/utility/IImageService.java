package com.dollop.fos.utility;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {

	public String uploadImage(MultipartFile file,String dir) throws IOException;
	public InputStream getImages(String fileName);
	
	
}
