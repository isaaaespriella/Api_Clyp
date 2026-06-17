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
        try {
            ControllerWatchedMovie cw = new ControllerWatchedMovie();
            List<WatchedMovie> lista = cw.getAll(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al traer películas vistas\"}").build();
        }
    }

    @Path("getByUser/{id_user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@PathParam("id_user") int idUser) {
        try {
            ControllerWatchedMovie cw = new ControllerWatchedMovie();
            List<WatchedMovie> lista = cw.getAll(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(WatchedMovie w) {
        try {
            ControllerWatchedMovie cw = new ControllerWatchedMovie();
            w = cw.save(w);
            return Response.ok(new Gson().toJson(w)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al guardar\"}").build();
        }
    }

    @Path("delete/{id_watched}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_watched") int idWatched) {
        try {
            ControllerWatchedMovie cw = new ControllerWatchedMovie();
            boolean ok = cw.delete(idWatched);
            if (!ok) return Response.status(404).entity("{\"error\":\"Not found\"}").build();
            return Response.ok("{\"deleted\":true}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al eliminar\"}").build();
        }
    }
    
    @Path("deleteByUserAndMovie")
@DELETE
@Produces(MediaType.APPLICATION_JSON)
public Response deleteByUserAndMovie(
        @QueryParam("id_user") int idUser,
        @QueryParam("id_movie") int idMovie) {
    try {
        boolean ok = new ControllerWatchedMovie().deleteByUserAndMovie(idUser, idMovie);
        if (!ok) return Response.status(404).entity("{\"error\":\"Not found\"}").build();
        return Response.ok().build();
    } catch (Exception e) {
        return Response.status(500).entity("{\"error\":\"Error al eliminar\"}").build();
    }
}
}
