package com.example.clothes.view;

import android.os.Bundle;

import com.example.clothes.interfaces.ISearch;
import com.example.clothes.model.Clothe;
import com.example.clothes.presenter.SearchPresenter;
import com.example.clothes.view.Utils.DatePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.clothes.R;

public class SearchActivity extends AppCompatActivity implements ISearch.View{

    private static final String TAG="SearchActivity";
    private ISearch.Presenter presenter;
    private DatePicker datePicker;


    public SearchActivity() {
        this.presenter=new SearchPresenter(this);
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

        Button search = findViewById(R.id.id_search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSearchData(new Clothe());
            }
        });

        this.datePicker = new DatePicker((EditText) findViewById(R.id.et_mostrar_hora_search),
                (Button) findViewById(R.id.btn_mostrar_hora_search));
    }

    @Override
    public void showMainList() {
        finish();
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

}
