package com.dollop.fos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestAddressRequest {


	private String restAddressId;
	private String street;
    private String city;
    private String state;
    private String zipCode;
    private String restContect;
    private String latitude;
    private String longitude;
    private String restId;
	
}
