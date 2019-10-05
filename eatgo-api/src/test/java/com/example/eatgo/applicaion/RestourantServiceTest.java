package com.example.eatgo.applicaion;

import com.example.eatgo.domain.*;
import com.example.eatgo.domain.MenuItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestourantServiceTest {

    private RestourantService restourantService;

    @Mock
    private RestourantRepository restourantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;

    //각 테스트 시행 전 무조건 실행되는 부분
    @Before
    public void setUp(){
        //목 어노테이션을 초기화시킴
        MockitoAnnotations.initMocks(this);

        MockRestourantRepository();
        mockMenuItemReposity();
//        restourantRepository = new RestourantRepositoryImpl();
//        menuItemRepository = new MenuItemRepositoryImpl();
        restourantService = new RestourantService(restourantRepository, menuItemRepository);
    }

    private void mockMenuItemReposity() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Kimchi"));

        given(menuItemRepository.findAllByRestourantId(1004L)).willReturn(menuItems);
    }

    private void MockRestourantRepository() {
        List<Restourant> restourants = new ArrayList<>();
        Restourant restourant = new Restourant(1004L,"Bob zip", "Seoul");
        restourants.add(restourant);
        given(restourantRepository.findAll()).willReturn(restourants);
        given(restourantRepository.findById(1004L))
                .willReturn(Optional.of(restourant));
    }

    @Test
    public void getRestourants(){
        List<Restourant> restourants = restourantService.getRestourants();

        Restourant restourant = restourants.get(0);
        assertThat(restourant.getId(), is(1004L));
    }

    @Test
    public void getRestourant(){
        Restourant restourant =  restourantService.getRestourant(1004L);
        assertThat(restourant.getId(), is(1004L));

        MenuItem menuItem = restourant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));
    }
//
//    @Test
//    public void addRestourant(){
//        Restourant restourant = new Restourant("Store", "Busan");
//        Restourant saved = new Restourant(1234L,"Store", "Busan");
//
//        given(restourantRepository.save(any())).willReturn(restourant);
//
//        Restourant created = restourantService.addRestourant(restourant);
//
//        assertThat(created.getId(), is(1234L));
//    }
}