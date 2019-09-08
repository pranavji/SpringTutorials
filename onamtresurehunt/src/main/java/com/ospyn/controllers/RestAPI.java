package com.ospyn.controllers;


import com.ospyn.models.Clue;
import com.ospyn.models.NotificationEntity;
import com.ospyn.repository.JpaClueRepository;
import com.ospyn.repository.JpaNotificationRepository;
import com.ospyn.repository.JpaTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/ajax")
public class RestAPI
{
    @Autowired
    private JpaTeamRepository jpaTeamRepository;

    @Autowired
    private JpaClueRepository jpaClueRepository;

    @Autowired
    private JpaNotificationRepository jpaNotificationRepository;


    @GetMapping("/deleteclue" )
    public ResponseEntity<String> deleteclue(@RequestParam(name = "clueid", required = true) Long clueid , @RequestParam(name = "teamid", required = true) Long teamid) {

        Clue deletionClue = jpaClueRepository.findById(clueid).get();

        List<Clue> teamClueList = jpaTeamRepository.findById(teamid).get().getClues();

        teamClueList.stream().forEach(p->{if(p.getNextClue()==deletionClue.getId()){
            p.setNextClue(deletionClue.getNextClue());
            jpaClueRepository.save(p);
        }});
        jpaClueRepository.delete(deletionClue);

        return ResponseEntity.status(HttpStatus.OK).body("Done");


    }
    @GetMapping("/deleteteam" )
    @Transactional
    public ResponseEntity<String> deleteteam( @RequestParam(name = "teamid", required = true) Long teamid) {

        jpaClueRepository.deleteByTeam(jpaTeamRepository.findById(teamid).get());
         jpaTeamRepository.deleteById(teamid);

        return ResponseEntity.status(HttpStatus.OK).body("Done");


    }

    @GetMapping("/notification" )

    public ResponseEntity<List<NotificationEntity>> loadNotifications() {

        return ResponseEntity.status(HttpStatus.OK).body(jpaNotificationRepository.findAll(Sort.by(Sort.Direction.DESC,"timeStamp")));


    }

}
