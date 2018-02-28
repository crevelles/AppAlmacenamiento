package com.example.profesoresi.appalmacenamiento;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsultaAEActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private TextView tvContenido;
    private String nombreFichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ae);

        tvTitulo = findViewById(R.id.tvTituloExt);
        tvContenido = findViewById(R.id.tvContenidoExt);

        recuperarNombreFichero();
        tvTitulo.setText(tvTitulo.getText().toString() + nombreFichero);
        cargarContenido();
    }

    private void cargarContenido() {
        File rutaAE = this.getExternalFilesDir(null);
        File f = new File(rutaAE.getAbsolutePath(), nombreFichero);
        BufferedReader br = null;
        String linea = null;
        String texto = "";

        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f)));

            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }

            tvContenido.setText(texto);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "El fichero no ha sido encontrado",
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error de conexión con el fichero",
                    Toast.LENGTH_LONG).show();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void recuperarNombreFichero() {
        SharedPreferences sp = getSharedPreferences("SPActivity", MODE_PRIVATE);
        // primer parámetro la clave
        // segundo parámetro valor por defecto
        nombreFichero= sp.getString("FICHERO_EXT", "Anónimo");
    }
}
