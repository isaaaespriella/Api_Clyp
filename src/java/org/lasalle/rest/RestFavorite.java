/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerFavorite;
import org.lasalle.model.Favorite;
/**
 *
 * @author elena
 */
@Path("favorite")
public class RestFavorite {

    @Path("getByUser/{id_user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@PathParam("id_user") int idUser) {
        try {
            List<Favorite> lista = new ControllerFavorite().getByUser(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Favorite f) {
        try {
            f = new ControllerFavorite().save(f);
            return Response.ok(new Gson().toJson(f)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al guardar\"}").build();
        }
    }

    @Path("delete/{id_favorite}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_favorite") int idFavorite) {
        try {
            boolean ok = new ControllerFavorite().delete(idFavorite);
            if (!ok) return Response.status(404).entity("{\"error\":\"Not found\"}").build();
            return Response.ok("{\"deleted\":true}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }
}
