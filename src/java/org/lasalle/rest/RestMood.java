/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerMood;
import org.lasalle.model.Mood;

/**
 *
 * @author elena
 */
@Path("mood")
public class RestMood {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        ControllerMood cm = new ControllerMood();
        String out = "";
        Gson gson = new Gson();

        try {

            List<Mood> lista = cm.getAll();
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer moods"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Mood m) throws Exception {

        ControllerMood cm = new ControllerMood();

        m = cm.save(m);

        String out = "";

        if (m.getId_mood() != 0) {

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
