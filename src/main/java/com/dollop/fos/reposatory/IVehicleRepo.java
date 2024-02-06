package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.document.deliveryboyverification.Vehicle;

public interface IVehicleRepo extends JpaRepository<Vehicle, String>{

	
}
