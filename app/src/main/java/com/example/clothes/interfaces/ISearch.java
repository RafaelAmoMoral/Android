package com.example.clothes.interfaces;

import com.example.clothes.model.Clothe;

import java.util.ArrayList;

public interface ISearch {

    // Interfaces anidadas de la interfaz IForm

    interface View {
        void showMainList(ArrayList<Clothe> clothes);
        void setDateError(String error);
    }

    interface Presenter {
        void onClickSearchData(Clothe c);
        boolean testDate(String date);
    }

}
