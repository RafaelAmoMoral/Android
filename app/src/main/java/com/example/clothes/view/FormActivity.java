package com.example.clothes.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.clothes.PermissionsManager;
import com.example.clothes.R;
import com.example.clothes.interfaces.IForm;
import com.example.clothes.model.Clothe;
import com.example.clothes.model.ClotheDAO;
import com.example.clothes.presenter.FormPresenter;
import com.example.clothes.view.Utils.DatePicker;
import com.example.clothes.view.Utils.Images;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
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
    private Clothe clothe;

    private ImageView image;
    private TextInputEditText nameEditText;
    private TextInputEditText sizeEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText dateEditText;
    private DatePicker datePicker;
    private Switch favourite;
    private Spinner state;

    private Button saveButton;


    public FormActivity() {
        presenter = new FormPresenter(this);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        clothe = getIntent().getExtras().getParcelable("clothe");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        nameEditText = findViewById(R.id.form_name_editText);
        sizeEditText = findViewById(R.id.form_size_ediText);
        descriptionEditText = findViewById(R.id.form_description_editText);
        priceEditText = findViewById(R.id.form_price_editText);
        dateEditText = findViewById(R.id.et_mostrar_hora);
        favourite = findViewById(R.id.form_switch_state);
        state = findViewById(R.id.form_spinner_states);
        image = findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickImage(view.getContext(), FormActivity.this);
            }
        });
        datePicker = new DatePicker((EditText) findViewById(R.id.et_mostrar_hora),
                (Button) findViewById(R.id.btn_mostrar_hora));
        saveButton = findViewById(R.id.id_search_button);

        setSpinner();
        setFormFieldsValues();
        setActivityMode();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    /**
     * A partir de Android 3.0, no se puede llamar implicitamente al método onPrepareOptionsMenu, para
     * poder llamarlo se ha de hacer explicitamente a través de este método.
     */
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    /**
     * Método usado una única vez al iniciar la actividad. Si el objeto clothe de esta clase es igual
     * a null (lo que significaría que se esta añadiendo), la opción eliminar que se encuentra en el
     * índice 1 del menú se ocultaría, de lo contrario se mostraría.
     *
     * @param menu Menu actual de la actividad.
     * @return Debe devolver verdadero para mostrar el menú.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(1).setVisible(this.clothe != null);
        return true;
    }

    /**
     * Método usado cada vez que se clica en el apartado del menú la opción de borrar. Este método usa
     * reflexión, es decir el nombre del mismo es usado para que la JVM lo encuentre.
     * Si queremos ver su uso click derecho en el nombre, find usages.
     *
     * @param aboutItem item clicado del menú.
     */
    public void delete(MenuItem aboutItem) {
        createSimpleDialog().show();
    }

    /**
     * Método usado para insertar los valores predefinidos del spinner, además llama al método
     * appendDatabaseStates para añadir a los estados por defecto los que el usuario añadio en
     * anteriores prendas.
     */
    private void setSpinner() {
        final List<String> states = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.states)));
        final ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, appendDatabaseStates(states));

        state.setAdapter(spinnerAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Añadir...")) {
                    AddItemDialog dialog = new AddItemDialog(FormActivity.this, spinnerAdapter, states, state);
                    dialog.show();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private List<String> appendDatabaseStates(List<String> states) {
        List<String> nonDefaultStates = ClotheDAO.getStates();
        List<String> finalStates = states;
        states.remove(states.size() - 1);

        boolean repeated = false;
        for (int i = 0; i < nonDefaultStates.size(); i++) {
            repeated = false;
            for (int z = 0; z < states.size() && !repeated; z++) {
                if (states.get(z).equals(nonDefaultStates.get(i))) {
                    repeated = true;
                }
            }
            if (repeated == false) {
                finalStates.add(nonDefaultStates.get(i));
            }
        }

        states.add("Añadir...");

        return finalStates;
    }

    private void setActivityMode() {
        if (clothe == null) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveClothe();
                }
            });
        } else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateClothe();
                }
            });
        }
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
                                presenter.onClickRemoveData(clothe, Activity.RESULT_CANCELED);
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        return dialog;
    }

    @Override
    public void displayMainActivity(Clothe c, Integer code) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("clothe", c);
        setResult(code, resultIntent);
        finish();
    }

    /**
     * Método usado para agregar los valores de la prenda al formulario, además si no tiene imagen,
     * le añade una por defecto.
     */
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
            nameEditText.setText(clothe.getName());
            priceEditText.setText(Integer.toString(clothe.getPrice().intValue()));
            sizeEditText.setText(clothe.getSize());
            descriptionEditText.setText(clothe.getDescription());
            dateEditText.setText(clothe.getPurchaseDate());
            favourite.setChecked(clothe.isFavorite());
            Adapter adapter = state.getAdapter();
            int n = adapter.getCount();
            boolean find = false;
            for (int i = 0; i < n && !find; i++) {
                String c = (String) adapter.getItem(i);
                if (c.equals(clothe.getState())) {
                    state.setSelection(i);
                    find = true;
                }
            }
        }
    }


    /**
     * Método encargado de obtener todos los datos del formulario.
     */
    public boolean getFormValues() {
        List<Boolean> validFields = new ArrayList<>();

        boolean isNameWellFormed = clothe.setName(nameEditText.getText().toString().trim());
        setNameError(isNameWellFormed ? "" : "El campo es obligatorio");
        validFields.add(isNameWellFormed);

        try {
            boolean isPriceWellFormed = clothe.setPrice(Integer.parseInt(priceEditText.getText().toString().trim()));
            setPriceError(isPriceWellFormed ?  "":"Formato de numero inválido");
            validFields.add(isPriceWellFormed);
        } catch (NumberFormatException nfe) {
            clothe.setPrice(-1);
            setPriceError("Formato de numero inválido");
            validFields.add(false);
        }

        boolean isSizeWellFormed = clothe.setSize(sizeEditText.getText().toString().trim());
        setSizeError(isSizeWellFormed ? "" : "Tallas disponibles: 'S', 'XS', 'M', 'XM', 'XL'");
        validFields.add(isSizeWellFormed);

        boolean isDescriptionWellFormed = clothe.setDescription(descriptionEditText.getText().toString().trim());
        setDescriptionError(isDescriptionWellFormed ? "" : "El campo es obligatorio");
        validFields.add(isSizeWellFormed);

        boolean isDateWellFormed = clothe.setPurchaseDate(dateEditText.getText().toString().trim());
        setDateError(isSizeWellFormed ? "" : "Formato de fecha permitido: dd/mm/yyyy");
        validFields.add(isDateWellFormed);

        clothe.setFavorite(favourite.isChecked());
        clothe.setState(state.getSelectedItem().toString());
        clothe.setImage(Images.fromImageViewToBase64(image));

        boolean valid = true;
        for (int i = 0; i < validFields.size() && valid; i++) {
            if (validFields.get(i) == false) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Método usado al clicar en el boton de añadir del formulario. Instancio un nuevo objeto ya que
     * aunque tenga valores debe de ser sobreescrito.
     */
    public void saveClothe() {
        clothe = new Clothe();
        if(getFormValues()){
            presenter.onClickSaveData(clothe);
        }
    }

    public void updateClothe() {
        if(getFormValues()){
            presenter.onClickUpdateClothe(clothe, Activity.RESULT_OK);
        }
    }

    //Mensajes de error del formulario

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

    //A partir de aqui manejo el acceso a la galeria

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (PermissionsManager.REQUEST_SELECT_IMAGE):
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
                        Images.resizeImage(bmp);
                        image.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionsManager.CODE_READ_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    //Mensaje con que debe de aceptar para tener las funcionalidades
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(
                R.string.form_image_chooser_title)), PermissionsManager.REQUEST_SELECT_IMAGE);
    }

}
