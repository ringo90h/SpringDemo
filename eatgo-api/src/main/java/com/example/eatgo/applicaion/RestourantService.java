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

    //Autowired를 통해 new RestourantRepository() 생략
    @Autowired
    RestourantRepository restourantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;

    //생성자를 통해 의존성 주입
    public RestourantService(RestourantRepository restourantRepository, MenuItemRepository menuItemRepository) {
        this.restourantRepository = restourantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    //getRestourants: 레스토랑의 정보들을 리스트 형태로 반환
    public List<Restourant> getRestourants() {
        List<Restourant> restoruants = restourantRepository.findAll();
        return restoruants;
    }

    //getRestourant(id): 레스토랑의 정보를 반환
    public Restourant getRestourant(Long id){
        //restourant가 Obtional 객체이기 때문에 null처리 꼭 해주어야함
        Restourant restourant = restourantRepository.findById(id).orElse(null);
        List<MenuItem> menuItems = menuItemRepository.findAllByRestourantId(id);
        restourant.setMenuItems(menuItems);

        return restourant;
    }

    public Restourant addRestourant(Restourant restourant) {
        return restourantRepository.save(restourant);
    }

    public Restourant updateRestourant(long id, String name, String address) {
        //TODO: update Restourant
        Restourant restourant = restourantRepository.findById(id).orElse(null);

        restourant.updateInformation(name, address);

        return restourant;
    }
}
