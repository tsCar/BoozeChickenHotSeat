package com.example.car.boozechickenhotseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends Activity implements View.OnTouchListener/*, View.OnLongClickListener*/{

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        novaIgra=(Button) findViewById(R.id.novaIgra);
        novaIgra.setVisibility(INVISIBLE);
        maligumb = (Button) findViewById(R.id.mali);
        maligumb.setOnTouchListener(this);
        drugimaligumb = (Button) findViewById(R.id.drugimali);
        drugimaligumb.setOnTouchListener(this);
        novaIgra= (Button) findViewById(R.id.novaIgra);
        pripremiZaIgru();

    }
    Istina igraSpremna=new Istina();
    Button maligumb, drugimaligumb, novaIgra;
    PamtiTimerIStisnut prviStisnut=new PamtiTimerIStisnut() , drugiStisnut=new PamtiTimerIStisnut() ;
    float dX,dY;


    public boolean onTouch(View v, MotionEvent event) {
        final Handler handler = new Handler();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                stisnutGumb(v);
                if (obaSuStisnuta()) { //poèni timer
                    igraSpremna.setIstina(true);
                    double vrijeme = System.currentTimeMillis();
                    Random rnd = new Random();
                    final int interval = rnd.nextInt(1000) + 1500;
                    final Runnable handlerTask= new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(this, interval);
                            proglasenjePobjednika("");
                        }

                    };
                    handlerTask.run();
                }

                dX = v.getX() - event.getRawX();
                dY = v.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                v.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                if(igraSpremna.istina()) {

                    handler.removeCallbacksAndMessages(null);
                    pocniIgru();//poništi timer
                }
                igraSpremna.setIstina(false);
                pustenGumb(v);
                break;
            default:
                return false;
        }

        return false;
    }


    boolean obaSuStisnuta(){
         return (prviStisnut.istina()&& drugiStisnut.istina());
    }

    public void izaberiNovuIgru(){
        //ovo mogu kasnije preselit u aktivnost s tutorialom ili nešto, u biti mi ne treba.
        maligumb.setVisibility(INVISIBLE);
        drugimaligumb.setVisibility(INVISIBLE);
        novaIgra.setVisibility(VISIBLE);
        novaIgra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pripremiZaIgru();
            }
        });
    }
    public void pocniIgru(){
        String pobjednik="nitko";

        if((prviStisnut.getVrijeme())>(drugiStisnut.getVrijeme())) pobjednik="prvi";
        else pobjednik="drugi";
        final String p=pobjednik;

    }
    public void proglasenjePobjednika(String s){
        Intent intent = new Intent(MainActivity.this, NeglavnaActivity.class);
        intent.putExtra("pobjednik",s);
        MainActivity.this.startActivity(intent);
    }

    public void pripremiZaIgru(){
        novaIgra.setVisibility(INVISIBLE);
        maligumb.setVisibility(VISIBLE);
        drugimaligumb.setVisibility(VISIBLE);
    }

    public void stisnutGumb(View v) {
        switch(v.getId()){
            case R.id.mali:
                prviStisnut.setIstina(true);
                prviStisnut.setVrijeme(0);
                break;
            case R.id.drugimali:
                drugiStisnut.setIstina(true);
                drugiStisnut.setVrijeme(0);
                break;
            default:break;
        }
    }

    public void pustenGumb(View v) {
        switch(v.getId()){
            case R.id.mali:
                prviStisnut.setIstina(false);
                prviStisnut.setVrijeme(System.currentTimeMillis());
                break;
            case R.id.drugimali:
                drugiStisnut.setIstina(false);
                drugiStisnut.setVrijeme(System.currentTimeMillis());
                break;
            default:break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) { //ovo ne radi ali možda æe mi trebat nešto slièno pa neka stoji ovdje
        super.onNewIntent(intent);
        if(intent.getStringExtra("pozovi").equals("pripremiZaIgru")) pripremiZaIgru();
    }
}

