package com.example.clothes;

import com.example.clothes.model.Clothe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClotheTest {

    Clothe test;

    @Before
    public void setUp() {
        test=new Clothe();
    }

    @Test
    public void setName() {
        assertEquals(test.setName(null),false); //CP-Clothe-01
        assertEquals(test.getName(),null);

        assertEquals(test.setName(""),false); //CP-Clothe-02
        assertEquals(test.getName(),null);

        assertEquals(test.setName("Jersey"),true); //CP-Clothe-03
        assertEquals(test.getName(),"Jersey");
    }

    @Test
    public void setPrice() {
        assertEquals(test.setPrice(null),false); //CP-Clothe-01
        assertEquals(test.getPrice(),null);

        assertEquals(test.setPrice(-1),false); //CP-Clothe-02
        assertEquals(test.getPrice(),null);

        assertEquals(test.setPrice(10),true); //CP-Clothe-03
        assertEquals(test.getPrice(),new Integer(10));
    }

    @Test
    public void setSize() {
        assertEquals(test.setSize(null),false); //CP-Clothe-01
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize(""),false); //CP-Clothe-02
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize("P"),false); //CP-Clothe-03
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize("XS"),true); //CP-Clothe-04
        assertEquals(test.getSize(),"XS");

    }

    @Test
    public void setDescription() {
        assertEquals(test.setDescription(null),false); //CP-Clothe-01
        assertEquals(test.getDescription(),null);

        assertEquals(test.setDescription(""),false); //CP-Clothe-02
        assertEquals(test.getDescription(),null);

        assertEquals(test.setDescription("Un Jersey muy viejo"),true); //CP-Clothe-03
        assertEquals(test.getDescription(),"Un Jersey muy viejo");
    }

    @Test
    public void setPurchaseDate() {
        assertEquals(test.setPurchaseDate(null),false); //CP-Clothe-01
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate(""),false); //CP-Clothe-02
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("100/100/100"),false); //CP-Clothe-03
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/100/100"),false); //CP-Clothe-04
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/100/2001"),false); //CP-Clothe-05
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/10/2001"),true); //CP-Clothe-06
        assertEquals(test.getPurchaseDate(),"10/10/2001");

    }
}