package com.ospyn.models;

import com.google.gson.Gson;
import com.ospyn.repository.JpaNotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor

public class Notification {
    private String message;
    private Long teamid;
    private Long clueid;
    private String unlocktime;
    private Boolean done;
    private Boolean winner;

    @Autowired
    public static JpaNotificationRepository notificationRepository;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public  static String add(String message , Date time)
    {
        notificationRepository.save(new NotificationEntity(message, time));
        return message;
    }
}
