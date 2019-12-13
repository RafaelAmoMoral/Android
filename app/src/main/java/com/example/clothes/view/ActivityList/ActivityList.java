package com.example.clothes.view.ActivityList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.clothes.R;
import com.example.clothes.interfaces.Ilist;
import com.example.clothes.model.Clothe;
import com.example.clothes.presenter.ListPresenter;
import com.example.clothes.view.AboutActivity;
import com.example.clothes.view.FormActivity;
import com.example.clothes.view.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.LayoutDirection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ActivityList extends AppCompatActivity implements Ilist.View {

    private String TAG = "Clothes/ActivityList";
    private Ilist.Presenter presenter;
    private List<Clothe> clothesList;
    private ClotheAdapter adaptador;

    public ActivityList() {
        presenter = new ListPresenter(this);
        clothesList = presenter.getClothes();
        /*Pedimos a la base de datos una vez, las demás que el usuario refresque*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.listadofb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickAdd();
            }
        });

        setRecyclerView();
    }

    public void setRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.id_recycler_list);
        adaptador = new ClotheAdapter(clothesList);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                Toast.makeText(ActivityList.this, "Posición: " +
                        String.valueOf(position) + " Id: " + clothesList.get(position).getId() +
                        " Nombre: " + clothesList.get(position).getName(), Toast.LENGTH_SHORT)
                        .show();
                presenter.onClotheClicked(clothesList.get(position));
            }
        });
        recyclerView.setAdapter(adaptador);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeController swipeController = new SwipeController(this);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void displayFormClothe(Clothe selectedClothe) {
        FormActivity.setClothe(selectedClothe);
        Intent intent = new Intent(ActivityList.this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_search_main, menu);
        return true;
    }

    @Override
    public void showDeleteClotheDialog(final int clotheSelected) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Está seguro de que desea eliminar?")
                .setMessage("Recuerde que esta decisión será permanente.")
                .setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItemInList(clotheSelected);
                                Toast.makeText(ActivityList.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
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
        dialog.show();
    }

    public Ilist.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showForm() {
        Intent intent = new Intent(ActivityList.this, FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void about(MenuItem aboutItem) {
        Intent intent = new Intent(ActivityList.this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void search(MenuItem searchItem) {
        Intent intent = new Intent(ActivityList.this, SearchActivity.class);
        startActivity(intent);
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

    public void removeItemInList(int index) {
        this.clothesList.remove(index);
        this.adaptador.notifyDataSetChanged();
    }
}


