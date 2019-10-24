package com.example.clothes.presenter;

import com.example.clothes.interfaces.Ilist;

public class ListPresenter implements Ilist.Presenter {

   private  Ilist.View view;

    public ListPresenter(Ilist.View view){
        this.view=view;
    }


    @Override
    public void onClickAdd() {
        view.showForm();
    }
}
