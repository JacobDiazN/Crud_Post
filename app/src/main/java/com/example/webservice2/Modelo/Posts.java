package com.example.webservice2.Modelo;

//La clase tiene cuatro atributos privados: id (entero), title (cadena de texto), body (cadena de texto) y userId (entero).
// Estos atributos representan los campos de información que se espera recibir para un objeto de tipo Posts.
public class Posts {
    private int id;
    private String title;
    private String body;
    private int userId;

    //Con esto métodos getters se pueden obtener los valores de los atributos desde otras partes del código.
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getUserId() {
        return userId;
    }
}
  