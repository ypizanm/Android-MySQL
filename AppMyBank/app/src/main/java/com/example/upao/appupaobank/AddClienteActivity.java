package com.example.upao.appupaobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class AddClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cliente);

        TextView tvnombreusuario = (TextView) findViewById(R.id.tvNombreUsuario);
        if(Util.nombre_empleado.toString().equalsIgnoreCase("")){

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        tvnombreusuario.setText("Usuario: " + Util.nombre_empleado);
    }

    public void onClickRegistrar(View view) {
        //Controles
        EditText etApellidoPaterno = (EditText) findViewById(R.id.textApellidoPaterno);
        EditText etApellidoMaterno = (EditText) findViewById(R.id.textApellidoMaterno);
        EditText etNombres = (EditText) findViewById(R.id.textNombres);
        EditText etDni = (EditText) findViewById(R.id.textDni);
        EditText etCiudad = (EditText) findViewById(R.id.textCiudad);
        EditText etDireccion = (EditText) findViewById(R.id.textDireccion);

        //TextView tvNombres = (TextView)findViewById(R.id.textNombres);

        TextView tvResultado = (TextView) findViewById(R.id.tvResultado);


        String apPaterno = etApellidoPaterno.getText().toString();
        String apMaterno = etApellidoMaterno.getText().toString();
        String nombres= etNombres.getText().toString();
        String dni= etDni.getText().toString();
        String ciudad= etCiudad.getText().toString();
        String direccion= etDireccion.getText().toString();

        String empleado= Util.codigo_empleado;

        /*
        String apPaterno = "Rosas";
        String apMaterno = "Canelo";
        String nombres= "Andres";
        String dni= "20010000";
        String ciudad= "Piura";
        String direccion= "direccion";
        */

        try{
            //Dato

            Log.e("paterno",apPaterno);
            Log.e("materno",apMaterno);
            Log.e("nombres",nombres);
            Log.e("dni",dni);
            Log.e("ciudad",ciudad);
            Log.e("direccion",direccion);


            //Lanzar consulta
            //Util.URL_APP
            String url =  "RegistrarCliente.php?paterno="+apPaterno
                    +"&materno="+apMaterno
                    +"&nombres="+nombres
                    +"&dni="+dni
                    +"&ciudad="+ciudad
                    +"&direccion="+direccion;

            Log.e("url",url);

            String jsonResult = Util.execJsonGetRequest(url);
            if(jsonResult.isEmpty())
            {
                throw new Exception("Cuenta no existe.");
            }


            //Procesar el resultado
            JSONObject object = new JSONObject(jsonResult);

            String estado = object.getString("state");//("estado");
            String msje = object.getString("message");

            Log.e("estado",estado);
            Log.e("msje",msje);

            if(estado.equals("0"))
            {
                throw new Exception(msje);
            }
            else
            {
                etApellidoPaterno.setText("");
                etApellidoMaterno.setText("");
                etNombres.setText("");
                etDni.setText("");
                etCiudad.setText("");
                etDireccion.setText("");
                //tvNombres.setText("");
                tvResultado.setText(msje);

            }

        } catch (Exception e)
        {
            tvResultado.setText("AQUI EL ERROR " + e.getMessage()+ "-------" + e.getStackTrace());

        }
    }

    public void onClickRetornar(View view) {
        // TODO
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
