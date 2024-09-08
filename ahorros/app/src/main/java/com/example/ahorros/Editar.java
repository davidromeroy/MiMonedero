package com.example.ahorros;

import android.content.Intent;
import android.databinding.tool.util.StringUtils;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ahorros.db.dbAhorros;
import com.example.ahorros.entidades.Gastos;
import com.example.ahorros.entidades.ListaCategorias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Editar  extends AppCompatActivity {
    //Llama a la lista de categorias
    ListaCategorias lista = ListaCategorias.getInstance();
    List<String> listaCategorias = lista.obtenerLista();

    EditText txtItem,txtFecha,txtPrecio,txtDetalle;
    AutoCompleteTextView txtCategoria;
    ArrayAdapter<String> adapterItems;
    Button btnGuardar;
    FloatingActionButton fabEditar,fabDelete;

    boolean correct = false;
    Gastos gasto;
    private String categ="";
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        txtItem = findViewById(R.id.etItem);
        txtFecha = findViewById(R.id.etFecha);
        txtPrecio = findViewById(R.id.etPrecio);
        txtCategoria = findViewById(R.id.etCategoria);
        txtDetalle = findViewById(R.id.etDetalle);
        btnGuardar = findViewById(R.id.btnGuardar);

        fabEditar = findViewById(R.id.fabEditar);
        fabDelete = findViewById(R.id.fabEliminar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabDelete.setVisibility(View.INVISIBLE);

        txtCategoria = findViewById(R.id.etCategoria);


        if(savedInstanceState == null){
            Bundle ex = getIntent().getExtras();
            if (ex == null){
                id = Integer.parseInt(null);
            } else {
                id = ex.getInt("id");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("id");
        }
        final dbAhorros db_gasto = new dbAhorros(Editar.this);
        gasto = db_gasto.verGastos(id);
        if (gasto != null){
            categ = gasto.getCategoria();
            txtItem.setText(gasto.getItem());
            txtFecha.setText(gasto.getFecha());
            txtPrecio.setText(gasto.getPrecio());
            txtCategoria.setText(categ);
            txtDetalle.setText(gasto.getDetalle());

            //Autocomplete, para que salga la lista de categorias
            adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, listaCategorias);
            txtCategoria.setAdapter(adapterItems);
            txtCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    categ = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getApplicationContext(), "Categoria: " + categ, Toast.LENGTH_SHORT).show();
                }
            });

            txtFecha.setInputType(InputType.TYPE_NULL);    //para que la fecha no se pueda modificar
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!txtItem.getText().toString().equals("") && !txtPrecio.getText().toString().equals("")) {
                        String item = StringUtils.capitalize(txtItem.getText().toString());
                        String prec = txtPrecio.getText().toString();
                        String det = StringUtils.capitalize(txtDetalle.getText().toString());
                        correct = db_gasto.editarGasto(id,item,prec,categ,det);    //,categ
                        if (correct){
                            Toast.makeText(Editar.this,"REGISTRO MODIFICADO",Toast.LENGTH_LONG).show();
                            verRegistro();
                        } else {
                            Toast.makeText(Editar.this,"ERROR AL MODIFICAR REGISTRO",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Editar.this,"DEBE LLENAR LOS CAMPOS OBLIGATORIOS",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void verRegistro(){
        Intent i = new Intent(this, MostrarDatos.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}