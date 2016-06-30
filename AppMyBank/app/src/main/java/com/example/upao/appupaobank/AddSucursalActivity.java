package com.example.upao.appupaobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class AddSucursalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sucursal);

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
        EditText etNombre = (EditText) findViewById(R.id.textNombreSucu);
        EditText etCiudad = (EditText) findViewById(R.id.textCiudad);
        EditText etDireccion = (EditText) findViewById(R.id.textDireccion);

        //TextView tvNombres = (TextView)findViewById(R.id.textNombres);
        TextView tvResultado = (TextView) findViewById(R.id.tvResultado);

        String nombre = etNombre.getText().toString();
        String ciudad= etCiudad.getText().toString();
        String direccion= etDireccion.getText().toString();

        String empleado= Util.codigo_empleado;

        try{
            //Dato

            Log.e("paterno",nombre);
            Log.e("ciudad",ciudad);
            Log.e("direccion",direccion);


            //Lanzar consulta
            //Util.URL_APP
            String url =  "RegistrarSucursal.php?nombre="+nombre
                    +"&ciudad="+ciudad
                    +"&direccion="+direccion;

            Log.e("url",url);

            String jsonResult = Util.execJsonGetRequest(url);
            if(jsonResult.isEmpty())
            {
                throw new Exception("Sucursal no existe.");
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
                etNombre.setText("");
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
