package com.example.clothes.view.ActivityList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothes.R;
import com.example.clothes.model.Clothe;

import java.util.List;

/**
 * Clase para almacenar el adaptador con los datos
 * de los acontecimientos que va a mostrar
 * el RecyclerView
 * <p>
 * Hay que añadir al proyecto la siguiente
 * dependencia en el archivo /app/build.gradle
 * con la versión que estemos trabajando
 * 'com.android.support:recyclerview-v7:27.1.1'
 */


public class ClotheAdapter
        extends RecyclerView.Adapter<ClotheAdapter.AcontecimientoViewHolder>
        implements View.OnClickListener {

    private List<Clothe> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class AcontecimientoViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView image;
        private Drawable defaultImage;
        private TextView nombre;
        private TextView precio;

        public AcontecimientoViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textView1);
            precio = itemView.findViewById(R.id.textView2);
            image = itemView.findViewById(R.id.card_id);
            defaultImage = itemView.getResources().getDrawable(R.drawable.ic_app);
        }

        public void AcontecimientoBind(Clothe item) {
            if (item != null) {
                nombre.setText(item.getName());
                precio.setText(Integer.toString(item.getPrice().intValue())+" €");
                if (item.getImage() == null) {
                    image.setImageDrawable(defaultImage);
                } else {
                    byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    image.setImageBitmap(decodedByte);
                }
            }
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public ClotheAdapter(@NonNull List<Clothe> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public AcontecimientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        row.setOnClickListener(this);

        AcontecimientoViewHolder avh = new AcontecimientoViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(AcontecimientoViewHolder viewHolder, int position) {
        Clothe item = items.get(position);
        viewHolder.AcontecimientoBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }
}