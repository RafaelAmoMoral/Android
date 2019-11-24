package com.example.clothes.presenter;

import com.example.clothes.interfaces.ISearch;
import com.example.clothes.model.Clothe;


public class SearchPresenter implements ISearch.Presenter {

    private ISearch.View view;

    public SearchPresenter(ISearch.View view) {
        this.view=view;
    }

    @Override
    public void onClickSearchData(Clothe c) {
        //Buscaríamos la prenda
        view.showMainList(); // Pasando por parametro los datos encontrados.
    }
}
