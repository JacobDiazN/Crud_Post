package com.example.webservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webservice2.DataBase.AdminSQLiteOpenHelper;
import com.example.webservice2.Interface.ServicioWebPosts;
import com.example.webservice2.Modelo.Posts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Insertar extends AppCompatActivity {

    //Aquí se están declarando variables de instancia para varios elementos de la interfaz de usuario,
    //como EditText para ingresar datos, y TextView para mostrar información.
    private EditText et_id_create;
    private TextView T_View_Title, T_View_Body, T_View_UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        //Estas líneas buscan las vistas en el layout por sus IDs,
        //y las asignan a las variables correspondientes, permitiendo el acceso a estos elementos desde el código.
        et_id_create = findViewById(R.id.txt_id_create);
        T_View_Title = findViewById(R.id.TextViewTitle);
        T_View_Body = findViewById(R.id.TextViewBody);
        T_View_UserId = findViewById(R.id.TextViewUserId);

        //Estas líneas de código establecen OnClickListener para el boton "Registrar".
        //Cuando se hace clic en este boton, se llaman al método registrar.
        Button btnRegistrar = findViewById(R.id.boton_registrar_create);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }

    //Este método es el que nos guardará un dato de un post alojado en la API Jason
    //Lo que logramos con este método es solicitarle al usuario que digite un ID,
    //el cual se va a concatenar con la URL del API y nos retornará los datos de ese ID.
    //Con ello podremos tomar esos datos y registrarlos en nuestra base de datos.
    private void registrar() {
        String postIdString = et_id_create.getText().toString();

        //Con este if verificamos que campo ID este completado por el usuario,
        //si falta ese dato se enviará un mensaje al usuario diciendo "Por favor, introduce un ID de post de 1 a 100"
        if (!postIdString.isEmpty()) {
            int postId = Integer.parseInt(postIdString);

            //Configuración de Retrofit para realizar la solicitudes HTTP
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Creación de la interfaz del servicio web utilizando Retrofit
            ServicioWebPosts servicioWebPosts = retrofit.create(ServicioWebPosts.class);

            //Creación de la solicitud para obtener un solo post utilizando el ID
            Call<Posts> call = servicioWebPosts.getSinglePost(String.valueOf(postId));

            //Realización de la solicitud asíncrona utilizando enqueue
            call.enqueue(new Callback<Posts>() {

                //Se implementan el método onResponse para manejar la respuesta exitosa de la solicitud.
                @Override
                public void onResponse(Call<Posts> call, Response<Posts> response) {
                    if (response.isSuccessful()) {
                        Posts post = response.body();
                        mostrarDatos(post);
                        guardarEnBaseDeDatos(post);
                        Toast.makeText(getApplicationContext(), "Datos guardados en la base de datos", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Código " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                //Se implementan el método onFailure para manejar la respuesta fallida de la solicitud.
                @Override
                public void onFailure(Call<Posts> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, introduce un ID de post de 1 a 100", Toast.LENGTH_LONG).show();
        }
    }

    //Método para mostrar datos de un objeto Posts en la interfaz de usuario
    private void mostrarDatos(Posts post) {
        //Esta lína de codigo establece el texto en un TextView para mostrar el title del post
        T_View_Title.setText("Title: " + post.getTitle());
        //Esta lína de codigo establece el texto en un TextView para mostrar el body del post
        T_View_Body.setText("Body: " + post.getBody());
        //Esta lína de codigo establece el texto en un TextView para mostrar el userId del post
        T_View_UserId.setText("UserId: " + post.getUserId());
    }


    // Método para almacenar los datos en la base de datos local
    private void guardarEnBaseDeDatos(Posts post) {
        // Crear o abrir la base de datos en modo lectura y escritura
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        // Crear un objeto ContentValues para almacenar los datos
        ContentValues registro = new ContentValues();
        registro.put("id", post.getId());
        registro.put("title", post.getTitle());
        registro.put("body", post.getBody());
        registro.put("userid", post.getUserId());

        // Insertar los datos en la tabla 'posts'
        BaseDeDatos.insert("posts", null, registro);

        // Cerrar la base de datos
        BaseDeDatos.close();
    }

    //Método para el botón limpiar el menu insertar
    public void Limpiar_create(View view){
        et_id_create.setText("");
        T_View_Title.setText("Title");
        T_View_Body.setText("Body");
        T_View_UserId.setText("UserId");
    }

    //Método para el botón regresar que nos lleva a la clase MainActivity.class
    public void Regresar_create(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}