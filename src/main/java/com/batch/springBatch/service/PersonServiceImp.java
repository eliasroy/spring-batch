package com.batch.springBatch.service;

import com.batch.springBatch.entities.Person;
import com.batch.springBatch.persitence.IPersonDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImp implements IPersonService{
    private final IPersonDAO personRepository;
    @Override
    @Transactional
    public void saveAll(List<Person> personList) {
        personRepository.saveAll(personList);
        log.info("save success");
    }
}
