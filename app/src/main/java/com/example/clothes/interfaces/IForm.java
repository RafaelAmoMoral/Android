package com.example.clothes.interfaces;

public interface IForm {

    // Interfaces anidadas de la interfaz IForm

    public interface View{
        void showMainList();
    }

    public interface Presenter{
        void onClickSaveData();
    }

}
