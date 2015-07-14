package com.example.car.boozechickenhotseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import static android.view.View.INVISIBLE;


public class NeglavnaActivity extends Activity implements View.OnTouchListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neglavna);
        gumb = (Button) findViewById(R.id.drugi);
        gumb.setOnTouchListener(this);
        gumb.setText("stani");
        Intent i=getIntent();
        switch(i.getStringExtra("pobjednik")){
            case ("prvi"):
                gumb.setText("prvi");
                break;
            case ("drugi"):
                gumb.setText("drugi");
                break;
            case ("nitko"):
                gumb.setText("nitko");
                break;
            case ("Grga"):
                gumb.setText("Vrijeme isteklo!");
                break;
        }
    }
    Button gumb;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent intent = new Intent(NeglavnaActivity.this, MainActivity.class);
        intent.putExtra("pozovi","pripremiZaIgru");
        startActivity(intent);
        finish();


        return false;
    }
}
