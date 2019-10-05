package com.example.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestourantTests {
    @Test
    public void creation(){
        Restourant restorant = new Restourant(1004L,"Bob zip","Seoul");
        assertThat(restorant.getName(), is("Bob zip"));
        assertThat(restorant.getAddress(), is("Seoul"));
    }

    @Test
    public void information(){
        Restourant restoruant = new Restourant(1004L,"Bob zip","Seoul");

        assertThat(restoruant.getInformation(), is("Bob zip in Seoul"));
    }
}