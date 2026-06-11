/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.lasalle.controller.ControllerUser;
import org.lasalle.model.User;

/**
 *
 * @author elena
 */
@Path("user")
public class RestUser {
    
@Path("test")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response test() {
    return Response.ok("{\"status\":\"ok\"}").build();
}

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        ControllerUser cu = new ControllerUser();
        String out = "";
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {

            List<User> lista = cu.getAll();
            out = gson.toJson(lista);

        } catch (Exception e) {

            out = """
                  {"response":"Error al traer usuarios"}
                  """;
        }

        return Response.ok(out).build();
    }

   @Path("save")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response save(User u) {

    try {

        ControllerUser cu = new ControllerUser();

        User saved = cu.save(u);

        if (saved == null) {
            return Response.status(409)
                    .entity("{\"error\":\"Email already registered\"}")
                    .build();
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        return Response.status(201)
                .entity(gson.toJson(saved))
                .build();

    } catch (Exception e) {

        return Response.status(500)
                .entity("{\"error\":\"Internal server error\"}")
                .build();
    }
}


@Path("login")
@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response login(User credentials) {

    try {

        if (credentials.getEmail() == null
                || credentials.getPassword() == null) {

            return Response.status(400)
                    .entity("{\"error\":\"email and password are required\"}")
                    .build();
        }

        ControllerUser cu = new ControllerUser();

        User user = cu.login(
                credentials.getEmail(),
                credentials.getPassword()
        );

        if (user == null) {

            return Response.status(401)
                    .entity("{\"error\":\"Invalid credentials\"}")
                    .build();
        }

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        return Response.ok(gson.toJson(user)).build();

    } catch (Exception e) {

        return Response.status(500)
                .entity("{\"error\":\"Internal server error\"}")
                .build();
    }
}
}
