package com.example.clothes.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.clothes.R;
import com.example.clothes.interfaces.IForm;
import com.example.clothes.presenter.FormPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FormActivity extends AppCompatActivity implements IForm.View{

    public static final String TAG = "Clothes/FormActivity";
    private static IForm.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter=new FormPresenter(this);

        setContentView(R.layout.activity_form);
        // En estas dos líneas se crea el toolbar del form
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        // Por último obtenermos el ActionBar y añadimos el botón up.
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
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
}
