package com.example.clothes.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clothes.R;

import java.util.List;


public class AddItemDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity caller;
    private TextView add, cancel;
    private EditText option;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> items;

    public AddItemDialog(Activity caller,ArrayAdapter<String> arrayAdapter, List<String> items, Spinner s) {
        super(caller);
        this.caller = caller;
        this.arrayAdapter=arrayAdapter;
        this.items=items;
        this.spinner=s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);          //Cancelamos el título
        setContentView(R.layout.add_item_dialog);               // Añadimos a la vista nuestro add_item_dialog.xml
        option = findViewById(R.id.et_add_state);               //Obtenemos el EditText

        add = findViewById(R.id.btn_add);                       //Obtenemos los botones y añadimos por OnClickListener
        cancel = findViewById(R.id.btn_cancel);                 // esta clase, para ello, implementar la interfaz
        add.setOnClickListener(this);                           // android.view.View.OnClickListener en esta clase
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:                                      //En caso de que el elemento clicado sea añadir
                String option=this.option.getText().toString();     //Obtenemos del EditText el contenido y lo añadimos
                if(option!=null){                                   //al ArrayList.
                    items.add(items.size()-1,option);
                    arrayAdapter.notifyDataSetChanged();            // Notificamos al ArrayAdapter de que su contenido cambio.
                }
                break;
            case R.id.btn_cancel:
                spinner.setSelection(0);
                break;
            default:
                break;
        }
        dismiss();                                                  //Cancelamos el dialogo.
    }

}

