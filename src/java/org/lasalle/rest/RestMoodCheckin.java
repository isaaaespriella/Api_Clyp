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
import org.lasalle.controller.ControllerMoodCheckin;
import org.lasalle.model.MoodCheckin;

/**
 *
 * @author elena
 */
@Path("checkin")
public class RestMoodCheckin {

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("id_user") int idUser) {

        ControllerMoodCheckin cm = new ControllerMoodCheckin();
        String out = "";
        Gson gson = new Gson();

        try {

            List<MoodCheckin> lista = cm.getAll(idUser);
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer checkins"}
                  """;
        }

        return Response.ok(out).build();
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(MoodCheckin m) throws Exception {

        ControllerMoodCheckin cm = new ControllerMoodCheckin();

        m = cm.save(m);

        String out = "";

        if (m.getId_checkin() != 0) {

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
