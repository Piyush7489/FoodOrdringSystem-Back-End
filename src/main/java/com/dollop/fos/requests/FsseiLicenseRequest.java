package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FsseiLicenseRequest {

	private String id;
	private String licenseNumber;
	private String fssaiLicensePhoto;
}
