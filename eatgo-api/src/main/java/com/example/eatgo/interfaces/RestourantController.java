package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.RestourantService;
import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import com.example.eatgo.domain.Restourant;
import com.example.eatgo.domain.RestourantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class RestourantController {
    //private RestourantRepositoryImpl repository = new RestourantRepository();
    //@Component와 상응

    private RestourantRepository restourantRepository;
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
    @Autowired
    private RestourantService restourantService;

    @GetMapping("/restourants")
    public List<Restourant> list(){
        return restourantService.getRestourants();
    }

    @GetMapping("/restourants/{id}")
    public Restourant detail(@PathVariable("id") Long id){
        Restourant restourant = restourantService.getRestourant(id);
        //기본 정보 + 메뉴 정보

        //Restourant restourant = restourantRepository.findById(id);

        return restourant;
    }

    @PostMapping("/restourants")
    public ResponseEntity<?> create(@RequestBody Restourant resource) throws URISyntaxException {
        String name = resource.getName();
        String address = resource.getAddress();

        Restourant restorant = new Restourant(name, address);
        restourantService.addRestourant(restorant);
        URI location = new URI("/restourant/" + restorant.getId());

        return ResponseEntity.created(location).body("");
    }
}
