package com.example.car.boozechickenhotseat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
        izaberiNovuIgru();
    }
    Button maligumb, drugimaligumb, novaIgra;
    PamtiTimerIStisnut prviStisnut=new PamtiTimerIStisnut() , drugiStisnut=new PamtiTimerIStisnut() ;
    float dX,dY;


    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                stisnutGumb(v);
                tuSeNestoDesi();
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
                break;
            default:
                return false;
        }
        return false;
    }


    boolean akoSuObaStisnuta(){
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
    public void tuSeNestoDesi(){
        if(akoSuObaStisnuta()) {
            long vrijeme = System.currentTimeMillis();
           proglasenjePobjednika();
            //pripremiZaIgru();
        }
    }
    public void proglasenjePobjednika(){
        Intent intent = new Intent(MainActivity.this, NeglavnaActivity.class);
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

    @Override
    protected void onNewIntent(Intent intent) { //ovo ne radi ali možda æe mi trebat nešto slièno pa neka stoji ovdje
        super.onNewIntent(intent);
        if(intent.getStringExtra("pozovi").equals("pripremiZaIgru")) pripremiZaIgru();
    }
}

