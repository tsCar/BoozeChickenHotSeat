package com.example.car.boozechickenhotseat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.*;
import java.util.Random;


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
    protected void onStart(){
        super.onStart();
        thread.start();
        looper = thread.getLooper();
        handler = new Handler(looper);
        runnable = new Runnable() {
            @Override
            public void run() {
                proglasenjePobjednika("Grga");
                timerTece.setIstina(false);
            }
        };
        timerTece.setIstina(false);
    }
    protected void onStop(){
        super.onStop();
        thread.quit();
    }
//tu su mi varijable!
    Button maligumb, drugimaligumb, novaIgra;
    PamtiTimerIStisnut prviStisnut=new PamtiTimerIStisnut() , drugiStisnut=new PamtiTimerIStisnut() ;
    float dX,dY;
    HandlerThread thread = new HandlerThread("thread");
    Looper looper ;
    Handler handler ;
    Runnable runnable;
    Istina timerTece=new Istina();


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                stisnutGumb(v);
                if (obaSuStisnuta()) { //po�ni timer
                   double vrijeme = System.currentTimeMillis();
                   handler.postDelayed(runnable, napraviInterval());
                   timerTece.setIstina(true);
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
                if(timerTece.istina()) {
                    handler.removeCallbacksAndMessages(null);
                    timerTece.setIstina(false);
                    pocniIgru();
                }
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
        //ovo mogu kasnije preselit u aktivnost s tutorialom ili ne�to, u biti mi ne treba.
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
        String pobjednik;
        if((prviStisnut.getVrijeme())>(drugiStisnut.getVrijeme())) pobjednik="prvi";
        else pobjednik="drugi";
        if((prviStisnut.getVrijeme())==(drugiStisnut.getVrijeme()))pobjednik="nitko";
            proglasenjePobjednika(pobjednik);

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
                if(obaSuStisnuta())prviStisnut.setVrijeme(System.currentTimeMillis());
                break;
            case R.id.drugimali:
                drugiStisnut.setIstina(false);
                if(obaSuStisnuta())drugiStisnut.setVrijeme(System.currentTimeMillis());
                break;
            default:break;
        }
    }

    public long napraviInterval(){
        Random rnd = new Random();
        return rnd.nextInt(1000) + 1500;
    }

    @Override
    protected void onNewIntent(Intent intent) { //ovo ne radi ali mo�da �e mi trebat ne�to sli�no pa neka stoji ovdje
        super.onNewIntent(intent);
        if(intent.getStringExtra("pozovi").equals("pripremiZaIgru")) pripremiZaIgru();
    }
}

