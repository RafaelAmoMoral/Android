package com.example.clothes.interfaces;

import android.view.MenuItem;

import com.example.clothes.model.Clothe;

import java.util.List;

public interface Ilist {

    // Interfaces anidadas de la interfaz IList

    interface View {
        void about(MenuItem menuItem);

        void search(MenuItem searchItem);

        void displayFormClothe(Clothe selectedClothe, int formMode);

        void showDeleteClotheDialog(int clotheSelected);
    }

    interface Presenter {
        void onClickAdd();

        List<Clothe> getClothes();

        Clothe getClothe(Integer id);

        void onClotheClicked(Clothe selectedClothe);

        void onClotheSwipped(int clothSelected);

        boolean removeClothe(int id);
    }
}
