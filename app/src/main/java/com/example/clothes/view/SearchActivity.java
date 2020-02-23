package com.example.clothes.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.clothes.interfaces.ISearch;
import com.example.clothes.model.Clothe;
import com.example.clothes.presenter.SearchPresenter;
import com.example.clothes.view.Utils.DatePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.clothes.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ISearch.View {

    private ISearch.Presenter presenter;
    private DatePicker datePicker;
    private TextInputEditText nameEditText;
    private TextInputEditText dateEditText;
    private Spinner state;


    public SearchActivity() {
        this.presenter = new SearchPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        nameEditText = findViewById(R.id.search_name_editText);
        dateEditText = findViewById(R.id.search_fecha_editText);
        state = findViewById(R.id.search_state_spinner);
        Button search = findViewById(R.id.id_search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClothe();
            }
        });
        this.datePicker = new DatePicker((EditText) findViewById(R.id.search_fecha_editText),
                (Button) findViewById(R.id.btn_mostrar_hora_search));

        setFormFieldsListeners();
    }

    private void searchClothe() {
        Clothe c = getClotheFromForm();
        if (c.getPurchaseDate() != null) {
            if (presenter.testDate(c.getPurchaseDate())) {
                presenter.onClickSearchData(c);
            }
        } else {
            presenter.onClickSearchData(c);
        }
    }

    private void setFormFieldsListeners() {
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

    private Clothe getClotheFromForm() {
        Clothe clothe = new Clothe();
        clothe.setName((nameEditText.getText().toString().trim().isEmpty()) ? null : nameEditText.getText().toString().trim());
        clothe.setPurchaseDate((dateEditText.getText().toString().trim().isEmpty()) ? null : dateEditText.getText().toString().trim());
        clothe.setState((state.getSelectedItem().toString().isEmpty() ||
                state.getSelectedItem().toString().equals("Seleccione un elemento...")) ?
                null : state.getSelectedItem().toString().trim());
        return clothe;
    }

    @Override
    public void setDateError(String error) {
        TextInputLayout descripcionInputLayout = findViewById(R.id.search_date_inputLayout);
        descripcionInputLayout.setError(error);
    }


    @Override
    public void showMainList(ArrayList<Clothe> clothes) {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("clothes", clothes);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
