package com.example.clothes.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.example.clothes.interfaces.IForm;
import com.example.clothes.model.Clothe;
import com.example.clothes.model.ClotheDAO;
import com.example.clothes.PermissionsManager;
import com.example.clothes.view.Utils.Images;

import java.util.ArrayList;
import java.util.List;

public class FormPresenter implements IForm.Presenter {

    //Variable de tipo interface IForm.View la cúal une el presentador con la vista.
    private IForm.View view;

    public FormPresenter(IForm.View view) {
        this.view = view;
    }

    @Override
    public void onClickImage(Context context, Activity a) {
        int readPermmissions = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermmissions != PackageManager.PERMISSION_GRANTED) {
            PermissionsManager pm=PermissionsManager.getInstance();
            pm.getPermission(a);
        } else {
            view.openGallery();
        }
    }

    public void onClickSaveData(Clothe c) {
        ClotheDAO clothe = new ClotheDAO(c);
        Long id= null;
        try {
            id = clothe.insert();
            c.setId(id != null ? id.intValue() : null);
            view.displayMainActivity(c, Activity.RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            //Mensaje de error
        }
    }

    @Override
    public void onClickRemoveData(Clothe c, Integer activityCode) {
        ClotheDAO clothe = new ClotheDAO(c);
        clothe.remove();
        view.displayMainActivity(c, activityCode);
    }

    @Override
    public void onClickUpdateClothe(Clothe c, Integer activityCode) {
        ClotheDAO clothe = new ClotheDAO(c);
        clothe.update();
        view.displayMainActivity(c, activityCode);
    }

}
