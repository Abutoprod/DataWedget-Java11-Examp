package com.tanger.inventariotanger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class DWUtilities {
    private static final String PROFILE_NAME = "DWGettingStartedJava";
    private static final String ACTION_DATAWEDGE = "com.symbol.datawedge.api.ACTION";
    private static final String EXTRA_CREATE_PROFILE = "com.symbol.datawedge.api.CREATE_PROFILE";
    private static final String EXTRA_SET_CONFIG = "com.symbol.datawedge.api.SET_CONFIG";

    public static void CreateDWProfile(Context context)
    {
        sendDataWedgeIntentWithExtra(context, ACTION_DATAWEDGE, EXTRA_CREATE_PROFILE, PROFILE_NAME);

        //  REQUER DataWedge VERSÃO 6.4

        //  CONFIGURANDO O PERFIL CRIADO
        Bundle profileConfig = new Bundle();
        profileConfig.putString("PROFILE_NAME", PROFILE_NAME);
        profileConfig.putString("PROFILE_ENABLED", "true"); //  Seems these are all strings
        profileConfig.putString("CONFIG_MODE", "UPDATE");

        Bundle barcodeConfig = new Bundle();
        barcodeConfig.putString("PLUGIN_NAME", "BARCODE");
        barcodeConfig.putString("RESET_CONFIG", "true");
        Bundle barcodeProps = new Bundle();
        barcodeProps.putString("configure_all_scanners", "true");
        barcodeProps.putString("scanner_input_enabled", "true");
        barcodeConfig.putBundle("PARAM_LIST", barcodeProps);
        profileConfig.putBundle("PLUGIN_CONFIG", barcodeConfig);
        Bundle appConfig = new Bundle();
        appConfig.putString("PACKAGE_NAME", context.getPackageName());      //  ASSOCIAR O PERFIL AO APP
        appConfig.putStringArray("ACTIVITY_LIST", new String[]{"*"});
        profileConfig.putParcelableArray("APP_LIST", new Bundle[]{appConfig});
        sendDataWedgeIntentWithExtra(context, ACTION_DATAWEDGE, EXTRA_SET_CONFIG, profileConfig);

        // CONSEGUE CONFIGURAR ASSIM QUE O CODIGO FOR INSERIDO
        profileConfig.remove("PLUGIN_CONFIG");
        Bundle intentConfig = new Bundle();
        intentConfig.putString("PLUGIN_NAME", "INTENT");
        intentConfig.putString("RESET_CONFIG", "true");
        Bundle intentProps = new Bundle();
        intentProps.putString("intent_output_enabled", "true");
        intentProps.putString("intent_action", context.getResources().getString(R.string.activity_intent_filter_action));
        intentProps.putString("intent_delivery", "0");  //  StartActivity
        intentConfig.putBundle("PARAM_LIST", intentProps);
        profileConfig.putBundle("PLUGIN_CONFIG", intentConfig);
        sendDataWedgeIntentWithExtra(context, ACTION_DATAWEDGE, EXTRA_SET_CONFIG, profileConfig);

        //  DESABILITAR O OUTPUT
        profileConfig.remove("PLUGIN_CONFIG");
        Bundle keystrokeConfig = new Bundle();
        keystrokeConfig.putString("PLUGIN_NAME", "KEYSTROKE");
        keystrokeConfig.putString("RESET_CONFIG", "true");
        Bundle keystrokeProps = new Bundle();
        keystrokeProps.putString("keystroke_output_enabled", "false");
        keystrokeConfig.putBundle("PARAM_LIST", keystrokeProps);
        profileConfig.putBundle("PLUGIN_CONFIG", keystrokeConfig);
        sendDataWedgeIntentWithExtra(context, ACTION_DATAWEDGE, EXTRA_SET_CONFIG, profileConfig);
    }

    private static void sendDataWedgeIntentWithExtra(Context context, String action, String extraKey, String extraValue)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extraValue);
        context.sendBroadcast(dwIntent);
        //Log.d("DWTest", "Entrei aqui ");
    }

    private static void sendDataWedgeIntentWithExtra(Context context, String action, String extraKey, Bundle extras)
    {
        Intent dwIntent = new Intent();
        dwIntent.setAction(action);
        dwIntent.putExtra(extraKey, extras);
        context.sendBroadcast(dwIntent);
        //Log.d("DWTest", "Entrei aqui2 ");
    }
}
