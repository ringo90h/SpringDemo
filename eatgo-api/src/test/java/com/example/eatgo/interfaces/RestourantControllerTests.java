package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.RestourantService;
import com.example.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//레스토랑 컨트롤러를 테스트한다고 명시
@RunWith(SpringRunner.class)
@WebMvcTest(RestourantController.class)
public class RestourantControllerTests {
    @Autowired
    private MockMvc mvc;

    /*
    //테스트에서는 자동 의존성주입이 되지 않기 때문에 매뉴얼 생성 필요
    //컨트롤러에 직접 의존성 주입
    @SpyBean(RestourantService.class)
    private RestourantService restourantService;

    @SpyBean(RestourantRepositoryImpl.class)
    private RestourantRepository restourantRepository;

    @SpyBean(MenuItemRepositoryImpl.class)
    private MenuItemRepository menuitemRepository;
    */
    //Mock으로 가짜를 투입한다 .
    @MockBean
    private RestourantService restourantService;



    @Test
    public void list() throws Exception {
        //목 오브젝트를 쓰면 컨트롤러가 가짜 정보를 통해 접근한다.
        List<Restourant> restourant = new ArrayList<>();
        restourant.add(new Restourant(1004L, "Bob zip","Seoul"));

        given(restourantService.getRestourants()).willReturn((restourant));

        mvc.perform(get("/restourants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
    }

    @Test
    public void detail() throws Exception {
        Restourant restourant = new Restourant(1004L, "Bob zip","Seoul");
        restourant.addMenuItem(new MenuItem("Kimchi"));

        Restourant restourant2 = new Restourant(2020L, "Bob zip","Seoul");
        restourant2.addMenuItem(new MenuItem("Kimchi"));

        given(restourantService.getRestourant(1004L)).willReturn((restourant));
        given(restourantService.getRestourant(2020L)).willReturn((restourant2));

        mvc.perform(get("/restourants/1004")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("Kimchi")));

        mvc.perform(get("/restourants/2020")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":2020")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
    }


    @Test
    public void create() throws Exception {

        mvc.perform(post("/restourants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"China\",\"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restourant/1234"))
                .andExpect(content().string("{}"));

        verify(restourantService).addRestourant(any());

    }
}
