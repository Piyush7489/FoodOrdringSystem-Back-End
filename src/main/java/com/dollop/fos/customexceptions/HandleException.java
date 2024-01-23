package com.dollop.fos.customexceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dollop.fos.helper.AppConstant;



@RestControllerAdvice
public class HandleException {
	@ExceptionHandler(ResourceFoundException.class)
	public ResponseEntity<CustomMsgForException>RestaurantNotFound(ResourceFoundException c)
	{
		return new ResponseEntity<CustomMsgForException>(new CustomMsgForException(new Date().toString(),500,AppConstant.MSG_EXCEPTION,c.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
