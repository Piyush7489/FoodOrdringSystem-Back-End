package com.dollop.fos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class RestAddress {

	@Id
	private String restAddressId;
	private String street;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private String zipCode;
    private String restContect;
}
