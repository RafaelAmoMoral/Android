package com.example.clothes.interfaces;

import android.app.Activity;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.clothes.model.Clothe;

public interface IForm {

    // Interfaces anidadas de la interfaz IForm

    interface View{
        void openGallery();
        void displayMainActivity(Clothe c, Integer activityCode);
        void setNameError(String error);
        void setPriceError(String error);
        void setSizeError(String error);
        void setDescriptionError(String error);
        void setDateError(String error);
    }

    interface Presenter{
        void onClickImage(Context context, Activity a);
        void onClickSaveData(Clothe c);
        void onClickUpdateClothe(Clothe c, Integer activityCode);
        void onClickRemoveData(Clothe c, Integer activityCode);
    }

}
