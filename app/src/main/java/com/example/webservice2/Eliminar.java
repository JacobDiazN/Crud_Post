package com.example.webservice2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.webservice2.DataBase.AdminSQLiteOpenHelper;

public class Eliminar extends AppCompatActivity {

    //Aquí se están declarando variables de instancia para varios elementos de la interfaz de usuario,
    // como EditText para ingresar datos, Button para realizar acciones y TextView para mostrar información.
    private EditText etIdEliminar;
    private Button btnMostrar, btnBorrar;
    private TextView txtMostrarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);

        //Estas líneas buscan las vistas en el layout por sus IDs,
        //y las asignan a las variables correspondientes, permitiendo el acceso a estos elementos desde el código.
        etIdEliminar = findViewById(R.id.txt_id_eliminar);
        btnMostrar = findViewById(R.id.boton_mostrar);
        btnBorrar = findViewById(R.id.boton_eliminar);
        txtMostrarDatos = findViewById(R.id.text_mostrar_datos);

        //Estas líneas de código establecen OnClickListener para el boton "Mostrar".
        //Cuando se hace clic en este boton, se llaman al método mostrarDatos.
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatos();
            }

        });

        //Estas líneas de código establecen OnClickListener para el boton "Eliminar".
        //Cuando se hace clic en este boton, se llaman al método eliminarDatos.
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDatos();
            }
        });
    }

    //Con este metedo se muestran los datos del ID ingresado por el usuario
    private void mostrarDatos() {
        String idString = etIdEliminar.getText().toString();

        //Con este if verificamos que campo ID este completado por el usuario,
        //si falta ese dato se enviará un mensaje al usuario diciendo "Por favor, introduce un ID de post válido"
        if (!idString.isEmpty()) {
            int id = Integer.parseInt(idString);

            try (AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
                 SQLiteDatabase baseDeDatos = admin.getReadableDatabase()) {

                //Acá Realizamos una consulta a la base de datos para obtener los datos asociados al ID ingresado por el usuario
                Cursor cursor = baseDeDatos.query("posts", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

                //Si se encuentran datos con el ID ingresado por el usuario, se muestra la información en el TextView txtMostrarDatos
                if (cursor.moveToFirst()) {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    String userId = cursor.getString(cursor.getColumnIndex("userId"));

                    String datos = "Title: " + title + "\nBody: " + body + "\nUserId: " + userId;
                    txtMostrarDatos.setText(datos);
                } else {
                    txtMostrarDatos.setText("No se encontraron datos para el ID especificado");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Mensaje de advertencia si el campo ID está vacío
            Toast.makeText(getApplicationContext(), "Por favor, introduce un ID de post válido", Toast.LENGTH_LONG).show();
        }
    }

    //Método para eliminar datos de la base de datos local
    private void eliminarDatos() {
        String idString = etIdEliminar.getText().toString();

        //Con este if verificamos que el campo ID este completado por el usuario,
        //si falta un dato se enviará un mensaje al usuario diciendo "Por favor, introduce un ID de post válido"
        if (!idString.isEmpty()) {
            int id = Integer.parseInt(idString);

            try (AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
                 SQLiteDatabase baseDeDatos = admin.getWritableDatabase()) {

                //Con este if verificamos que el ID existe,
                //si existe, al dar click al button eliminar ID estos datos serán eliminados de la base de datos,
                //si no existe, se enviará un mensaje tost "El ID no existe en la base de datos"
                if (existeIdEnBaseDeDatos(id, baseDeDatos)) {
                    // Utilizar parámetros de selección
                    baseDeDatos.delete("posts", "id=?", new String[]{String.valueOf(id)});
                    Toast.makeText(getApplicationContext(), "Dato eliminado correctamente", Toast.LENGTH_LONG).show();

                    etIdEliminar.setText("");
                    txtMostrarDatos.setText("Datos");
                } else {
                    Toast.makeText(getApplicationContext(), "El ID no existe en la base de datos", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, introduce un ID de post válido", Toast.LENGTH_LONG).show();
        }
    }

    //Con este método realizaremos una consulta y verificaremos si el ID dado por el usuario existe en la base de datos.
    private boolean existeIdEnBaseDeDatos(int id, SQLiteDatabase BaseDeDatos) {
        String[] projection = {"id"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = BaseDeDatos.query("posts", projection, selection, selectionArgs, null, null, null);
        boolean existeId = cursor.moveToFirst();
        cursor.close();
        return existeId;
    }


    // Método para el botón regresar que nos lleva a la clase MainActivity.class
    public void Regresar_eliminar(View view) {
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}