package com.dollop.fos.document.deliveryboyverification;

import com.dollop.fos.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryBoyVerification {

	@Id
	private String id;
	private String identityCard;
	@OneToOne
	@JoinColumn(name="boy_id")
	private User boy;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
	
}
