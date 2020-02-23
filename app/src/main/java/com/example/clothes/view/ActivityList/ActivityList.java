package com.example.clothes.view.ActivityList;

import android.app.Activity;
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
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityList extends AppCompatActivity implements Ilist.View {

    public static final int SEARCH_CLOTHES = 1;
    public static final int ADD_CLOTHE = 2;
    public static final int UPDATE_CLOTHE = 3;

    private Ilist.Presenter presenter;
    private ClotheAdapter adaptador;
    private TextView itemsCount;

    public ActivityList() {
        presenter = new ListPresenter(this);
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

        itemsCount = findViewById(R.id.list_itemsCounter);

        List<Clothe> clothes = presenter.getClothes();
        setRecyclerView(clothes);
        setItemCount(clothes.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_search_main, menu);
        return true;
    }

    /**
     * Este método es usado a la hora de clickar en la opción search de las opciones del menú, ya que
     * usa reflexión, el nombre del método ha de seguir una sintaxis concreta.
     *
     * @param searchItem
     */
    @Override
    public void search(MenuItem searchItem) {
        Intent intent = new Intent(ActivityList.this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_CLOTHES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case SEARCH_CLOTHES: {
                    if (resultCode == Activity.RESULT_OK) {
                        List<Clothe> clothes = data.getParcelableArrayListExtra("clothes");
                        this.adaptador.setClothes(clothes);
                        this.adaptador.notifyDataSetChanged();
                        setItemCount(this.adaptador.getClothes().size());
                    }
                    break;
                }
                case ADD_CLOTHE: {
                    if (resultCode == Activity.RESULT_OK) {
                        Clothe clothe = data.getParcelableExtra("clothe");
                        this.adaptador.getClothes().add(clothe);
                        this.adaptador.notifyDataSetChanged();
                        setItemCount(this.adaptador.getClothes().size());
                    }
                }
                case UPDATE_CLOTHE: {
                    Clothe clothe = data.getParcelableExtra("clothe");
                    if (resultCode == Activity.RESULT_OK) {
                        if (clothe != null) {
                            boolean found = false;
                            for (int i = 0; i < this.adaptador.getClothes().size() && !found; i++) {
                                if (this.adaptador.getClothes().get(i).getId() == clothe.getId()) {
                                    this.adaptador.getClothes().set(i, clothe);
                                    found = true;
                                }
                            }
                            this.adaptador.notifyDataSetChanged();
                            setItemCount(this.adaptador.getClothes().size());
                        }
                    } else {
                        boolean found = false;
                        for (int i = 0; i < this.adaptador.getClothes().size() && !found; i++) {
                            if (this.adaptador.getClothes().get(i).getId() == clothe.getId()) {
                                this.adaptador.getClothes().remove(i);
                                found = true;
                            }
                        }
                        this.adaptador.notifyDataSetChanged();
                        setItemCount(this.adaptador.getClothes().size());
                    }
                }
            }
        }
    }

    /**
     * Este método es usado a la hora de clickar en la opción search de las opciones del menú, ya que
     * usa reflexión, el nombre del método ha de seguir una sintaxis concreta.
     *
     * @param aboutItem
     */
    @Override
    public void about(MenuItem aboutItem) {
        Intent intent = new Intent(ActivityList.this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayFormClothe(Clothe selectedClothe, int formMode) {
        Intent intent = new Intent(ActivityList.this, FormActivity.class);
        intent.putExtra("clothe", selectedClothe);
        startActivityForResult(intent, formMode);
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

    public void setRecyclerView(List<Clothe> clothes) {
        final RecyclerView recyclerView = findViewById(R.id.id_recycler_list);
        adaptador = new ClotheAdapter(clothes);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = recyclerView.getChildAdapterPosition(v);
                presenter.onClotheClicked(presenter.getClothe(adaptador.getClothes().get(position).getId()));
            }
        });
        recyclerView.setAdapter(adaptador);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeController swipeController = new SwipeController(this);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    public void setItemCount(int size) {
        this.itemsCount.setText(Integer.toString(size));
    }

    public Ilist.Presenter getPresenter() {
        return presenter;
    }

    private void removeItemInList(int index) {
        if (this.presenter.removeClothe(this.adaptador.getClothes().get(index).getId())) {
            this.adaptador.getClothes().remove(index);
            this.adaptador.notifyDataSetChanged();
            setItemCount(presenter.getClothes().size());
            Toast.makeText(ActivityList.this, "Elemento eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ActivityList.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
        }
    }

}


