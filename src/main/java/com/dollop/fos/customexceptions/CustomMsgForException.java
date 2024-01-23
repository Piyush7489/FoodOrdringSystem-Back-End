package com.dollop.fos.customexceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomMsgForException {

	
	private String date;
	private Integer code;
	private String status;
	private String msg;
	
}
