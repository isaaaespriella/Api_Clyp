/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.lasalle.controller.ControllerReview;
import org.lasalle.model.Review;
/**
 *
 * @author elena
 */
@Path("review")
public class RestReview {

    @Path("getByUser/{id_user}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUser(@PathParam("id_user") int idUser) {
        try {
            List<Review> lista = new ControllerReview().getByUser(idUser);
            return Response.ok(new Gson().toJson(lista)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }

    @Path("getByUserAndMovie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByUserAndMovie(
            @QueryParam("id_user") int idUser,
            @QueryParam("id_movie") int idMovie) {
        try {
            Review r = new ControllerReview().getByUserAndMovie(idUser, idMovie);
            if (r == null) return Response.status(404).build();
            return Response.ok(new Gson().toJson(r)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }

    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Review r) {
        try {
            r = new ControllerReview().save(r);
            return Response.status(201).entity(new Gson().toJson(r)).build();
        } catch (SQLIntegrityConstraintViolationException e) {
            return Response.status(409).entity("{\"error\":\"Review already exists for this movie\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al guardar\"}").build();
        }
    }

    @Path("update")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Review r) {
        try {
            Review updated = new ControllerReview().update(r);
            if (updated == null) return Response.status(404).entity("{\"error\":\"Review not found\"}").build();
            return Response.ok(new Gson().toJson(updated)).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error al actualizar\"}").build();
        }
    }

    @Path("delete/{id_review}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id_review") int idReview) {
        try {
            boolean ok = new ControllerReview().delete(idReview);
            if (!ok) return Response.status(404).entity("{\"error\":\"Not found\"}").build();
            return Response.ok("{\"deleted\":true}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\":\"Error\"}").build();
        }
    }
}

