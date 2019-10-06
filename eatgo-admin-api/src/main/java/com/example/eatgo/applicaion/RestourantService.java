package com.example.eatgo.applicaion;


import com.example.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestourantService {

    //Autowired를 통해 new RestourantRepository() 생략
    RestourantRepository restourantRepository;
    MenuItemRepository menuItemRepository;
    ReviewRepository reviewRepository;

    //생성자를 통해 의존성 주입
    @Autowired
    public RestourantService(RestourantRepository restourantRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
        this.restourantRepository = restourantRepository;
        this.menuItemRepository = menuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    //getRestourants: 레스토랑의 정보들을 리스트 형태로 반환
    public List<Restourant> getRestourants() {
        List<Restourant> restoruants = restourantRepository.findAll();
        return restoruants;
    }

    //getRestourant(id): 레스토랑의 정보를 반환
    public Restourant getRestourant(Long id){
        //restourant가 Obtional 객체이기 때문에 null처리 꼭 해주어야함
        Restourant restourant = restourantRepository.findById(id).orElseThrow(()->new RestourantNotFoundException(id));

        //레스토랑의 아이디를 가지고 있는 메뉴를 불러온다
        List<MenuItem> menuItems = menuItemRepository.findAllByRestourantId(id);
        restourant.setMenuItems(menuItems);

        //레스토랑의 아이디를 가지고 있는 리뷰를 불러온다.
        List<Review> reviews = reviewRepository.findAllByRestourantId(id);
        restourant.setReviews(reviews);

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
