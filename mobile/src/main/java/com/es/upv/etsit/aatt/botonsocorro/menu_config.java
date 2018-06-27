package com.es.upv.etsit.aatt.botonsocorro;

import android.support.v7.app.AppCompatActivity;
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




public class menu_config extends AppCompatActivity {
    String nombre;
    String telefono;
    String email;
    boolean preferenciasGuardadas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_config);
        cargarPreferencias();

        //AQUI PREPARAMOS EL BOTON DE IR ATRAS
        Button btn2 = (Button) findViewById(R.id.botonatras);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent2, 0);
            }
        });

        //AQUI PREPARAMOS EL BOTON DE ACTUALIZAR
        Button btn3 = (Button) findViewById(R.id.boton_actualiza);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPreferencias();
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        guardarPreferencias();
    }
//AQUI COMPROBAMOS SI HAY PREFERENCIAS GUARDADAS ANTERIORMENTE
        @Override
        protected void onStart() {
            super.onStart();
            cargarPreferencias();

            }

//guardar configuración aplicación Android usando SharedPreferences
        public void guardarPreferencias(){
            EditText etnombre = (EditText)findViewById(R.id.editText3);
            EditText ettelefono = (EditText)findViewById(R.id.editText);
            EditText etemail = (EditText)findViewById(R.id.editText2);

            nombre = etnombre.getText().toString();
            telefono = ettelefono.getText().toString();
            email = etemail.getText().toString();
            SharedPreferences prefs = getSharedPreferences("MisDatosApp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("preferenciasGuardadas", true);
            editor.putString("nombre", nombre);
            editor.putString("telefono",telefono);
            editor.putString("email", email);
            editor.commit();
            Toast.makeText(this, "Preferencias guardadas!", Toast.LENGTH_SHORT).show();
        }

//cargar configuración aplicación Android usando SharedPreferences
        public void cargarPreferencias(){
            EditText etnombre = (EditText)findViewById(R.id.editText3);
            EditText ettelefono = (EditText)findViewById(R.id.editText);
            EditText etemail = (EditText)findViewById(R.id.editText2);
            SharedPreferences prefs = getSharedPreferences("MisDatosApp", Context.MODE_PRIVATE);
            this.nombre= prefs.getString("nombre", " ");
            this.telefono = prefs.getString("telefono", " ");
            this.email = prefs.getString("email", " ");
            preferenciasGuardadas = prefs.getBoolean("preferenciasGuardadas", false);
            // para ver el funcionamiento, imprimimos preferencias si existen
            String mensaje = "";
            if (this.preferenciasGuardadas) {
                mensaje = "Las preferencias fueron guardadas ya";
            } else {
                mensaje = "Las preferencias todavia no se guardaron";
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

            etnombre.setText(this.nombre.toString());
            etemail.setText(this.email.toString());
            ettelefono.setText(this.telefono.toString());



        }


}

