package calc;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
@Singleton
public class CalcService {
    private CalcCache calcCache= new CalcCache(100);

    private enum Operation {
        add, subtract, multiply, divide
    }

    private String calc(Operation op, String path, Float... vals) {
        String output = calcCache.getCache(path);
        if (output == null) {
            switch (op) {
                case add:
                    output = Float.toString(vals[0] + vals[1] + vals[2]);
                    break;
                case subtract:
                    output = Float.toString(vals[0] - vals[1] - vals[2]);
                    break;
                case multiply:
                    output = Float.toString(vals[0] * vals[1] * vals[2]);
                    break;
                case divide:
                    output = vals[1] == 0 ? "Divide by zero" :
                                            Float.toString(vals[0] / vals[1]);
            }
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
