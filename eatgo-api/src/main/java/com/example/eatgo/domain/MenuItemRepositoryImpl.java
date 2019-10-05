package com.example.eatgo.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuItemRepositoryImpl{
//public class MenuItemRepositoryImpl implements MenuItemRepository{
    private List<MenuItem> menuItems = new ArrayList();

    public MenuItemRepositoryImpl(){
        menuItems.add(new MenuItem("Kimchi"));
    }

    //@Override
    public List<MenuItem> findAllByRestourantId(Long restourantId){
        return menuItems;
    }
}
