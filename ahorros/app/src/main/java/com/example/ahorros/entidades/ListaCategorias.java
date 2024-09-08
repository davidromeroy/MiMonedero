package com.example.ahorros.entidades;

import java.util.ArrayList;
import java.util.List;

public class ListaCategorias {

    private static ListaCategorias instance;
    private List<String> lista;

    private ListaCategorias() {
        lista = new ArrayList<String>();
        lista.add("Ahorros");
        lista.add("Alimentación");
        lista.add("Caprichos");
        lista.add("Comidas y delivery");
        lista.add("Gastos casa");
        lista.add("Gastos U");
        lista.add("Ingresos");
        lista.add("Transporte");
    }

    public static ListaCategorias getInstance() {
        if (instance == null) {
            instance = new ListaCategorias();
        }
        return instance;
    }

    public void agregarElemento(String elemento) {
        lista.add(elemento);
    }

    public List<String> obtenerLista() {
        return lista;
    }
}

