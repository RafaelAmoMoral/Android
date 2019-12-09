package com.example.clothes.view;
import com.example.clothes.R;
import com.example.clothes.view.ActivityList.ActivityList;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplashScreen extends AppCompatActivity  implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView imageView = findViewById(R.id.id_splash_app_icon);
        rotarImagen(imageView);

        //Método encargado de detener la vista 5 segundos después de los cuáles hace que desaparezca de la pila de vistas.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),
                        ActivityList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }, 5000);
    }

    private void rotarImagen(View view){
        RotateAnimation animation = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animation.setAnimationListener(this);
        animation.setDuration(1500);
        view.startAnimation(animation);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        TextView appName = findViewById(R.id.id_splash_app_name);
        appName.animate().alpha(1.0f).setDuration(1000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
