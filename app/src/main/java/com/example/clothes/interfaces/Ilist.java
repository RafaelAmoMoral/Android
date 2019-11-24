package com.example.clothes.interfaces;

import android.view.MenuItem;

public interface Ilist {

    // Interfaces anidadas de la interfaz IList

    interface View{
        void showForm();
        void about(MenuItem menuItem);
        void search(MenuItem searchItem);
    }

    interface Presenter{
        void onClickAdd();
    }
}
