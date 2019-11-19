package com.example.clothes.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.clothes.R;

import java.util.List;


public class AddItemDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity caller;
    private Button yes, no;
    private EditText option;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> items;

    public AddItemDialog(Activity a, Spinner s, ArrayAdapter<String> arrayAdapter, List<String> items) {
        super(a);
        this.caller = a;
        this.spinner=s;
        this.arrayAdapter=arrayAdapter;
        this.items=items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_item_dialog);
        option = (EditText) findViewById(R.id.et_add_state);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                String option=this.option.getText().toString();
                if(option!=null){
                    items.add(items.size()-1,option);
                    arrayAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

}

