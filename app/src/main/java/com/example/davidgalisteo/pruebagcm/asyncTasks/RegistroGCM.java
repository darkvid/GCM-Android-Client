package com.example.davidgalisteo.pruebagcm.asyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.davidgalisteo.pruebagcm.MainActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.net.URL;

/**
 * Created by davidgalisteo on 10/12/14.
 */
public class RegistroGCM extends AsyncTask<Void,Integer,String> {

    GoogleCloudMessaging gcm;
    Context contexto;
    String regid;
    String SENDER_ID;
    MainActivity padre;

    public RegistroGCM(GoogleCloudMessaging gc, Context cont, String regI, String senderI, MainActivity father){
        gcm = gc;
        contexto = cont;
        regid = regI;
        SENDER_ID = senderI;
        padre = father;
    }

    @Override
    protected void onPostExecute(String msg) {
        //alert exito
        //subir al server
        // You should send the registration ID to your server over HTTP,
        // so it can use GCM/HTTP or CCS to send messages to your app.
        // The request to your server should be authenticated if your app
        // is using accounts.
        padre.sendRegistrationIdtoBackend(regid);
    }

    @Override
    protected String doInBackground(Void... params) {
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(contexto);
            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;

            // For this demo: we don't need to send it because the device
            // will send upstream messages to a server that echo back the
            // message using the 'from' address in the message.

            // Persist the regID - no need to register again.
            padre.storeRegistrationId(contexto, regid);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
            // If there is an error, don't just keep trying to register.
            // Require the user to click a button again, or perform
            // exponential back-off.
        }
        return msg;
    }

}
