package com.ospyn.repository;

import com.ospyn.models.Clue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaClueRepository extends CrudRepository<Clue,Long> {
    List<Clue> findByUuidAndPassword(String uuid,String password);


}
