package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.RestourantService;
import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import com.example.eatgo.domain.Restourant;
import com.example.eatgo.domain.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class RestourantController {
    //컨트롤러 -> 앱 서비스의 메소드와 매칭되어 전달자 역할
    //실질적인 처리는 앱 서비스 클래스에서 수행

    @Autowired
    private RestourantService restourantService;

    // [GET]/restourants -> 레스토랑의 리스트를 리턴
    @GetMapping("/restourants")
    public List<Restourant> list(){
        return restourantService.getRestourants();
    }

    // [GET]/restourants/{id} -> 레스토랑의 정보 반환
    @GetMapping("/restourants/{id}")
    public Restourant detail(@PathVariable("id") Long id){

        Restourant restourant = restourantService.getRestourant(id);
        return restourant;
    }

    // [POST]/restourants -> request의 내용을 읽어서
    @PostMapping("/restourants")
    public ResponseEntity<?> create(@Valid @RequestBody Restourant resource) throws URISyntaxException {

        Restourant restorant = Restourant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();

        restourantService.addRestourant(restorant);
        URI location = new URI("/restourant/" + restorant.getId());

        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restourants/{id}")
    public String update(@PathVariable("id") Long id,@Valid @RequestBody Restourant resource){
        String name = resource.getName();
        String address = resource.getAddress();

        restourantService.updateRestourant(id, name, address);
        return "{}";
    }
}
