package com.example.clothes.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.clothes.interfaces.ISearch;
import com.example.clothes.model.Clothe;
import com.example.clothes.model.ClotheDAO;

import java.util.ArrayList;
import java.util.List;


public class SearchPresenter implements ISearch.Presenter {

    private ISearch.View view;

    public SearchPresenter(ISearch.View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClickSearchData(Clothe c) {
        ClotheDAO clothe = new ClotheDAO(c);
        try {
            List<Clothe> clothes = clothe.findBy();
            view.showMainList(new ArrayList<>(clothes)); // Pasando por parametro los datos encontrados.
        } catch (Exception e) {
            // Decir que hubo un parametro errorneo
            e.printStackTrace();
        }
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
        boolean valid = date.matches("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$");
        view.setDateError(valid ? "" : "Formato de fecha permitido: dd/mm/yyyy");
        return valid;
    }
}
