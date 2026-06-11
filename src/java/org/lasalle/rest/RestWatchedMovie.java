/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import jakarta.ws.rs.QueryParam;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerWatchedMovie;
import org.lasalle.model.WatchedMovie;

/**
 *
 * @author elena
 */
@Path("watched")
public class RestWatchedMovie {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
  public Response getAll(@QueryParam("id_user") int idUser) {

        ControllerWatchedMovie cw = new ControllerWatchedMovie();
        String out = "";
        Gson gson = new Gson();

        try {

            List<WatchedMovie> lista = cw.getAll(idUser);
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer películas vistas"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(WatchedMovie w) throws Exception {

        ControllerWatchedMovie cw = new ControllerWatchedMovie();

        w = cw.save(w);

        String out = "";

        if (w.getId() != 0) {

            Gson gson = new Gson();
            out = gson.toJson(w);

        } else {

            out = """
                  {"response":"Error al insertar"}
                  """;
        }

        return Response.ok(out).build();
    }
}
