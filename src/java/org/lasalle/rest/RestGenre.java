/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerGenre;
import org.lasalle.model.Genre;

/**
 *
 * @author elena
 */
@Path("genre")
public class RestGenre {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        ControllerGenre cg = new ControllerGenre();
        String out = "";
        Gson gson = new Gson();

        try {

            List<Genre> lista = cg.getAll();
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer géneros"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Genre g) throws Exception {

        ControllerGenre cg = new ControllerGenre();

        g = cg.save(g);

        String out = "";

        if (g.getId_genre() != 0) {

            Gson gson = new Gson();
            out = gson.toJson(g);

        } else {

            out = """
                  {"response":"Error al insertar"}
                  """;
        }

        return Response.ok(out).build();
    }
}
