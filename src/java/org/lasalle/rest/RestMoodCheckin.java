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
        try {
            List<MoodCheckin> lista = new ControllerMoodCheckin().getAll(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al traer checkins\"}").build();
        }
    }

    @Path("getByUser/{id_user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@PathParam("id_user") int idUser) {
        try {
            List<MoodCheckin> lista = new ControllerMoodCheckin().getAll(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(MoodCheckin m) {
        try {
            m = new ControllerMoodCheckin().save(m);
            return Response.ok(new Gson().toJson(m)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al insertar\"}").build();
        }
    }

    @Path("update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(MoodCheckin m) {
        try {
            MoodCheckin updated = new ControllerMoodCheckin().update(m);
            if (updated == null) return Response.status(404).entity("{\"error\":\"Checkin not found\"}").build();
            return Response.ok(new Gson().toJson(updated)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al actualizar\"}").build();
        }
    }

    @Path("delete/{id_checkin}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_checkin") int idCheckin) {
        try {
            boolean ok = new ControllerMoodCheckin().delete(idCheckin);
            if (!ok) return Response.status(404).entity("{\"error\":\"Not found\"}").build();
            return Response.ok("{\"deleted\":true}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al eliminar\"}").build();
        }
    }
}
