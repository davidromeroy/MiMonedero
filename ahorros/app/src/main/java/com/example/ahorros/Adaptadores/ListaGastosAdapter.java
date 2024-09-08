package com.example.ahorros.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahorros.MostrarDatos;
import com.example.ahorros.R;
import com.example.ahorros.entidades.Gastos;
import com.example.ahorros.formulario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaGastosAdapter extends RecyclerView.Adapter<ListaGastosAdapter.GastoViewHolder> {

    ArrayList<Gastos> listaGastos;


    public ListaGastosAdapter(ArrayList<Gastos> listaGastos){
        this.listaGastos = listaGastos;
    }
    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_resultados,null,false);
        return new GastoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        //Aqui manda cada cosa a cada textview de lista_item_resultados
        holder.item.setText(listaGastos.get(position).getItem());
        holder.fecha.setText(listaGastos.get(position).getFecha());
        holder.precio.setText(listaGastos.get(position).getPrecio() + " $");
        holder.detalle.setText(listaGastos.get(position).getDetalle());
    }

    @Override
    public int getItemCount() {
        return listaGastos.size();
    }

    public class GastoViewHolder extends RecyclerView.ViewHolder {
        TextView item, fecha, precio, categoria, detalle;
        public GastoViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.fecha);
            item = itemView.findViewById(R.id.item);
            precio = itemView.findViewById(R.id.precio);
            detalle = itemView.findViewById(R.id.detalle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, MostrarDatos.class);
                    i.putExtra("id",listaGastos.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });

        }
    }
}