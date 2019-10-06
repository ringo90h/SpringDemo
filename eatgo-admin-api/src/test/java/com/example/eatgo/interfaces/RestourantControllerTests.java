package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.RestourantService;
import com.example.eatgo.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        restourant.add(Restourant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build());

        given(restourantService.getRestourants()).willReturn(restourant);

        mvc.perform(get("/restourants"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restourant restourant = Restourant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        MenuItem menuItem = MenuItem.builder()
                .name("Kimchi")
                .build();

        restourant.setMenuItems(Arrays.asList(menuItem));

        Review review = Review.builder()
                .name("JOKER")
                .score(5)
                .description("Greate!")
                .build();

        restourant.setReviews(Arrays.asList(review));

        given(restourantService.getRestourant(1004L)).willReturn(restourant);

        mvc.perform(get("/restourants/1004")).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1004")))
                .andExpect(content().string(containsString("\"name\":\"Bob zip\"")))
                .andExpect(content().string(containsString("Kimchi")))
                .andExpect(content().string(containsString("Greate!")));
    }
    @Test
    public void detailWithNotExisted() throws Exception {
        given(restourantService.getRestourant(404L))
                .willThrow(new RestourantNotFoundException(404L));
        mvc.perform(get("/restourants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }


    @Test
    public void createWithValidData() throws Exception {
        given(restourantService.addRestourant(any())).will(invocation -> {
            Restourant restourant = invocation.getArgument(0);
            return Restourant.builder()
                    .id(1234L)
                    .name(restourant.getName())
                    .address(restourant.getAddress())
                    .build();
        });

        mvc.perform(post("/restourants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"China\",\"address\":\"Busan\"}"))
                .andExpect(status().isCreated());
        //.andExpect(header().string("location","/restourant/1234"));
        //.andExpect(content().string("{}"));

        verify(restourantService).addRestourant(any());

    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(post("/restourants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
        //.andExpect(header().string("location","/restourant/1234"));
        //.andExpect(content().string("{}"));

        //verify(restourantService).addRestourant(any());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(patch("/restourants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"JOKER\",\"address\":\"Busan\"}"))
                .andExpect(status().isOk());

        verify(restourantService).updateRestourant(1004L, "JOKER", "Busan");
    }

    @Test
    public void updateWithInValidData() throws Exception {
        mvc.perform(patch("/restourants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void updateWithInValidName() throws Exception {
        mvc.perform(patch("/restourants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"address\":\"Busan\"}"))
                .andExpect(status().isBadRequest());
    }
}
