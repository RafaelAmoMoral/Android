package com.example.clothes.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.example.clothes.interfaces.IForm;
import com.example.clothes.interfaces.Ilist;
import com.example.clothes.model.Clothe;
import com.example.clothes.view.FormActivity;
import com.google.android.material.snackbar.Snackbar;

public class FormPresenter implements IForm.Presenter {

    //Variable de tipo interface IForm.View la cúal une el presentador con la vista.
    private IForm.View view;

    public FormPresenter(IForm.View view) {
        this.view = view;
    }

    @Override
    public void onClickImage(Context context) {
        int readPermmissions = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readPermmissions != PackageManager.PERMISSION_GRANTED) {
            this.view.requestPermmission();
        } else {
            view.openGallery();
        }
    }

    @Override
    public void resultPermissions(int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            view.openGallery();
        } else {
            view.presentePermissionsSnackBar();
        }
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

    /**
     * Método llamado al clicar en el botón añadir, el cuál comprueba si el formulario es válido para
     * poder añadir la nueva prenda a la bbdd.
     * Para comprobar si el formulario es válido este método se ayuda de métodos que comprueban de
     * forma atómica acada Input.
     *
     * @param formClothe Prenda del formulario
     * @return true si el formulario es válido.
     */
    public boolean isFormValid(Clothe formClothe) {
        boolean valid = true;

        valid = valid == false ? valid : testName(formClothe.getName());
        valid = valid == false ? valid : testPrice(String.valueOf(formClothe.getPrice()));
        valid = valid == false ? valid : testSize(formClothe.getSize());
        valid = valid == false ? valid : testDescription(formClothe.getDescription());
        valid = valid == false ? valid : testDate(formClothe.getPurchaseDateFormated());

        return valid;
    }


    /**
     * Método llamado al salir del foco del EditText del nombre de la prenda en el formulario.
     * Este método cumple dos funciones:
     * - A de devolver un booleano para la validación del conjunto del formulario.
     * - LLama a la vista para cambiar el error del editText si no es válido el contenido.
     *
     * @param name Nombre de la prenda del formulario.
     * @return true si la cadena no esta vacía.
     */
    @Override
    public boolean testName(String name) {
        boolean valid = !name.isEmpty();
        view.setNameError(valid ? "" : "El campo es obligatorio");
        return valid;
    }

    /**
     * Método llamado al salir del foco del EditText del precio de la prenda en el formulario.
     * Este método cumple dos funciones:
     * - A de devolver un booleano para la validación del conjunto del formulario.
     * - LLama a la vista para cambiar el error del editText si no es válido el contenido.
     *
     * @param price Precio de la prenda del formulario.
     * @return true si el numero no es negativo.
     */
    @Override
    public boolean testPrice(String price) {
        boolean valid = !price.isEmpty();
        String error;
        if (!valid) {
            try {
                valid = !(Integer.parseInt(price) < 0);
                error = (valid ? "" : "El precio no puede ser negativo");
            } catch (NumberFormatException nfe) {
                error = "Formato de numero inválido";
            }
        } else {
            error = "";
        }
        view.setPriceError(error);
        return valid;
    }

    /**
     * Método llamado al salir del foco del EditText de la talla de la prenda en el formulario.
     * Este método cumple dos funciones:
     * - A de devolver un booleano para la validación del conjunto del formulario.
     * - LLama a la vista para cambiar el error del editText si no es válido el contenido.
     *
     * @param size Talla de la prenda del formulario.
     * @return true si la talla se encuentra dentro de las tallas permitidas.
     */
    @Override
    public boolean testSize(String size) {
        String[] tallas = {"S", "XS", "M", "XM", "XL"};

        boolean valid = false;
        for (int i = 0; i < tallas.length && !valid; i++) {
            if (tallas[i].equals(size.toUpperCase())) {
                valid = true;
            }
        }
        view.setSizeError(valid ? "" : "Tallas disponibles: 'S', 'XS', 'M', 'XM', 'XL'");
        return valid;
    }

    /**
     * Método llamado al salir del foco del EditText de la descripcion de la prenda en el formulario.
     * Este método cumple dos funciones:
     * - A de devolver un booleano para la validación del conjunto del formulario.
     * - LLama a la vista para cambiar el error del editText si no es válido el contenido.
     *
     * @param description Descripcion de la prenda del formulario.
     * @return true si la cadena no esta vacia.
     */
    @Override
    public boolean testDescription(String description) {
        boolean valid=!description.isEmpty();
        view.setDescriptionError(valid ? "" : "El campo es obligatorio");
        return valid;
    }

    /**
     * Método llamado al salir del foco del EditText de la fecha de la prenda en el formulario.
     * Este método cumple dos funciones:
     * - A de devolver un booleano para la validación del conjunto del formulario.
     * - LLama a la vista para cambiar el error del editText si no es válido el contenido.
     *
     * @param date Fecha de la prenda del formulario.
     * @return true si la fecha cumple con el formato dd/mm/yyyy.
     */
    @Override
    public boolean testDate(String date) {
        boolean valid=date.matches("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$");
        view.setDateError(valid?"":"Formato de fecha permitido: dd/mm/yyyy");
        return valid;
    }
}
