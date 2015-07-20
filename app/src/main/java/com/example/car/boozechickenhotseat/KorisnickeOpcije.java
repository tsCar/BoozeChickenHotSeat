package com.example.car.boozechickenhotseat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class KorisnickeOpcije extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        zvukUpaljen=true;
        super.onCreate(savedInstanceState);
        Context context=getApplicationContext();
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.OnSharedPreferenceChangeListener listener;
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                zvukUpaljen=settings.getBoolean("meniZvuk", true);
            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);
    }

    protected void onResume(){
        super.onResume();
    }

    boolean zvukUpaljen;
    SharedPreferences  settings;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getFragmentManager().beginTransaction().replace(android.R.id.content, new KorisnickeOpcijeFragment()).addToBackStack("").commit();
       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
*/

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
           // case R.id.action_settings:
                //getFragmentManager().beginTransaction().replace(android.R.id.content, new KorisnickeOpcijeFragment()).commit();
             //   break;
            default:
                return super.onOptionsItemSelected(item);
        }*/
        return true;
    }
}
