package com.example.clothes.view.Utils;

import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;

public class DatePicker extends AppCompatActivity implements View.OnClickListener{

    private EditText field;
    private Button btn;
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private final Calendar c = Calendar.getInstance();
    private int mes = c.get(Calendar.MONTH);
    private int dia = c.get(Calendar.DAY_OF_MONTH);
    private int anio = c.get(Calendar.YEAR);
    private final int actualMes = c.get(Calendar.MONTH);
    private final int actualDia = c.get(Calendar.DAY_OF_MONTH);
    private final int actualAnio = c.get(Calendar.YEAR);

    public DatePicker(EditText field, Button btn) {
        this.field = field;
        this.btn=btn;
        this.btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         obtenerFecha();
    }

    private void obtenerFecha() {
        final DatePickerDialog recogerFecha = new DatePickerDialog(field.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                anio=year;
                month=mes;
                dia=dayOfMonth;
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                field.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        }, actualAnio, actualMes, actualDia);
        //Muestro el widget
        recogerFecha.show();
    }

    public boolean isItFuture(){
        boolean isFuture=false;

        if(actualAnio<this.anio){
            isFuture=true;
        }else{
            if(actualMes<this.mes){
                isFuture=true;
            }else{
                if(actualDia<this.dia ){
                    isFuture=true;
                }
            }
        }

        return isFuture;
    }

}
