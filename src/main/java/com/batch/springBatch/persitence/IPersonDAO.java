package com.batch.springBatch.persitence;

import com.batch.springBatch.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonDAO extends CrudRepository<Person, Long> {
}
