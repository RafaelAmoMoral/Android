package com.example.clothes.presenter;

import com.example.clothes.interfaces.IForm;
import com.example.clothes.interfaces.Ilist;

public class FormPresenter implements IForm.Presenter {

    private  IForm.View view;

    public FormPresenter(IForm.View view){
        this.view=view;
    }

    @Override
    public void onClickSaveData() {
        //Guardaríamos los datos
        view.showMainList();
    }
}
