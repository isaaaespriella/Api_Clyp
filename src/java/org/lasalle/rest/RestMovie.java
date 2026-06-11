/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerMovie;
import org.lasalle.model.Movie;

/**
 *
 * @author elena
 */
@Path("movie")
public class RestMovie {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        ControllerMovie cm = new ControllerMovie();
        String out = "";
        Gson gson = new Gson();

        try {

            List<Movie> lista = cm.getAll();
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer películas"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Movie m) throws Exception {

        ControllerMovie cm = new ControllerMovie();

        m = cm.save(m);

        String out = "";

        if (m.getId_movie() != 0) {

            Gson gson = new Gson();
            out = gson.toJson(m);

        } else {

            out = """
                  {"response":"Error al insertar"}
                  """;
        }

        return Response.ok(out).build();
    }
}
