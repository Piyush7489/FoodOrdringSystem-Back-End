package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.DeliveryBoyVerificationRequest;

public interface IDeliveryBoyService {

	public ResponseEntity<?> deliveryBoyRegistration(DeliveryBoyVerificationRequest d);
}
