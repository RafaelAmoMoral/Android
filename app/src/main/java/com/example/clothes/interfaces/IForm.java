package com.example.clothes.interfaces;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.clothes.model.Clothe;

public interface IForm {

    // Interfaces anidadas de la interfaz IForm

    interface View{
        void openGallery();
        void requestPermmission();
        void showMainList();
        void setNameError(String error);
        void setPriceError(String error);
        void setSizeError(String error);
        void setDescriptionError(String error);
        void setDateError(String error);
    }

    interface Presenter{
        void resultPermissions(int[] grantResults);
        void onClickImage(Context context);
        void onClickSaveData(Clothe c);
        void onClickRemoveData(Clothe c);
        boolean isFormValid(Clothe formClothe);
        boolean testName(String name);
        boolean testPrice(String price);
        boolean testSize(String size);
        boolean testDescription(String description);
        boolean testDate(String date);
    }

}
