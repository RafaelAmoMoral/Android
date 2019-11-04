package com.example.clothes.presenter;

import com.example.clothes.interfaces.IForm;
import com.example.clothes.interfaces.Ilist;

public class FormPresenter implements IForm.Presenter {

    //Variable de tipo interface IForm.View la cúal une el presentador con la vista.
    private  IForm.View view;

    public FormPresenter(IForm.View view){
        this.view=view;
    }

    /**
     * Método llamado por la clase ActivityForm al pulsar el botón con id saveData. Este métood se
     * encarga de llamar al método de la vista showMainList(). En un futuro será el encargado de llamar al
     * modelo para guardar los datos.
     */
    @Override
    public void onClickSaveData() {
        view.showMainList();
    }
}
