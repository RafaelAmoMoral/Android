package com.example.clothes.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.clothes.R;
import com.example.clothes.interfaces.Ilist;
import com.example.clothes.presenter.ListPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

public class ActivityList extends AppCompatActivity implements Ilist.View {

    String TAG="Clothes/ActivityList";
    private Ilist.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new ListPresenter(this);

        FloatingActionButton fab = findViewById(R.id.listadofb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Pulsando botón flotante...");
                presenter.onClickAdd();
            }
        });
    }

    @Override
    public void showForm() {
        Intent intent=new Intent(ActivityList.this,FormActivity.class);
        startActivity(intent);
    }
}


