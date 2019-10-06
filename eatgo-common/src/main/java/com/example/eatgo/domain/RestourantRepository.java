package com.example.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestourantRepository extends CrudRepository<Restourant, Long> {
    List<Restourant> findAll();

    Optional<Restourant> findById(Long id);

    Restourant save(Restourant restourant);
}
