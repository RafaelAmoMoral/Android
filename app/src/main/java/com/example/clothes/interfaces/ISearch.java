package com.example.clothes.interfaces;

import com.example.clothes.model.Clothe;

import java.util.List;

public interface ISearch {

    // Interfaces anidadas de la interfaz IForm

    interface View {
        void showMainList();
    }

    interface Presenter {
        void onClickSearchData(Clothe c);
    }

}
