package com.example.ahorros;

import androidx.appcompat.app.AppCompatActivity;

import android.databinding.tool.util.StringUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.example.ahorros.db.dbAhorros;
import com.example.ahorros.entidades.ListaCategorias;

public class formulario extends AppCompatActivity {
    //Llama a la lista de categorias
    ListaCategorias lista = ListaCategorias.getInstance();
    List<String> listaCategorias = lista.obtenerLista();
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    private EditText etItem, etPrecio, etDetalle;
    private String categ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        etItem = (EditText) findViewById(R.id.et1);
        etPrecio = (EditText) findViewById(R.id.et2);
        etDetalle = (EditText) findViewById(R.id.etCategory);

        //Mandamos la lista de categorias al boton de seleccion
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, (List<String>) listaCategorias);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> {
            categ = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), "Categoria: " + categ, Toast.LENGTH_SHORT).show();
        });
    }

    //Funcion para guardar el objeto Gasto con los datos escritos en la database
    public void guardar(View v) {
        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String item = StringUtils.capitalize(etItem.getText().toString());
        String prec = etPrecio.getText().toString();
        String det = StringUtils.capitalize(etDetalle.getText().toString());

        //Verificamos que no falte nada por escribir o seleccionar
        if ((item.length() != 0) && (prec.length() != 0) && (categ.length() != 0)){
            dbAhorros db_Ahorro = new dbAhorros(this);
            long id = db_Ahorro.insertarGasto(date, item, prec, categ, det);
            if (id > 0) {
                Toast.makeText(this, "REGISTRO GUARDADO", Toast.LENGTH_SHORT
                ).show();
                clean();
            } else {
                Toast.makeText(this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "FALTA ALGUN ELEMENTO", Toast.LENGTH_SHORT).show();
        }
    }

    //Limpiamos los editText una vez guardado el Gasto
    public void clean(){
        etItem.setText("");
        etPrecio.setText("");
        etDetalle.setText("");
        String categ="";
        autoCompleteTxt.setText("");
    }
}