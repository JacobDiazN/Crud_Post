package com.example.webservice2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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

public class Actualizar extends AppCompatActivity {

    //Aquí se están declarando variables de instancia para varios elementos de la interfaz de usuario,
    // como EditText para ingresar datos, Button para realizar acciones y TextView para mostrar información.
    private EditText etIdActualizar, etNuevoTitle, etNuevoBody, etNuevoUserId;
    private Button btnMostrar, btnActualizar;
    private TextView txtMostrarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        //Estas líneas buscan las vistas en el layout por sus IDs,
        // y las asignan a las variables correspondientes, permitiendo el acceso a estos elementos desde el código.
        etIdActualizar = findViewById(R.id.txt_id_actualizar);
        etNuevoTitle = findViewById(R.id.txt_nuevo_title);
        etNuevoBody = findViewById(R.id.txt_nuevo_body);
        etNuevoUserId = findViewById(R.id.txt_nuevo_userId);
        btnMostrar = findViewById(R.id.boton_mostrar_actualizar);
        btnActualizar = findViewById(R.id.boton_actualizar);
        txtMostrarDatos = findViewById(R.id.text_mostrar_datos_actualizar);

        //Estas líneas de código establecen OnClickListener para el boton "Mostrar".
        //Cuando se hace clic en este boton, se llaman al método mostrarDatose.
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatos();
            }
        });

        //Estas líneas de código establecen OnClickListener para el boton ""Actualizar"
        //Cuando se hace clic en este boton, se llaman al método actualizarDatos.
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
            }
        });
    }

    //Con este metedo se muestran los datos del ID ingresado por el usuario
    private void mostrarDatos() {
        String idString = etIdActualizar.getText().toString();

        //Con este if verificamos que campo ID este completado por el usuario,
        //si falta ese dato se enviará un mensaje al usuario diciendo "Por favor, introduce un ID de post válido"
        if (!idString.isEmpty()) {
            int id = Integer.parseInt(idString);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

            Cursor cursor = BaseDeDatos.query("posts", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String body = cursor.getString(cursor.getColumnIndex("body"));
                @SuppressLint("Range") String userId = cursor.getString(cursor.getColumnIndex("userId"));

                String datos = "Title: " + title + "\nBody: " + body + "\nUserId: " + userId;
                txtMostrarDatos.setText(datos);
            } else {
                txtMostrarDatos.setText("No se encontraron datos para el ID especificado");
            }

            cursor.close();
            BaseDeDatos.close();
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, introduce un ID de post válido", Toast.LENGTH_SHORT).show();
        }
    }

    //Con este método se obtienen los valores ingresados por el usuario para el ID, el nuevo title, el nuevo body y el nuevo userId.
    //y dando click al button actualizar id estos datos son modificados en la base de datos.
    private void actualizarDatos() {
        String idString = etIdActualizar.getText().toString();
        String nuevoTitle = etNuevoTitle.getText().toString();
        String nuevoBody = etNuevoBody.getText().toString();
        String nuevoUserId = etNuevoUserId.getText().toString();

        //Con este if verificamos que todos los campos esten completados por el usuario,
        //si falta un dato se enviará un mensaje al usuario diciendo "Por favor, completa todos los campos"
        if (!idString.isEmpty() && !nuevoTitle.isEmpty() && !nuevoBody.isEmpty() && !nuevoUserId.isEmpty()) {
            int id = Integer.parseInt(idString);

            //Acá llamamos a la base de datos llamada administracion en mosdo lectura y escritura
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            // Con este if Verificamos si el ID existe antes de intentar actualizarlo
            if (existeIdEnBaseDeDatos(id, BaseDeDatos)) {
                // Actualizar el registro con el ID especificado
                ContentValues valoresActualizados = new ContentValues();
                valoresActualizados.put("title", nuevoTitle);
                valoresActualizados.put("body", nuevoBody);
                valoresActualizados.put("userId", nuevoUserId);

                BaseDeDatos.update("posts", valoresActualizados, "id=" + id, null);
                Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_LONG).show();

                //Con estas líneas de código nos permitirá limpiar los campos donde el usuario a escrito
                etIdActualizar.setText("");
                etNuevoTitle.setText("");
                etNuevoBody.setText("");
                etNuevoUserId.setText("");
                txtMostrarDatos.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "El ID no existe en la base de datos", Toast.LENGTH_LONG).show();
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_LONG).show();
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

    // Este método nos permite regresar a la actividad principal (MainActivity.class) cuando se presiona el botón regresar.
    public void Regresar_actualizar(View view) {
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}
