package com.shido.stickybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void methodOne(View view) {

      Intent i =   registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //passando null e retornando a intent, toda vez precisa clicar nesse botão para saber o status da bateria
        int status = i.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        displayBatterStatus(status);

    }

    public void methodTwo(View view) {
        //Nesse metodo só precisa ser chamado uma vez, já que temos instancia da BatterChangedReceiver
        registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));



    }

    private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           int  status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            displayBatterStatus(status);
        }
    };


    private void displayBatterStatus(int status){
        Log.i("Batter changed: ", String.valueOf(status));

        if(status == BatteryManager.BATTERY_STATUS_CHARGING){
            Toast.makeText(this, "Battery Charging", Toast.LENGTH_SHORT).show();
        }

        if(status == BatteryManager.BATTERY_STATUS_FULL){
            Toast.makeText(this, "Battery Full", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryChangedReceiver);
    }
}
