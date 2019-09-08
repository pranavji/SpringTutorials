package com.ospyn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter@Setter
@NoArgsConstructor
public class NotificationEntity {
    public NotificationEntity(String message , Date timeStamp) {
        Message = message;
        this.timeStamp = timeStamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String Message;


    @Temporal(TemporalType.TIMESTAMP)
    @Nullable
    private Date timeStamp;


}
