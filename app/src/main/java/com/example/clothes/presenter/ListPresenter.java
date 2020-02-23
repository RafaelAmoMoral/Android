package com.example.clothes.presenter;

import android.annotation.SuppressLint;

import com.example.clothes.interfaces.Ilist;
import com.example.clothes.model.Clothe;
import com.example.clothes.model.ClotheDAO;
import com.example.clothes.view.ActivityList.ActivityList;

import java.util.List;

public class ListPresenter implements Ilist.Presenter {

   private  Ilist.View view;

    public ListPresenter(Ilist.View view){
        this.view=view;
    }

    @SuppressLint("NewApi")
    @Override
    public List<Clothe> getClothes(){
        ClotheDAO cDAO = new ClotheDAO();
        return cDAO.getClothes();
    }

    @Override
    public Clothe getClothe(Integer id){
        ClotheDAO cDAO = new ClotheDAO(id);
        return cDAO.getClothe();
    }

    @Override
    public void onClotheClicked(Clothe selectedClothe) {
        view.displayFormClothe(selectedClothe, ActivityList.UPDATE_CLOTHE);
    }

    /**
     * Método llamado por la clase ActivityList al pulsar el botón con id listadofb. Este métood se
     * encarga de llamar al método de la vista showForm().
     */
    @Override
    public void onClickAdd() {
        view.displayFormClothe(null, ActivityList.ADD_CLOTHE);
    }

    @Override
    public void onClotheSwipped(int clotheSelected) {
        view.showDeleteClotheDialog(clotheSelected);
    }

    @Override
    public boolean removeClothe(int id){
        ClotheDAO clothe=new ClotheDAO(id);
        return clothe.remove();
    }
}
