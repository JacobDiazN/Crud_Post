package com.example.webservice2.Interface;

import com.example.webservice2.Modelo.Posts;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//La interfaz ServicioWebPosts define los métodos para interactuar con el servicio web.
public interface ServicioWebPosts {

    //con este metodo obtenemos toda la información de la URL
    //La anotación @GET("posts") indica que esta solicitud HTTP es un método GET a la ruta "posts".
    // El método devuelve un objeto Call que envuelve una lista de objetos Posts.
    @GET("posts")
    Call<List<Posts>> getPosts();

    //Con este metodo obtenemos un solo post de la URL
    //La anotación @GET("posts/{postId}") indica que esta solicitud HTTP es un método GET a la ruta "posts/{postId}".
    //El método toma un parámetro postId que se utiliza para reemplazar {postId} en la URL.
    // El método devuelve un objeto Call que envuelve un objeto Posts.
    @GET("posts/{postId}")
    Call<Posts> getSinglePost(@Path("postId") String postId);
}

