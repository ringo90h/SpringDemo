package com.example.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestourantTests {
    @Test
    public void creation(){
        Restourant restorant = Restourant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        assertThat(restorant.getId(), is(1004L));
        assertThat(restorant.getName(), is("Bob zip"));
        assertThat(restorant.getAddress(), is("Seoul"));
    }

    @Test
    public void information(){
        Restourant restoruant = Restourant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();
        assertThat(restoruant.getInformation(), is("Bob zip in Seoul"));
    }
}