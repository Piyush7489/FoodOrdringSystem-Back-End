package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.document.deliveryboyverification.DeliveryBoyVerification;

public interface IDeliveryboyVerificationRepo extends JpaRepository<DeliveryBoyVerification, String>{

}
