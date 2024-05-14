package com.batch.springBatch.service;

import com.batch.springBatch.entities.Person;

import java.util.List;

public interface IPersonService {
    public void saveAll(List<Person> personList);
}
