package com.example.clothes.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.clothes.R;
import com.example.clothes.interfaces.IForm;
import com.example.clothes.model.Clothe;
import com.example.clothes.presenter.FormPresenter;
import com.example.clothes.view.Utils.DatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity implements IForm.View {

    public static final String TAG = "Clothes/FormActivity";
    private IForm.Presenter presenter;
    private DatePicker datePicker;

    public FormActivity() {
        presenter = new FormPresenter(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        Button saveButton = findViewById(R.id.id_search_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Pulsando botón guardar...");
                presenter.onClickSaveData(new Clothe());
            }
        });

        this.datePicker = new DatePicker((EditText) findViewById(R.id.et_mostrar_hora),
                (Button) findViewById(R.id.btn_mostrar_hora));

        setFormFields();
        setSpinner();

    }

    private void setSpinner() {
        //Obtenemos nuestro spinner
        final Spinner spinner = (Spinner) findViewById(R.id.form_spinner_states);

        /*Convertimos nuestro array en una lista, en este caso un ArrayList, con scope final para que
         * pueda ser accesible por la interfaz que implementaremos más adelante*/
        final List<String> states = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.states)));
        /* A continuación, creamos un ArrayAdapter pasándo por parámetro el contexto en el que se encuentra,
         * el recurso IMPLEMENTADO POR DEFECTO EN ANDROID android.R.layout.simple_spinner_item y nuestra lista*/
        final ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);

        //Añadimos el ArrayAdapter al spinner
        spinner.setAdapter(spinnerAdapter);

        /*Añadimos un nuevo itemListener al spinner creándo al vuelo la interfaz OnItemSelectedListener e
          implementándo sus métodos*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Obtenemos el valor  del objeto seleccionado y si es igual a nuestro Nuevo... abrimos
                nuestro dialogo*/
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Añadir...")) {
                    /*Construimos nuestro dialogo con la actividad que lo lanzará, el spinnerAdapter
                    y la lista con el contenido del spinner.*/
                    AddItemDialog dialog = new AddItemDialog(FormActivity.this, spinnerAdapter, states, spinner);
                    dialog.show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Que quiero hacer cuando no haya nada seleccionado
            }
        });
    }

    public void delete(MenuItem aboutItem) {
        createSimpleDialog().show();
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Está seguro de que desea eliminar?")
                .setMessage("Recuerde que esta decisión será permanente.")
                .setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Borrate");
                                presenter.onClickRemoveData(new Clothe());
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Me arrepenti");
                                dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_form, menu);

        /*MenuItem item = menu.findItem(R.id.id_form_menu_delete);
        if(item!=null){
            item.setVisible(false);
        }*/

        return true;
    }

    @Override
    public void showMainList() {
        finish();
    }

    private void setFormFields() {
        TextInputEditText nombreEditText = findViewById(R.id.form_name_editText);
        TextInputEditText tallaEditText = findViewById(R.id.form_size_ediText);
        TextInputEditText descripcionEditText = findViewById(R.id.form_description_editText);
        TextInputEditText priceditText = findViewById(R.id.form_price_editText);
        TextInputEditText descripcionDateText = findViewById(R.id.et_mostrar_hora);

        nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    presenter.testName(et.getText().toString());
                }
            }
        });

        priceditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    presenter.testPrice(et.getText().toString());
                }
            }
        });

        tallaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    presenter.testSize(et.getText().toString());
                }
            }
        });

        descripcionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    presenter.testDescription(et.getText().toString());
                }
            }
        });


        descripcionDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    presenter.testDate(et.getText().toString());
                }
            }
        });

    }

    @Override
    public void setNameError(String error) {
        TextInputLayout nameInputLayout = findViewById(R.id.form_name_inputLayout);
        nameInputLayout.setError(error);
    }

    @Override
    public void setPriceError(String error) {
        TextInputLayout priceInputLayout = findViewById(R.id.form_price_inputLayout);
        priceInputLayout.setError(error);
    }

    @Override
    public void setSizeError(String error) {
        TextInputLayout sizeInputLayout = findViewById(R.id.form_size_inputLayout);
        sizeInputLayout.setError(error);
    }

    @Override
    public void setDescriptionError(String error) {
        TextInputLayout descripcionInputLayout = findViewById(R.id.form_description_inputLayout);
        descripcionInputLayout.setError(error);

    }

    @Override
    public void setDateError(String error) {
        TextInputLayout descripcionInputLayout = findViewById(R.id.form_date_inputLayout);
        descripcionInputLayout.setError(error);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
