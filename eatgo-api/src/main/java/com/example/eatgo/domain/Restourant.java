package com.example.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restourant {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

//    private String regionName;
//    private String categoryName;

    @Transient
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    public String getInformation() {
        return name + " in "+ address;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }
    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
