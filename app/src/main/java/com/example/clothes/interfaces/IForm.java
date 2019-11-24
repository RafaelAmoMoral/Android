package com.example.clothes.interfaces;

import com.example.clothes.model.Clothe;

public interface IForm {

    // Interfaces anidadas de la interfaz IForm

    interface View{
        void showMainList();
        void setNameError(String error);
        void setPriceError(String error);
        void setSizeError(String error);
        void setDescriptionError(String error);
        void setDateError(String error);
    }

    interface Presenter{
        void onClickSaveData(Clothe c);
        void onClickRemoveData(Clothe c);
        void testName(String name);
        void testPrice(String price);
        void testSize(String size);
        void testDescription(String description);
        void testDate(String date);
    }

}
