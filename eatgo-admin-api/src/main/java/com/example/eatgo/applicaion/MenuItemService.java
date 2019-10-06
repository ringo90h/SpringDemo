package com.example.eatgo.applicaion;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import com.example.eatgo.domain.Restourant;
import com.example.eatgo.domain.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class MenuItemService {
    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository){
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long restourantId, List<MenuItem> menuItems){
        for (MenuItem menuItem : menuItems){
            update(restourantId, menuItem);
        }
    }

    private void update(Long restourantId, MenuItem menuItem) {
        if(menuItem.isDestory()){
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestourantId(restourantId);
        menuItemRepository.save(menuItem);
    }
}
