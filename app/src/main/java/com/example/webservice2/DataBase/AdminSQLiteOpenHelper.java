package com.example.webservice2.DataBase;

import androidx.annotation.Nullable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Esta clase AdminSQLiteOpenHelper va a tener como objetivo administrar la base de datos
//Esta clase hereda los atributos y métodos de la clase SQLiteOpenHelper.
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    //Este es el constructor de la clase.
    // Recibe parámetros como el contexto de la aplicación, el nombre de la base de datos, una fábrica de cursores, y la versión de la base de datos.
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        //Este método onCreate se llama automáticamente cuando la base de datos se está creando por primera vez.
        // Aquí se ejecuta una sentencia SQL para crear una tabla llamada "posts" con las columnas id (entero y clave primaria), title (texto), body (texto) y userId (entero).
        BaseDeDatos.execSQL("create table posts(id int primary key, title text, body text, userId int)");
    }
    //Este método onUpgrade se llama cuando la versión de la base de datos cambia. Por ahora esta vacía
    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int oldVersion, int newVersion) {

    }
}