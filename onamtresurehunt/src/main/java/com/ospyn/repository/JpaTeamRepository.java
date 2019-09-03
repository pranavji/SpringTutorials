package com.ospyn.repository;

import com.ospyn.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaTeamRepository extends CrudRepository<Team,Long> {

    Optional<Team> findById(Long id);

}
