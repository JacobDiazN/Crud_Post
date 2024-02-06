package com.example.webservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //Método Listar_URL para el botón Listar Post URL
    //se crea un objeto llamado read de la clase Intent que nos llevará a el activity Listar.class
    //y creamos el método startActivity
    public void Listar_URL(View view){
        Intent read = new Intent(this, Listar.class);
        startActivity(read);
    }

    //Método Insertar_DB para el botón Insertar Post DB
    //se crea un objeto llamado create de la clase Intent que nos llevará a el activity Insertar.class
    //y creamos el método startActivity
    public void Insertar_DB(View view){
        Intent create = new Intent(this, Insertar.class);
        startActivity(create);
    }

    //Método Mostrar_DB para el botón Mostrar Post DB
    //se crea un objeto llamado read de la clase Intent que nos llevará a el activity MostarDb.class
    //y creamos el método startActivity
    public void Mostrar_DB(View view){
        Intent read = new Intent(this, MostarDb.class);
        startActivity(read);
    }

    //Método Actualizar_DB para el botón Actualizar Post DB
    //se crea un objeto llamado update de la clase Intent que nos llevará a el activity Actualizar.class
    //y creamos el método startActivity
    public void Actualizar_DB(View view){
        Intent update = new Intent(this, Actualizar.class);
        startActivity(update);
    }

    //Método Eliminar_DB para el botón Eliminar Post DB
    //se crea un objeto llamado delete de la clase Intent que nos llevará a el activity Eliminar.class
    //y creamos el método startActivity
    public void Eliminar_DB(View view){
        Intent delete = new Intent(this, Eliminar.class);
        startActivity(delete);
    }
}