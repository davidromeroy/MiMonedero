package com.example.ahorros.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.example.ahorros.MainActivity;
import com.example.ahorros.entidades.Gastos;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class dbAhorros extends AdminSQLiteOpenHelper{

    Context context;

    public dbAhorros(Context context) {
        super(context);
        this.context = context;
    }

    public long insertarGasto(String fecha, String item, String precio, String categoria,String detalles){
        long id = 0;
        try{

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("fecha", fecha);
            values.put("item", item);
            values.put("precio", precio);
            values.put("categoria", categoria);
            values.put("detalles", detalles);

            id = db.insert(TABLE_AHORROS,null,values);
        } catch (Exception e){
            e.toString();
        }
        return id;
    }

    public boolean editarGasto(int id,String item, String precio, String categoria,String detalles){
        boolean correct = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = admin.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_AHORROS + " SET item = '" + item + "', precio = '" + precio + "', categoria = '" + categoria + "', detalles = '"+ detalles + "' WHERE id='" + id +"' ");
            correct = true;
        } catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }


    public boolean eliminarGasto(int id){
        boolean correct = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = admin.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM " + TABLE_AHORROS +" WHERE id = '" + id + "'" );
            correct = true;
        } catch (Exception e){
            e.toString();
            correct = false;
        } finally {
            db.close();
        }
        return correct;
    }



    public ArrayList<Gastos> mostrarGastos(String categoria){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = admin.getWritableDatabase();
        ArrayList<Gastos> listGastos = new ArrayList<>();
        Gastos gasto = null;
        Cursor cursorGastos = db.rawQuery("SELECT * FROM " + TABLE_AHORROS + " WHERE categoria='" + categoria +"'",null);
        if (cursorGastos.moveToLast()){
            do {
                gasto = new Gastos();
                gasto.setId(cursorGastos.getInt(0));
                gasto.setFecha(cursorGastos.getString(1));
                gasto.setItem(cursorGastos.getString(2));
                gasto.setPrecio(cursorGastos.getString(3));
                gasto.setCategoria(cursorGastos.getString(4));
                gasto.setDetalle(cursorGastos.getString(5));
                listGastos.add(gasto);
            } while (cursorGastos.moveToPrevious());
        }
        cursorGastos.close();
        return listGastos;
    }

    public Gastos verGastos (int id){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = admin.getWritableDatabase();

        Gastos gasto = null;
        Cursor cursorGastos;

        cursorGastos = db.rawQuery("SELECT * FROM " + TABLE_AHORROS + " WHERE id =" + id +" LIMIT 1",null);
        if (cursorGastos.moveToFirst()) {
            gasto = new Gastos();
            gasto.setId(cursorGastos.getInt(0));
            gasto.setFecha(cursorGastos.getString(1));
            gasto.setItem(cursorGastos.getString(2));
            gasto.setPrecio(cursorGastos.getString(3));
            gasto.setCategoria(cursorGastos.getString(4));
            gasto.setDetalle(cursorGastos.getString(5));
        }
        cursorGastos.close();
        return gasto;
    }


    public void exportarCSV() {
        File carpeta = new File(Environment.getExternalStorageDirectory() + "/Database_CSV");
        String archivoAgenda = carpeta.toString() + "/" + "Gastos.csv";
        boolean isCreate = false;
        if(!carpeta.exists()) {
            isCreate = carpeta.mkdirs() ;
        }
        try {
            FileWriter fileWriter = new FileWriter(archivoAgenda);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery("select * from " + TABLE_AHORROS, null);

            if(fila != null && fila.getCount() != 0) {
                fila.moveToFirst();
                do {

                    fileWriter.append(fila.getString(0));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(1));
                    fileWriter.append(",");
                    fileWriter.append(fila.getString(2));
                    fileWriter.append("\n");

                } while(fila.moveToNext());
            } else {
                Toast.makeText(context, "Base de datos vacía", Toast.LENGTH_LONG).show();
            }

            db.close();
            fileWriter.close();
            Toast.makeText(context, "SE CREO EL ARCHIVO CSV EXITOSAMENTE", Toast.LENGTH_LONG).show();

        } catch (Exception e) { }
        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();

    }
}
