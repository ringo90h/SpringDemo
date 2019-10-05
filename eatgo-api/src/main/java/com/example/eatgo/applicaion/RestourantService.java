package com.example.eatgo.applicaion;


import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import com.example.eatgo.domain.Restourant;
import com.example.eatgo.domain.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestourantService {
    @Autowired
    RestourantRepository restourantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    public RestourantService(RestourantRepository restourantRepository, MenuItemRepository menuItemRepository) {
        this.restourantRepository = restourantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<Restourant> getRestourants() {
        List<Restourant> restoruants = restourantRepository.findAll();
        return restoruants;
    }

    public Restourant getRestourant(Long id){
        Restourant restourant = restourantRepository.findById(id).orElse(null);
        List<MenuItem> menuItems = menuItemRepository.findAllByRestourantId(id);
        restourant.setMenuItems(menuItems);

        return restourant;
    }

    public Restourant addRestourant(Restourant restourant) {
        return restourantRepository.save(restourant);
    }
}
