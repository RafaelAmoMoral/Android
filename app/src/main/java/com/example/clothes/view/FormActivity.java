package com.example.clothes.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormActivity extends AppCompatActivity implements IForm.View {

    public static final String TAG = "Clothes/FormActivity";

    private IForm.Presenter presenter;
    private static Clothe clothe;

    private static final int CODE_READ_EXTERNAL_STORAGE_PERMISSION = 123;
    private static final int REQUEST_SELECT_IMAGE = 456;

    private ImageView image;
    private TextInputEditText idEditText;
    private TextInputEditText nameEditText;
    private TextInputEditText sizeEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText dateEditText;
    private DatePicker datePicker;
    private Switch favourite;
    private Spinner state;


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
                saveFormDataIntoClothe();
                if (presenter.isFormValid(clothe)) ;
                presenter.onClickSaveData(clothe);
            }
        });

        this.datePicker = new DatePicker((EditText) findViewById(R.id.et_mostrar_hora),
                (Button) findViewById(R.id.btn_mostrar_hora));

        image = findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickImage(view.getContext());
            }
        });

        idEditText = findViewById(R.id.form_id_editText);
        nameEditText = findViewById(R.id.form_name_editText);
        sizeEditText = findViewById(R.id.form_size_ediText);
        descriptionEditText = findViewById(R.id.form_description_editText);
        priceEditText = findViewById(R.id.form_price_editText);
        dateEditText = findViewById(R.id.et_mostrar_hora);
        favourite = findViewById(R.id.form_switch_state);
        state = findViewById(R.id.form_spinner_states);

        setFormFieldsErrors();
        setFormFieldsValues();
        setSpinner();
    }

    @Override
    public void requestPermmission() {
        ActivityCompat.requestPermissions(FormActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_READ_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_READ_EXTERNAL_STORAGE_PERMISSION:
                presenter.resultPermissions(grantResults);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void openGallery() {
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.form_image_chooser_title)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                        resizeImage(bmp);
                        image.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    private void resizeImage(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int newWidth = 100;
        int newHeight = 100;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        bmp = Bitmap.createBitmap(bmp, 0, 0,
                width, height, matrix, true);
    }

    public static void setClothe(Clothe clothe) {
        FormActivity.clothe = clothe;
    }

    private void setFormFieldsValues() {
        if (clothe != null) {
            if (clothe.getImage() != null) {
                byte[] decodedString = Base64.decode(clothe.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image.setImageBitmap(decodedByte);
            } else {
                Drawable d = getResources().getDrawable(R.drawable.ic_app);
                image.setImageDrawable(d);
            }
            idEditText.setText(Integer.toString(clothe.getId()));
            nameEditText.setText(clothe.getName());
            sizeEditText.setText(clothe.getSize());
            dateEditText.setText(clothe.getDescription());
            priceEditText.setText(Integer.toString(clothe.getPrice().intValue()));
            dateEditText.setText(clothe.getPurchaseDateFormated());
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
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
    public void showMainList() {
        finish();
    }

    private void setFormFieldsErrors() {

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    et.setText(et.getText().toString().trim());
                    presenter.testName(et.getText().toString());
                }
            }
        });

        priceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    et.setText(et.getText().toString().trim());
                    presenter.testPrice(et.getText().toString());
                }
            }
        });

        sizeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    et.setText(et.getText().toString().trim());
                    presenter.testSize(et.getText().toString());
                }
            }
        });

        descriptionEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    et.setText(et.getText().toString().trim());
                    presenter.testDescription(et.getText().toString());
                }
            }
        });


        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextInputEditText et = (TextInputEditText) v;
                    et.setText(et.getText().toString().trim());
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

    public void saveFormDataIntoClothe() {
        clothe.setName(nameEditText.getText().toString().trim());
        clothe.setPrice(Integer.parseInt(priceEditText.getText().toString().trim()));
        clothe.setSize(sizeEditText.getText().toString().trim());
        clothe.setDescription(descriptionEditText.getText().toString().trim());
        clothe.setPurchaseDate(dateEditText.getText().toString().trim());
        clothe.setFavorite(favourite.isChecked());
        clothe.setState(state.getSelectedItem().toString());
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
