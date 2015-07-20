package com.example.car.boozechickenhotseat;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import static android.view.View.INVISIBLE;


public class NeglavnaActivity extends KorisnickeOpcije implements View.OnTouchListener {

    @Override
    protected void onPause(){
        super.onPause();
        m.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neglavna);
        gumb = (Button) findViewById(R.id.drugi);
        gumb.setOnTouchListener(this);
        gumb.setText("stani");
        m = MediaPlayer.create(NeglavnaActivity.this, R.raw.gotovo);
        if (zvukUpaljen) m.start();
        Intent i=getIntent();
        switch(i.getStringExtra("pobjednik")){
            case ("prvi"):
                gumb.setText(R.string.pobijedioPlavi);
                break;
            case ("drugi"):
                gumb.setText(R.string.pobijedioCrveni);
                break;
            case ("Grga"):
                gumb.setText(R.string.istekloVrijeme);
                break;
        }
    }
    Button gumb;
    MediaPlayer m;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent intent = new Intent(NeglavnaActivity.this, MainActivity.class);
        intent.putExtra("pozovi","pripremiZaIgru");
        startActivity(intent);
        finish();


        return false;
    }
}
