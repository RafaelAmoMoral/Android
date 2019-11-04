package com.example.clothes.presenter;

import com.example.clothes.interfaces.Ilist;

public class ListPresenter implements Ilist.Presenter {

    // Variable de tipo interface Ilist.View la cúal une el presentador con la vista.
   private  Ilist.View view;

    public ListPresenter(Ilist.View view){
        this.view=view;
    }


    /**
     * Método llamado por la clase ActivityList al pulsar el botón con id listadofb. Este métood se
     * encarga de llamar al método de la vista showForm().
     */
    @Override
    public void onClickAdd() {
        view.showForm();
    }
}
