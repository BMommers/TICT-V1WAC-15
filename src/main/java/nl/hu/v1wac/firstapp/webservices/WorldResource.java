package nl.hu.v1wac.firstapp.webservices;

import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;
import javax.json.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/countries")
public class WorldResource {

    @GET
    @Produces("application/json")
    public String getCountries() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.getAllCountries()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("naam", c.getName());

            jab.add(job);
        }
        JsonArray array = jab.build();
        return array.toString();
    }



    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getOrderInfo(@PathParam("id") String id) {
        WorldService service = ServiceProvider.getWorldService();
        Country c = service.getCountryByCode(id);

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", c.getCode());
        job.add("naam", c.getName());
        return job.build().toString();
    }


    @GET
    @Path("largestsurfaces")
    @Produces("application/json")
    public String getLargestCountries() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.get10LargestSurfaces()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("naam", c.getName());
            job.add("oppervlakte", c.getSurface());

            jab.add(job);
        }

        return jab.build().toString();
    }

    @GET
    @Path("largestpopulation")
    @Produces("application/json")
    public String getLargestPopulations() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.get10LargestSurfaces()) {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("code", c.getCode());
            job.add("naam", c.getName());
            job.add("populatie", c.getPopulation());

            jab.add(job);
        }
        return jab.build().toString();
    }
}
