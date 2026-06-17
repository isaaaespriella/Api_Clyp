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
public Response save(Genre g) {
    if (g == null || g.getName() == null || g.getName().isBlank()) {
        return Response.status(400).entity("{\"error\":\"name is required\"}").build();
    }
    try {
        ControllerGenre cg = new ControllerGenre();
        g = cg.save(g);
        return Response.ok(new Gson().toJson(g)).build();
    } catch (Exception e) {
        return Response.status(500).entity("{\"error\":\"Error al insertar\"}").build();
    }
}
    
    
    @Path("update")
@PUT
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response update(Genre g) {
    try {
        Genre updated = new ControllerGenre().update(g);
        if (updated == null) return Response.status(404).entity("{\"error\":\"Genre not found\"}").build();
        return Response.ok(new Gson().toJson(updated)).build();
    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        return Response.status(409).entity("{\"error\":\"Genre name already exists\"}").build();
    } catch (Exception e) {
        return Response.status(500).entity("{\"error\":\"Error al actualizar\"}").build();
    }
}

@Path("delete/{id_genre}")
@DELETE
@Produces(MediaType.APPLICATION_JSON)
public Response delete(@PathParam("id_genre") int idGenre) {
    try {
        boolean ok = new ControllerGenre().delete(idGenre);
        if (!ok) return Response.status(404).entity("{\"error\":\"Genre not found\"}").build();
        return Response.ok("{\"deleted\":true}").build();
    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
        return Response.status(409).entity("{\"error\":\"genre in use\"}").build();
    } catch (Exception e) {
        return Response.status(500).entity("{\"error\":\"Error al eliminar\"}").build();
    }
}
}
