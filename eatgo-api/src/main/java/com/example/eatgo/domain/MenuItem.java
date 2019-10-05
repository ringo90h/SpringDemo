package com.example.eatgo.domain;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class MenuItem{

    @Id
    @GeneratedValue
    private Long id;

    private Long restourantId;

    private final String ItemName;

    public MenuItem(String ItemName){
        this.ItemName = ItemName;
    }

    public String getName(){
        return ItemName;
    }

}
