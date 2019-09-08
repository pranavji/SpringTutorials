package com.ospyn;

import com.ospyn.models.Notification;
import com.ospyn.repository.JpaNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class OnamTresureHuntApplication {
	@Autowired
	private JpaNotificationRepository jpaNotificationRepository;
	public static void main(String[] args) {

		SpringApplication.run(OnamTresureHuntApplication.class, args);


	}
	@PostConstruct
	private void init() {
		Notification.notificationRepository = jpaNotificationRepository;
	}

}
