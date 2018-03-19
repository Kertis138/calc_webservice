package calc;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
@Singleton
public class CalcService {
    private CalcCache calcCache= new CalcCache(100);

    private enum Operation {
        add,
        subtract,
        multiply,
        divide
    }

    private float operationFunc(Operation op, float a, float b) {
        switch (op) {
            case add:
                return a+b;
            case subtract:
                return a-b;
            case multiply:
                return a*b;
            case divide:
                return a/b;
        }
        return 0;
    }

    private float calcFunc(Operation op, Float ...vals) {
        float result = vals[0];
        for (int i = 1; i<vals.length; ++i)
            result = operationFunc(op, result, vals[i]);
        return result;
    }

    private String calc(Operation op, String path, Float... vals) {
        String output = calcCache.getCache(path);
        if (output == null) {
            output = Float.toString(calcFunc(op, vals));
            calcCache.cache(path, output);
        }
        return output;
    }

    @GET
    @Path("/add/{a}/{b}/{c}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@Context UriInfo uriInfo,
                        @PathParam("a") float a,
                        @PathParam("b") float b,
                        @PathParam("c") float c) {
        String output = calc(Operation.add, uriInfo.getPath(), a,b,c);
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/subtract/{a}/{b}/{c}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response subtract(@Context UriInfo uriInfo,
                             @PathParam("a") float a,
                             @PathParam("b") float b,
                             @PathParam("c") float c) {
        String output = calc(Operation.subtract, uriInfo.getPath(), a,b,c);
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/multiply/{a}/{b}/{c}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response multiply(@Context UriInfo uriInfo,
                             @PathParam("a") float a,
                             @PathParam("b") float b,
                             @PathParam("c") float c) {
        String output = calc(Operation.multiply, uriInfo.getPath(), a,b,c);
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/divide/{a}/{b}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response divide(@Context UriInfo uriInfo,
                           @PathParam("a") float a,
                           @PathParam("b") float b) {
        String output = calc(Operation.divide, uriInfo.getPath(), a,b);
        return Response.status(200).entity(output).build();
    }

}
