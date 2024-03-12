package com.dollop.fos.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
	@Id
	private Long roleId;
	private String roleName;
	@OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
	private Set<UserRole> userRole = new HashSet<>();
}
