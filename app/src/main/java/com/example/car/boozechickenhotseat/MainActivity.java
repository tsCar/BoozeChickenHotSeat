package com.example.car.boozechickenhotseat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.*;
import java.util.Random;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends Opcije implements View.OnTouchListener/*, View.OnLongClickListener*/{

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
        mp = MediaPlayer.create(MainActivity.this,R.raw.rise);
        pripremiZaIgru();
        zvukUpaljen.setIstina(true);

    }
    protected void onStart(){
        super.onStart();
        thread = new HandlerThread("thread");
        thread.start();
        looper = thread.getLooper();
        handler = new Handler(looper);
        runnable = new Runnable() {
            @Override
            public void run() {
                proglasenjePobjednika("Grga");
                timerTece.setIstina(false);
                if(mp.isPlaying())mp.stop();
            }
        };
        timerTece.setIstina(false);
    }
    protected void onStop(){
        super.onStop();
        thread.quit();
    }
    protected void onResume(){
        super.onResume();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.zvuk:
                if(zvukUpaljen.istina()){
                    zvukUpaljen.setIstina(false);
                }
                else {
                    zvukUpaljen.setIstina(true);
                }
                return true;
            /*case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//tu su mi varijable!
    Button maligumb, drugimaligumb, novaIgra;
    PamtiTimerIStisnut prviStisnut=new PamtiTimerIStisnut() , drugiStisnut=new PamtiTimerIStisnut() ; //sad kad ne pamtim vrijeme mogu pobrisat klasu pamtiTimerIStisnut i koristit klasu Istina
    float dX,dY;
    HandlerThread thread;
    Looper looper ;
    Handler handler ;
    Runnable runnable;
    Istina timerTece=new Istina();
    MediaPlayer mp;
    Istina zvukUpaljen=new Istina();
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                stisnutGumb(v);
                if (obaSuStisnuta()) { //poèni timer
                   if(zvukUpaljen.istina())mp.start();
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
                pustenGumb(v);
                if(timerTece.istina()) {
                    handler.removeCallbacksAndMessages(null);
                    timerTece.setIstina(false);
                    if(mp.isPlaying())mp.stop();
                    pocniIgru(v);
                }
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
    public void pocniIgru(View v){
        String pobjednik;
        if(v.getId()==R.id.mali) pobjednik="drugi";
        else pobjednik="prvi";
        proglasenjePobjednika(pobjednik);
    }

    public void proglasenjePobjednika(String s){
        Intent intent = new Intent(MainActivity.this, NeglavnaActivity.class);
        intent.putExtra("pobjednik", s);
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
                break;
            case R.id.drugimali:
                drugiStisnut.setIstina(true);
                break;
            default:break;
        }
    }

    public void pustenGumb(View v) {
        switch(v.getId()){
            case R.id.mali:
                prviStisnut.setIstina(false);
                break;
            case R.id.drugimali:
                drugiStisnut.setIstina(false);
                break;
            default:break;
        }
    }

    public long napraviInterval(){
        Random rnd = new Random();
        return rnd.nextInt(3000) + 3000;
    }
}

