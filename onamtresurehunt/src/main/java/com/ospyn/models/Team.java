package com.ospyn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "teamlist")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @NotNull
    private String teamName;

    @OneToMany(mappedBy = "team" , cascade = CascadeType.ALL ,fetch = FetchType.EAGER)

    private List<Clue> clues;

    public Team(String teamName) {
        this.teamName = teamName;
    }
}
