package com.BikkadIT;

import com.BikkadIT.entity.Role;
import com.BikkadIT.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceProjectApplication {
	@Autowired
   private RoleRepo roleRepo;
	@Value("${normal.role.id}")
	private long normalId;
	@Value("${admin.role.id}")
	private long adminId;
	public static void main(String[] args) {
		SpringApplication.run(ECommerceProjectApplication.class, args);



	}

	public void run(String ... args){
		try {
			Role roleAdmin = Role.builder().roleName("ROLE_ADMIN").roleId(adminId).build();
			Role roleNormal = Role.builder().roleName("ROLE_NORMAL").roleId(normalId).build();
			roleRepo.save(roleAdmin);
            roleRepo.save(roleNormal);
		}catch (Exception e){
			e.printStackTrace();
		}
	}



}
