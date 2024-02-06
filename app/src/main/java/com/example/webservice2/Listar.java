package com.example.webservice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.webservice2.Interface.ServicioWebPosts;
import com.example.webservice2.Modelo.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Listar extends AppCompatActivity {

    //Aquí se está declarando una variable de instancia para un elemento de la interfaz de usuario,
    //como un TextView para ingresar datos.
    private TextView mJsonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        //Estas líneas de codigo buscan las vistas en el layout por sus IDs,
        //y las asignan a las variables correspondientes, permitiendo el acceso a estos elementos desde el código.
        mJsonText = (TextView)findViewById(R.id.jsonText);

        //Cuando se hace clic en este boton listar, se llaman al método getPosts.
        getPosts();

    }

    //Este es el Método para realizar la solicitud HTTP y obtener la lista de todos posts del API
    private void getPosts(){

        //Configuración de Retrofit para realizar la solicitudes HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creación de la interfaz del servicio web utilizando Retrofit
        ServicioWebPosts servicioWebPosts = retrofit.create(ServicioWebPosts.class);

        //Creación de la solicitud para obtener la lista de posts
        Call<List<Posts>> call = servicioWebPosts.getPosts();

        //Realización de la solicitud asíncrona utilizando enqueue
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {

                if(!response.isSuccessful()){
                    mJsonText.setText("Codigo" + response.code());
                    return;
                }

                //Obtención de la lista de posts desde la respuesta
                List<Posts> postsList = response.body();
                mJsonText.setPadding(60, 250, 60, 0);

                //Iteración a través de la lista de posts y construcción del contenido
                for(Posts post: postsList){
                    String content = "";
                    content += "\n";
                    content += "userId:" + post.getUserId() + "\n";
                    content += "id:" + post.getId() + "\n";
                    content += "title:" + post.getTitle() + "\n";
                    content += "body:" + post.getBody() + "\n \n"
                    + "--------------------------------------------------------------------------------" + "\n";

                    //Con esta línea de coódigo agregamos el contenido al TextView
                    mJsonText.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                mJsonText.setText(t.getMessage());
            }
        });
    }

    //Método para el botón regresar
    public void Regresar_read(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}