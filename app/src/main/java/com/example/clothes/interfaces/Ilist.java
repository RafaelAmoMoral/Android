package com.example.clothes.interfaces;

import android.view.MenuItem;

import com.example.clothes.model.Clothe;

import java.util.List;

public interface Ilist {

    // Interfaces anidadas de la interfaz IList

    interface View{
        void showForm();
        void about(MenuItem menuItem);
        void search(MenuItem searchItem);
    }

    interface Presenter{
        void onClickAdd();
        List<Clothe> getClothes();
    }
}
