package com.ospyn;

import com.ospyn.repository.JpaClueRepository;
import com.ospyn.repository.JpaTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.rmi.CORBA.Util;

@SpringBootApplication
public class OnamTresureHuntApplication {
	@Autowired
	private static JpaTeamRepository jpaTeamRepository;
	public static void main(String[] args) {

		SpringApplication.run(OnamTresureHuntApplication.class, args);


	}

}
