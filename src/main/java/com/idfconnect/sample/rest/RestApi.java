package com.idfconnect.sample.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * <p>RestApi class.</p>
 *
 * @author nghia and Tony
 * @since 1.0
 */
@Path("api")
public class RestApi {

    @Context
    HttpServletResponse servletResponse;

    @Context
    HttpServletRequest servletRequest;


    /**
     * <p>get.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     * @since 1.0
     */
    @GET
    @Path("sample")
    @Produces(MediaType.TEXT_PLAIN)
    public Response get(){
        return Response
                .status(Response.Status.OK)
                .entity("This is the protected page with sample content")
                .build();
    }

    /**
     * <p>getPublic.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @GET
    @Path("public")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublic(){
        return Response
                .status(Response.Status.OK)
                .entity("This is the public content page")
                .build();
    }

    /**
     * <p>admin.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @GET
    @Path("admin")
    @Produces(MediaType.TEXT_PLAIN)
    public Response admin(){
        return Response
                .status(Response.Status.OK)
                .entity("This is the protected page with Admin content")
                .build();
    }

    /**
     * <p>logout.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     */
    @GET
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(){
        return Response
                .status(Response.Status.OK)
                .entity("Success")
                .build();
    }



    /**
     * <p>post.</p>
     *
     * @param dummy a {@link java.lang.String} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     * @since 1.0
     */
    @POST
    @Path("sample")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response post(String dummy){
        return Response
                .status(Response.Status.OK)
                .entity("created")
                .build();
    }

    /**
     * <p>update.</p>
     *
     * @param dummy a {@link java.lang.String} object.
     * @return a {@link javax.ws.rs.core.Response} object.
     * @since 1.0
     */
    @PUT
    @Path("sample")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(String dummy){
        return Response
                .status(Response.Status.OK)
                .entity("You have sent us "+ dummy)
                .build();
    }

    /**
     * <p>delete.</p>
     *
     * @return a {@link javax.ws.rs.core.Response} object.
     * @since 1.0
     */
    @DELETE
    @Path("sample")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(){
        return Response
                .status(Response.Status.OK)
                .entity("deleted")
                .build();
    }

}
