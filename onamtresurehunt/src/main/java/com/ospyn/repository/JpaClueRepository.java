package com.ospyn.repository;

import com.ospyn.models.Clue;
import com.ospyn.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaClueRepository extends CrudRepository<Clue,Long> {
    List<Clue> findByUuidAndPassword(String uuid,String password);

    void deleteByTeam(Team teamid);

    Optional<Clue> findByUuid(String uuid);
}
