package com.tanger.inventariotanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DWUtilities.CreateDWProfile(this);
        Button btnScan = findViewById(R.id.btnScan);
        btnScan.setOnTouchListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        displayScanResult(intent);
    }

    private void displayScanResult(Intent scanIntent)
    {
        String decodedSource = scanIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = scanIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        Log.d("DWTest", "Intent recebida: " +decodedData);
        String decodedLabelType = scanIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        String scan = decodedData + " [" + decodedLabelType + "]\n\n";
        final TextView output = findViewById(R.id.txtOutput);
        output.setText(scan + output.getText());
    }
    //  EXEMPLO DE COMO CHAMAR EM TELA
    // LEMBRANDO QUE POR PADRÃO NOS MODELOS ZEBRA TC 22 OS BOTÕES DE SCANN VEM JA HABILITADO
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.btnScan)
        {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                //  PRESSIONAR DO BOTÃO INICIA O SCANER
                Intent dwIntent = new Intent();
                dwIntent.setAction("com.symbol.datawedge.api.ACTION");
                dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "START_SCANNING");
                sendBroadcast(dwIntent);
            }
            else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                //  SOLTAR O BOTÃO O SCANNER SE ENCERRA
                Intent dwIntent = new Intent();
                dwIntent.setAction("com.symbol.datawedge.api.ACTION");
                dwIntent.putExtra("com.symbol.datawedge.api.SOFT_SCAN_TRIGGER", "STOP_SCANNING");
                sendBroadcast(dwIntent);
            }
        }
        return true;
    }
}