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
            jab.add(formatCountry(c));
        }
        return jab.build().toString();
    }

    @GET
    @Path("largestpopulation")
    @Produces("application/json")
    public String getLargestPopulations() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.get10LargestPopulations()) {
            jab.add(formatCountry(c));
        }
        return jab.build().toString();
    }

    public JsonObjectBuilder formatCountry(Country c) {
        JsonObjectBuilder job = Json.createObjectBuilder();

        job.add("code", c.getCode());
        job.add("name", c.getName());
        job.add("capital", c.getCapital());
        job.add("surface", c.getSurface());
        job.add("government", c.getGovernment());
        job.add("lat", c.getLatitude());
        job.add("iso3", c.getIso3());
        job.add("continent", c.getContinent());
        job.add("region", c.getRegion());
        job.add("population", c.getPopulation());
        job.add("lng", c.getLongitude());

        return job;
    }
}
