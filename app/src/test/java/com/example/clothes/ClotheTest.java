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
        assertEquals(test.setName(null),false); //1
        assertEquals(test.getName(),null);

        assertEquals(test.setName(""),false); //2
        assertEquals(test.getName(),null);

        assertEquals(test.setName("Jersey"),true); //3
        assertEquals(test.getName(),"Jersey");
    }

    @Test
    public void setPrice() {
        assertEquals(test.setPrice(null),false); //1
        assertEquals(test.getPrice(),null);

        assertEquals(test.setPrice(-1),false); //2
        assertEquals(test.getPrice(),null);

        assertEquals(test.setPrice(10),true); //3
        assertEquals(test.getPrice(),new Integer(10));
    }

    @Test
    public void setSize() {
        assertEquals(test.setSize(null),false); //1
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize(""),false); //2
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize("P"),false); //3
        assertEquals(test.getSize(),null);

        assertEquals(test.setSize("XS"),true); //4
        assertEquals(test.getSize(),"XS");

    }

    @Test
    public void setDescription() {
        assertEquals(test.setDescription(null),false); //1
        assertEquals(test.getDescription(),null);

        assertEquals(test.setDescription(""),false); //2
        assertEquals(test.getDescription(),null);

        assertEquals(test.setDescription("Un Jersey muy viejo"),true); //3
        assertEquals(test.getDescription(),"Un Jersey muy viejo");
    }

    @Test
    public void setPurchaseDate() {
        assertEquals(test.setPurchaseDate(null),false); //1
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate(""),false); //2
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("100/100/100"),false); //3
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/100/100"),false); //4
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/100/2001"),false); //5
        assertEquals(test.getPurchaseDate(),null);

        assertEquals(test.setPurchaseDate("10/10/2001"),true); //6
        assertEquals(test.getPurchaseDate(),"10/10/2001");

    }
}