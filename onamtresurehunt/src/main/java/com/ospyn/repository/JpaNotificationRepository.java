package com.ospyn.repository;

import com.ospyn.models.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationRepository extends JpaRepository<NotificationEntity,Long> {

}
