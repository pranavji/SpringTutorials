package com.ospyn.controllers;

import com.ospyn.helpers.PushSocket;
import com.ospyn.helpers.ZXingHelper;
import com.ospyn.models.Clue;
import com.ospyn.models.Greeting;
import com.ospyn.models.Notification;
import com.ospyn.models.Team;
import com.ospyn.repository.JpaClueRepository;
import com.ospyn.repository.JpaTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class GeneralController {

    @Autowired
    private JpaTeamRepository jpaTeamRepository;

    @Autowired
    private JpaClueRepository jpaClueRepository;


    @GetMapping("/service/addteam")
    public String addTeam(@RequestParam(name = "teamname", required = true, defaultValue = "") String teamname, Model model) {

        try {

            jpaTeamRepository.save(new Team(teamname));
            model.addAttribute("message", "Sucess");
            return "redirect:../admin";
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed To add");
            return "redirect:../admin?message=DuplicateEntry";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed To add");
            return "redirect:../admin?message=UnknownError";
        }
    }

    @GetMapping("/service/addclue")
    public String addClue(@RequestParam(name = "cluetitle", required = true, defaultValue = "") String clueTitle,
                          @RequestParam(name = "clue", required = true, defaultValue = "") String clue,
                          @RequestParam(name = "password", required = true, defaultValue = "") String password,
                          @RequestParam(name = "teamid", required = true, defaultValue = "") String teamid,
                          @RequestParam(name = "clueimage", defaultValue = "") String clueImage,
                          Model model) {

        try {
            Team team = jpaTeamRepository.findById(Long.parseLong(teamid)).get();
            Clue clueDTO = new Clue(clue, password, clueTitle, team ,clueImage);

            Clue prevClue = null;
            try {
                prevClue = jpaTeamRepository.findById(team.getId()).get().getClues().stream().max(new Comparator<Clue>() {
                    @Override
                    public int compare(Clue o1, Clue o2) {
                        return (int) (o1.getId() - o2.getId());
                    }
                }).get();


            } catch (Exception e) {
                e.printStackTrace();
            }
            jpaClueRepository.save(clueDTO);
            if (prevClue != null) {
                prevClue.setNextClue(jpaClueRepository.save(clueDTO).getId());
                jpaClueRepository.save(prevClue);
            }
            String clueId = clueDTO.getUuid().toString();
            new ZXingHelper().getQRCodeImage("http://202.88.244.214:6005/getClue?id=" + clueId, 300, 300, "qr" + clueId);

            model.addAttribute("message", "Sucess");
            return "redirect:../admin";
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed To add");
            return "redirect:../admin?message=DuplicateEntry";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed To add");
            return "redirect:../admin?message=UnknownError";
        }
    }

    @PostMapping("/service/login")
    public String login(HttpSession session, @RequestParam(name = "username", required = true, defaultValue = "") String username, @RequestParam(name = "password", required = true, defaultValue = "") String password, Model model) {

        if (username.equals("admin") && password.equals("0spyn0nam")) {
            session.setAttribute("username", "admin");
            return "redirect:../admin";
        } else {
            return "redirect:../admin?message=UnknownError";
        }


    }

    @PostMapping("/service/verifyclue")
    public String verifyclue(HttpSession session, @RequestParam(name = "pwd", required = true, defaultValue = "") String pwd, @RequestParam(name = "uuid", required = true, defaultValue = "") String uuid, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        List<Clue> clue = jpaClueRepository.findByUuidAndPassword(uuid, pwd);
        if (!clue.isEmpty()) {
            Clue currentClue = clue.get(0);

            if (currentClue.getNextClue() == null) {
                if(!currentClue.isUnLocked()) {
                    currentClue.setUnLocked(true);
                    currentClue.setUnLockTime(new Date());
                    List winnerlist = StreamSupport.stream(jpaTeamRepository.findAll().spliterator(),false).filter(p->p.isWinner()).collect(Collectors.toList());
                    if(winnerlist.isEmpty())
                    {
                        Team currentTeam = currentClue.getTeam();
                        currentTeam.setWinner(true);
                        jpaTeamRepository.save(currentTeam);
                    }

                    jpaClueRepository.saveAll(clue);
                   if(currentClue.getTeam().isWinner())
                       PushSocket.broadCast(new Notification(Notification.add("Team \""+currentClue.getTeam().getTeamName()+"\" won at "+currentClue.getUnLockTime(),currentClue.getUnLockTime()),currentClue.getTeam().getId(),currentClue.getId(),currentClue.getUnLockTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter),true,true).toString());
                   else
                       PushSocket.broadCast(new Notification(Notification.add("Team \""+currentClue.getTeam().getTeamName()+"\" finished all clues at "+currentClue.getUnLockTime(),currentClue.getUnLockTime()),currentClue.getTeam().getId(),currentClue.getId(),currentClue.getUnLockTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter),true,false).toString());
                }


               model.addAttribute("completiontime",jpaClueRepository.findByUuidAndPassword(uuid, pwd).get(0).getUnLockTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter));
                return "winner";

            }
            else
            {
                if(!currentClue.isUnLocked()) {
                    currentClue.setUnLocked(true);
                    currentClue.setUnLockTime(new Date());
                    PushSocket.broadCast(new Notification(Notification.add("Team \"" + currentClue.getTeam().getTeamName() + "\" has unlocked clue "+currentClue.getClueTitle(),currentClue.getUnLockTime()), currentClue.getTeam().getId(), currentClue.getId(), currentClue.getUnLockTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter), true, false).toString());
                    jpaClueRepository.saveAll(clue);
                }
            }

            model.addAttribute("uuid", uuid);
            model.addAttribute("clueObject", currentClue);
            model.addAttribute("nextCluePassword", jpaClueRepository.findById(currentClue.getNextClue()).get().getPassword());
            return "clueview";
        } else {

            try {
                return "redirect:../getClue?id=" + uuid + "&message=oops";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @GetMapping("/service/logout")
    public String logout(HttpSession session) {


        session.setAttribute("username","");
        session.setAttribute("username",null);


        return "redirect:../admin";


    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(Notification message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("hello");
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getMessage()) + "!");
    }



}
