package com.ospyn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor

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

    @Column
    boolean unLocked;


    @ManyToOne
    @JoinColumn(name = "team_list_id" )
    private Team team;

    public Clue(String clue, String password, String clueTitle, Team team ) {
        this.clue = clue;
        this.password = password;
        this.clueTitle = clueTitle;
        this.team = team;
        this.uuid = UUID.randomUUID().toString();
    }
}
