package com.example.clothes.presenter;

import com.example.clothes.interfaces.Ilist;
import com.example.clothes.model.Clothe;
import com.example.clothes.model.ClotheDAO;

import java.util.List;

public class ListPresenter implements Ilist.Presenter {

    // Variable de tipo interface Ilist.View la cúal une el presentador con la vista.
   private  Ilist.View view;
   private ClotheDAO clotheDAO;

    public ListPresenter(Ilist.View view){
        this.view=view;
        this.clotheDAO=new ClotheDAO();
    }

    @Override
    public List<Clothe> getClothes(){
        return clotheDAO.getAllClothes();
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
