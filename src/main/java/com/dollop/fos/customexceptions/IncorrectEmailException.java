package com.dollop.fos.customexceptions;

public class IncorrectEmailException extends RuntimeException{

	public IncorrectEmailException() {
		super("Category  Alredy Exist...");
		// TODO Auto-generated constructor stub
	}

	public IncorrectEmailException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
