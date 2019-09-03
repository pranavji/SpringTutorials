package com.ospyn.repository;

import com.ospyn.models.Clue;
import org.springframework.data.repository.CrudRepository;

public interface JpaClueRepository extends CrudRepository<Clue,Long> {
}
