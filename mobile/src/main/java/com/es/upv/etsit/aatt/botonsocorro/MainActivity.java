package com.es.upv.etsit.aatt.botonsocorro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.app.Activity;
import android.widget.TextView;
import android.net.Uri;
import android.util.Log;
import android.content.pm.PackageManager;


// nuevas

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends AppCompatActivity {
    String nombre;
    String telefono;
    String email;
    boolean preferenciasGuardadas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// FUNCION ESPERA =========================================================================================================================================================
        //aqui irá el hilo en segundo plano que tendra que estar esperando la posible pulsación del reloj

/*private void mandarMensaje(final String path, final String texto){
    new Thread(new Runnable() {
        @Override
        public void run() {
            NodeApi.GetConnectedNodesResult nodos =
                    Wearable.NodeApi.getConnectedNodes(apiClient).await()
                    for (Node nodo : nodos.getNodes()) {
                Wearable.MessageApi.sendMessage(apiClient, nodo.getNodo(),path, texto.getBytes())
                        .setResultCallback(
                                new ResultCallback<MessageApi.SendMessageResult>() {
                                    @Override
                                    public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) resultado {
                                        if (!resultado.getStatus().isSuccess()) {
                                            Log.e("sincronización","Error al mandar mensaje. Godigo:"+resultado.getStatus().getStatusCode();)
                                        }
                                    }
                                }
                        );
                    }
        }
    })
*/

// CONFIGURACIÓN DE LOS BOTONES ===========================================================================================================================================
// CONFIGURAMOS EL BOTON DE CONFIGURACIÓN
        Button btn = (Button) findViewById(R.id.button_conf);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), menu_config.class);
                startActivityForResult(intent, 0);
            }
        });
        // CONFIGURAMOS EL BOTON DE SOS
        Button btn4 = (Button) findViewById(R.id.button_sos);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sos();
            }
        });
    }


    // ===========================================================================================================================================================================
    //FUNCIONES AUXILIARES
    //==============================================================================================================================================================================


    //guardar configuración aplicación Android usando SharedPreferences
    //Esta no se usa en el MainActivity. solo en el menu_config
    public void guardarPreferencias() {
        SharedPreferences prefs = getSharedPreferences("MisDatosApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("preferenciasGuardadas", true);
        editor.putString("nombre", nombre);
        editor.putString("telefono", telefono);
        editor.putString("email", email);
        editor.commit();
        Toast.makeText(this, "guardando preferencias", Toast.LENGTH_SHORT).show();
    }

    //cargar configuración aplicación Android usando SharedPreferences
    //Esta la usamos también en menu_config
    public void cargarPreferencias() {
        SharedPreferences prefs = getSharedPreferences("MisDatosApp", Context.MODE_PRIVATE);
        this.nombre = prefs.getString("nombre", " ");
        this.telefono = prefs.getString("telefono", " ");
        this.email = prefs.getString("email", " ");
        preferenciasGuardadas = prefs.getBoolean("preferenciasGuardadas", false);

    }

    // FUNCION SOS
    public void sos() {

        final String latitud = "nose";
        String longitud = "nose";

        //Paso 1.      CARGAMOS LOS DATOS
        SharedPreferences prefs = getSharedPreferences("MisDatosApp", Context.MODE_PRIVATE);
        this.nombre = prefs.getString("nombre", " ");
        this.telefono = prefs.getString("telefono", " ");
        this.email = prefs.getString("email", " ");
        preferenciasGuardadas = prefs.getBoolean("preferenciasGuardadas", false);
        nombre = this.nombre.toString();
        email = this.email.toString();
        telefono = this.telefono.toString();


        //Paso 2 hacemos la alerta   ========================================================================================================================

        //INTENCION LLAMAR. Posible función que ponga una notificación para llamar rapidamente al contacto de emergencia. Si da tiempo la hacemos
        //Intent intencionLlamar = new Intent(Intent.ACTION_DIAL, Uri.parse(telefono));


        // ENVIAMOS SMS ===============================================================================================================================================================
 /*       if(ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(
                MainActivity.this,Manifest
                        .permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]
                    { Manifest.permission.SEND_SMS,},1000);
        }else{
            enviarMensaje(telefono,nombre+ " ha pulsado SOS"); // Hay una función auxiliar que manda el sms.
        }
*/



        // ENVIAMOS EMAIL DE SOS
        String[] TO = {email}; //aquí pon tu correo
        Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto", email, null));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, nombre + " ha pulsado el boton SOS!");


        // obtener posicion=========================================







/*
        // Acquire a reference to the system Location Manager
        LocationManager loc = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

*/



            //SIGUE EMAIL ========================================

            emailIntent.putExtra(Intent.EXTRA_TEXT, "Atención! " + nombre + " ha pulsado el botón en: " + latitud + " y " + longitud + ".");

            try {
                startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
            }

        }

    private void makeUseOfNewLocation(Location location) {

       // Toast.makeText(MainActivity.this,
     //           "LOCALIZADO", Toast.LENGTH_LONG).show();

    }


    // FUNCION AUXILIAR QUE ENVIA EL SMS
    private void enviarMensaje (String numero, String mensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}

