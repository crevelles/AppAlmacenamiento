package com.example.profesoresi.appalmacenamiento;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AExternoActivity extends AppCompatActivity {

    private EditText etTexto;
    static final String NOM_FICHERO_EXT = "MiFicheroExt.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aexterno);

        etTexto = findViewById(R.id.etTextoExt);
    }

    public void guardarAE(View v) {
        File rutaAE = this.getExternalFilesDir(null);
        System.out.println(rutaAE.getAbsolutePath());
        File f = new File(rutaAE.getAbsolutePath(), NOM_FICHERO_EXT);
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(f,true));
            osw.write(etTexto.getText().toString() + "\n");

            guardarPreferencias();

            setResult(RESULT_OK, getIntent());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "El fichero no ha sido encontrado",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, getIntent());
        } catch (IOException e) {
            Toast.makeText(this, "Error de conexión con el fichero",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, getIntent());
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            finish();
        }
    }

    private void guardarPreferencias() {
        SharedPreferences sp = getSharedPreferences("SPActivity", MODE_PRIVATE);
        String nombreFichExt = sp.getString("FICHERO_EXT", "");

        if (nombreFichExt.equals("")) {
            SharedPreferences.Editor editor = sp.edit();

            editor.putString("FICHERO_EXT", NOM_FICHERO_EXT);
            editor.commit();
        }
    }
}
