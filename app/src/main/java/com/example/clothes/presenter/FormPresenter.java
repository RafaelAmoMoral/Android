package com.example.clothes.presenter;

import com.example.clothes.interfaces.IForm;
import com.example.clothes.interfaces.Ilist;
import com.example.clothes.model.Clothe;

public class FormPresenter implements IForm.Presenter {

    //Variable de tipo interface IForm.View la cúal une el presentador con la vista.
    private IForm.View view;

    public FormPresenter(IForm.View view) {
        this.view = view;
    }

    public void onClickSaveData(Clothe c) {
        //Guardo datos
        view.showMainList();
    }

    @Override
    public void onClickRemoveData(Clothe c) {
        //Elimino datos
        view.showMainList();
    }

    @Override
    public void testName(String name) {
        view.setNameError(name.trim().isEmpty() ? "El campo es obligatorio" : "");
    }

    @Override
    public void testPrice(String price) {
        String error;
        if (!price.trim().isEmpty()) {
            try {
                error=(Integer.parseInt(price.trim()) < 0 ? "El precio no puede ser negativo" : "");
            }catch (NumberFormatException nfe){
                error="Formato de numero inválido";
            }
        }else{
            error="";
        }
        view.setPriceError(error);

    }

    @Override
    public void testSize(String size) {
        String[] tallas = {"S", "XS", "M", "XM", "XL"};

        boolean valid = false;
        for (int i = 0; i < tallas.length && !valid; i++) {
            if (tallas[i].equals(size.trim().toUpperCase())) {
                valid = true;
            }
        }
        view.setSizeError(!valid ? "Tallas disponibles: 'S', 'XS', 'M', 'XM', 'XL'" : "");
    }

    @Override
    public void testDescription(String description) {
        view.setDescriptionError(description.trim().isEmpty() ? "El campo es obligatorio" : "");

    }

    @Override
    public void testDate(String date) {
        if (date.trim().matches("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$")) {
            view.setDateError("");
        } else {
            view.setDateError("Formato de fecha permitido: dd/mm/yyyy");
        }
    }
}
