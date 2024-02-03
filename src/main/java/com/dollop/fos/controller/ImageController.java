package com.dollop.fos.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.utility.IImageService;

import jakarta.servlet.http.HttpServletResponse;




@RestController
@CrossOrigin("*")
@RequestMapping("img")
public class ImageController {
	@Autowired
	private IImageService imageService;
	
	@GetMapping("/hello")
	public String getHello() {
		return "hello";
	}

	@RequestMapping(value = "/getImageApi/{folderName}/{imgName}",method = RequestMethod.GET, produces = MediaType.ALL_VALUE)
	public void getImage(@PathVariable("folderName") String folderName,@PathVariable("imgName") String imgName,HttpServletResponse response) throws IOException {	
		InputStream data = imageService.getImages(folderName+"/"+imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(data, response.getOutputStream());

	}
}
