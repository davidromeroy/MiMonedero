package com.example.ahorros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahorros.Adaptadores.ListaGastosAdapter;
import com.example.ahorros.db.dbAhorros;
import com.example.ahorros.entidades.Gastos;
import com.example.ahorros.entidades.ListaCategorias;

import java.util.ArrayList;
import java.util.List;

public class Datos extends AppCompatActivity {
    //Llama a la lista de categorias
    ListaCategorias lista = ListaCategorias.getInstance();
    List<String> listaCategorias = lista.obtenerLista();

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    TextView textView5;
    TextView totalGastado;
    RecyclerView listaGastos;
    ArrayList<Gastos> listaArrayGastos;
    private String categ="";
    double GastoTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        textView5 = findViewById(R.id.textView5);
        totalGastado = findViewById(R.id.totalGastado);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, (List<String>) listaCategorias);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categ = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Categoría: " + categ, Toast.LENGTH_SHORT).show();
            }
        });
        listaGastos = findViewById(R.id.listaResultados);
    }

    //Funcion para solicitar la lista de todos los gastos de una categoria y mostrarlos
    public void consulta(View v) {
        listaGastos.setLayoutManager(new LinearLayoutManager(this));
        dbAhorros db_ahorros = new dbAhorros(this);
        listaArrayGastos = db_ahorros.mostrarGastos(categ);
        ListaGastosAdapter adapter = new ListaGastosAdapter(listaArrayGastos);
        listaGastos.setAdapter(adapter);
        textView5.setText(categ == "Ingresos" ? "Ganancias:" : "Gastado:");     //Cambia el contenido del textView5 cuando cambia la categoria
        consultarGastos();

    }

    //Funcion para conocer el total de gastos de una categoria
    public void consultarGastos() {
        double gastoActual = 0;
        GastoTotal = 0;
        //Se hace un for each a la listaArrayGastos para obtener cada Gasto de dicha lista
        for (Gastos gasto: listaArrayGastos) {
            String gastoActual_str = gasto.getPrecio();     //se escoge el precio del Gasto con x id, en string
            gastoActual = Double.parseDouble(gastoActual_str);      // se transforma el gastoActual_str en double para poder sumarlo
            GastoTotal += gastoActual;      //Se van sumando todos los precios de cada gasto
        }

        GastoTotal = Math.round(GastoTotal * 100.0) / 100.0;   //Redondeamos a 2 decimales
        totalGastado.setText(String.valueOf(GastoTotal));       //Mandamos al txtView el resultado

    }

}