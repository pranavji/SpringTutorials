package com.ospyn.repository;

import com.ospyn.models.Clue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaClueRepository extends CrudRepository<Clue,Long> {
    List<Clue> findByUuidAndPassword(String uuid,String password);
}
