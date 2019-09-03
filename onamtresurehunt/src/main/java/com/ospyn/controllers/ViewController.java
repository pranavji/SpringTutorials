package com.ospyn.controllers;

import com.ospyn.helpers.ZXingHelper;
import com.ospyn.models.Team;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ViewController {

    @Autowired
    private JpaTeamRepository jpaTeamRepository;

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

        model.addAttribute("teams",jpaTeamRepository.findAll());
        return "admin/dashboard"; //view
    }
    @GetMapping("/getClue")
    public String getClue(@RequestParam(name = "id", required = false, defaultValue = "") String uuid,Model model) {



        model.addAttribute("uuid",uuid);
        return "clueverify"; //view
    }
}
