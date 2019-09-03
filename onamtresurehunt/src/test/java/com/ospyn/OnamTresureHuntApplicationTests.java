package com.ospyn;

import com.ospyn.models.Clue;
import com.ospyn.models.Team;
import com.ospyn.repository.JpaTeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnamTresureHuntApplicationTests {

	@Autowired
	private JpaTeamRepository jpaTeamRepository;

	@Test
	public void contextLoads() {
		Clue clueA1= new Clue();
		clueA1.setClue("find me if you can1");
		clueA1.setClueTitle("test clue");
		clueA1.setPassword("12345");
		clueA1.setUnLocked(false);

		Clue clueA2= new Clue();
		clueA2.setClue("find me if you can2");
		clueA2.setClueTitle("test clue");
		clueA2.setPassword("12345");
		clueA2.setUnLocked(false);

		Clue clueB1= new Clue();
		clueB1.setClue("find me if you can3");
		clueB1.setClueTitle("test clue");
		clueB1.setPassword("12345");
		clueB1.setUnLocked(false);


		Clue clueB2= new Clue();
		clueB2.setClue("find me if you can4");
		clueB2.setClueTitle("test clue");
		clueB2.setPassword("12345");
		clueB2.setUnLocked(false);



		Team A = new Team();
		A.setTeamName("Alpha");
		A.setClues((Arrays.asList(clueA1,clueA2)));
		clueA1.setTeam(A);
		clueA2.setTeam(A);
		Team B = new Team();
		B.setTeamName("Beta");
		B.setClues((Arrays.asList(clueB1,clueB2)));
		clueB1.setTeam(B);
		clueB2.setTeam(B);
		jpaTeamRepository.saveAll(Arrays.asList(A,B));
	}

	@Test
	public void diplay()
	{
		System.out.println(jpaTeamRepository.findById(1L));
	}

}
