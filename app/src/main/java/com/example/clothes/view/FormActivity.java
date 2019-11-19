package com.example.clothes.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.clothes.R;
import com.example.clothes.interfaces.IForm;
import com.example.clothes.presenter.FormPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity implements IForm.View{

    public static final String TAG = "Clothes/FormActivity";
    private static IForm.Presenter presenter;
    private static String[] tallas = {"S","XS","M","XM","XL"};
    private String[] arrayEstados = {"Nuevo","Seminuevo","Algo desgastado","Desgastado","Nuevo..."};
    private DatePicker datePicker;

    public FormActivity() {
        presenter=new FormPresenter(this);

    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // En estas dos líneas se crea el toolbar del form
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Por último obtenermos el ActionBar y añadimos el botón up.
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        /*En estas dos líneas se agrega un clickListener al boton con id saveButton y lo asociamos
        con el método de la clase FormPresenter onClickSaveData()*/
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Pulsando botón guardar...");
                presenter.onClickSaveData();
            }
        });

        setFormFields();
        setSpinner();

        this.datePicker = new DatePicker((EditText) findViewById(R.id.et_mostrar_hora),
                (Button) findViewById(R.id.btn_mostrar_hora));
    }

    private void setSpinner() {
        Spinner sp = (Spinner) findViewById(R.id.form_tallas_spinner);
        final List<String> estadosList = new ArrayList<>(Arrays.asList(arrayEstados));

        final ArrayAdapter spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estadosList);
        sp.setAdapter(spinnerAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Nuevo..."))
                {
                    AddItemDialog cdd=new AddItemDialog(FormActivity.this,(Spinner)parent, spinnerAdapter, estadosList);
                    cdd.show();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    /**
     * Método encargado de finalizar la vista en la pila de vistas.
     */
    @Override
    public void showMainList() {
        finish();
    }

    private void setFormFields(){
        TextInputEditText nombreEditText = (TextInputEditText) findViewById(R.id.form_name_editText);
        TextInputEditText precioEditText = (TextInputEditText) findViewById(R.id.form_price_editText);
        TextInputEditText tallaEditText = (TextInputEditText) findViewById(R.id.form_size_ediText);
        TextInputEditText descripcionEditText = (TextInputEditText) findViewById(R.id.form_description_editText);
        TextInputEditText descripcionDateText = (TextInputEditText) findViewById(R.id.et_mostrar_hora);

        nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    Log.d("AppCRUD", et.getText().toString());
                    TextInputLayout nombreInputLayout = (TextInputLayout) findViewById(R.id.form_name_inputLayout);

                    if (et.getText().toString().length()==0) {
                        nombreInputLayout.setError("El campo es obligatorio");
                    } else {
                        nombreInputLayout.setError("");
                    }
                }
            }
        });

        tallaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    Log.d("AppCRUD", et.getText().toString());
                    TextInputLayout sizeInputLayout = (TextInputLayout) findViewById(R.id.form_size_inputLayout);
                    boolean checked=false;
                    for(int i=0;i<tallas.length && !checked;i++){
                        if(tallas[i].equals(et.getText().toString().toUpperCase())){
                            checked=true;
                        }
                    }
                    if (!checked) {
                        sizeInputLayout.setError("Tallas disponibles "+"S, XS, M, XM, XL");
                    } else {
                        sizeInputLayout.setError("");
                    }
                }
            }
        });

        descripcionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    Log.d("AppCRUD", et.getText().toString());
                    TextInputLayout descripcionEditText = (TextInputLayout) findViewById(R.id.form_description_inputLayout);
                    if (et.getText().toString().length()==0) {
                        descripcionEditText.setError("El campo es obligatorio");
                    } else {
                        descripcionEditText.setError("");
                    }
                }
            }
        });





    }

}
