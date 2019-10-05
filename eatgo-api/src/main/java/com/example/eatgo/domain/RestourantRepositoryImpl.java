package com.example.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RestourantRepositoryImpl{
//public class RestourantRepositoryImpl implements RestourantRepository{

    private List<Restourant> restourants = new ArrayList<>();

    public RestourantRepositoryImpl(){
        restourants.add(new Restourant(1004L,"Bob zip", "Seoul"));
        restourants.add(new Restourant(2020L,"Bob zip", "Seoul"));
    }

    //@Override
    public List<Restourant> findAll() {
        return restourants;
    }

    //@Override
//    public Optional<Restourant> findById(Long id) {
//        return restourants.stream()
//                .filter(r -> r.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }

    //@Override
    public Restourant save(Restourant restourant) {
        restourant.setId(1234L);
        restourants.add(restourant);
        return restourant;
    }


}
