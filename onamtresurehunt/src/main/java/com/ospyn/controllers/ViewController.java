package com.ospyn.controllers;

import com.ospyn.Utils;
import com.ospyn.helpers.ZXingHelper;
import com.ospyn.models.Team;
import com.ospyn.repository.JpaClueRepository;
import com.ospyn.repository.JpaTeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private JpaTeamRepository jpaTeamRepository;

    @Autowired
    private JpaClueRepository jpaClueRepository;

    @GetMapping("/test")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);
        new ZXingHelper().getQRCodeImage(name,300,300,name);

        return "index"; //view
    }
    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) {
        for (Team team : jpaTeamRepository.findAll()) {
            Logger logger = LoggerFactory.getLogger(ViewController.class);
            logger.info(team.toString());
            System.out.println(team.toString());
        }
        model.addAttribute("message", "test");
        model.addAttribute("tasks", tasks);

        return "index"; //view
    }
    @GetMapping("/admin")
    public String admin(@RequestParam(name = "message", required = false, defaultValue = "") String message,Model model, HttpSession session) {

        if(null==session.getAttribute("username")||session.getAttribute("username").equals(""))
        {
            return "admin/login";
        }

        if(message!=null)
        {
            model.addAttribute("message",message);
        }

        jpaTeamRepository.findAll().forEach(p->{if(p.isWinner()){
            Utils.Winner=p;
        }});

        model.addAttribute("teams",jpaTeamRepository.findAll());


        return "admin/dashboard"; //view
    }
    @GetMapping("/getClue")
    public String getClue(@RequestParam(name = "id", required = false, defaultValue = "") String uuid,@RequestParam(name = "message", defaultValue = "") String message,Model model) {

        model.addAttribute("title",jpaClueRepository.findByUuid(uuid).get().getClueTitle());

        model.addAttribute("uuid",uuid);

        model.addAttribute("message",message);
        return "clueverify"; //view
    }
    @GetMapping("/socket")
    public String getSocket(@RequestParam(name = "id", required = false, defaultValue = "") String uuid,Model model) {



        model.addAttribute("uuid",uuid);
        return "socket"; //view
    }
}
