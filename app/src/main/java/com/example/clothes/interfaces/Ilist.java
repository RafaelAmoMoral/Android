package com.example.clothes.interfaces;

public interface Ilist {

    // Interfaces anidadas de la interfaz IList

    public interface View{
        void showForm();
    }

    public interface Presenter{
        void onClickAdd();
    }
}
