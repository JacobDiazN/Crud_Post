package com.example.webservice2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.webservice2.DataBase.AdminSQLiteOpenHelper;

public class MostarDb extends AppCompatActivity {

    //Aquí se está declarando una variable de instancia para un elemento de la interfaz de usuario,
    //como un TextView para ingresar datos.
    private TextView listarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_db);

        //Estas líneas de codigo buscan las vistas en el layout por sus IDs,
        //y las asignan a las variables correspondientes, permitiendo el acceso a estos elementos desde el código.
        listarText = findViewById(R.id.mostrarText);

        //Cuando se hace clic en este boton Mostrar Post DB, se llaman al método mostrarDatosBaseDeDatos.
        mostrarDatosBaseDeDatos();
    }

    //Con este método mostramos todos los datos almacenados en la base de datos local de SQLite
    private void mostrarDatosBaseDeDatos() {

        //Acá llamamos a la base de datos, solo en modo lectura
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        // Acá Consultamos todos los datos de la tabla 'posts'
        Cursor cursor = BaseDeDatos.rawQuery("select * from posts", null);

        // Acá Con este if consultaremos si existen datos dentro de la tabla
        //Si no existe no enviará un mensaje a traves del TextView diciendo "No hay datos en la base de datos."
        if (cursor.moveToFirst()) {

            //Acá Declaramos una instancia dataBuilder de la clase StringBuilder
            StringBuilder dataBuilder = new StringBuilder();

            // Con este Do While Recorremos el cursor y agregamos los datos al StringBuilder
            do {
                //Acá Obtenemos los datos de cada columna
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String body = cursor.getString(2);
                int userId = cursor.getInt(3);

                listarText.setPadding(60, 250, 60, 0);

                //Acá construimos una cadena con los datos del post
                dataBuilder.append("\n");
                dataBuilder.append("ID: ").append(id).append("\n");
                dataBuilder.append("Title: ").append(title).append("\n");
                dataBuilder.append("Body: ").append(body).append("\n");
                dataBuilder.append("UserID: ").append(userId).append("\n");
                dataBuilder.append("\n--------------------------------------------------------------------------------\n");

            } while (cursor.moveToNext());

            // Acá mostramos los datos obtenidos en el TextView
            listarText.setText(dataBuilder.toString());
        } else {
            listarText.setPadding(60, 350, 60, 0);


            listarText.setText("No hay datos en la base de datos.");
        }

        // Con esta línea de código Cerramos el cursor y la base de datos
        cursor.close();
        BaseDeDatos.close();
    }

    //Este es el Método para el botón regresar que nos lleva a la clase Principal MainActivity.class
    public void Regresar_Mostar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}