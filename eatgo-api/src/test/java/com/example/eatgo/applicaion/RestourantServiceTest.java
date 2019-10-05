package com.example.eatgo.applicaion;

import com.example.eatgo.domain.*;
import com.example.eatgo.domain.MenuItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        menuItems.add(MenuItem.builder()
            .name("Kimchi")
            .build());

        given(menuItemRepository.findAllByRestourantId(1004L)).willReturn(menuItems);
    }

    private void MockRestourantRepository() {
        List<Restourant> restourants = new ArrayList<>();
        Restourant restourant = Restourant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

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

    @Test
    public void addRestourant(){
        given(restourantRepository.save(any())).will(invocation -> {
            Restourant restourant = invocation.getArgument(0);
            restourant.setId(1234L);
            return restourant;
        });

        Restourant restourant = Restourant.builder()
                .name("Store")
                .address("Busan")
                .build();
//
//        Restourant saved = Restourant.builder()
//                .id(1234L)
//                .name("Store")
//                .address("Busan")
//                .build();


        Restourant created = restourantService.addRestourant(restourant);

        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestourant(){
        Restourant restourant = Restourant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restourantRepository.findById(1004L))
                .willReturn(Optional.of(restourant));

        restourantService.updateRestourant(1004L,"Sool zip", "Busan");

        assertThat(restourant.getName(), is("Sool zip"));
        assertThat(restourant.getAddress(), is("Busan"));
    }
}