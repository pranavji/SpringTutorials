package com.ospyn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clue")
public class Clue {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String clue;

    @Override
    public String toString() {
        return "Clue{" +
                "id=" + id +
                ", clue='" + clue + '\'' +
                ", password='" + password + '\'' +
                ", clueTitle='" + clueTitle + '\'' +
                ", unLocked=" + unLocked +
                '}';
    }

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private String uuid;

    @Column
    @NotNull
    String clueTitle;

    @Column(nullable = true)
    String clueImage;

    @Column
    boolean unLocked;



    //@UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date unLockTime;

    @ManyToOne
    @JoinColumn(name = "teamlistid" )
    private Team team;


    private Long nextClue;

    public Clue(String clue, String password, String clueTitle, Team team, String clueImage ) {
        this.clue = clue;
        this.password = password;
        this.clueTitle = clueTitle;
        this.team = team;
        this.uuid = UUID.randomUUID().toString();
        this.clueImage = clueImage;
    }

    public String getClueImage() {
        if(null!=clueImage)
        {
            if(clueImage.trim().length()<=0)
                clueImage=null;
        }
        return clueImage;
    }
}
