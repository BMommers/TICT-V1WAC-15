package nl.hu.v1wac.firstapp.webservices;

import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;

import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;

@Path("/countries")
public class WorldResource {

    @GET
    @Produces("application/json")
    public String getCountries() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.getAllCountries()) {
            jab.add(formatCountry(c));
        }
        JsonArray array = jab.build();
        return array.toString();
    }


    @GET
    @Path("filter/{qry}")
    @Produces("application/json")
    public String getLargestCountries(@PathParam("qry") String filter) {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jab = Json.createArrayBuilder();

        for (Country c : service.filterCountries(filter)) {
            jab.add(formatCountry(c));
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

        return formatCountry(c).build().toString();
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

    @PUT
    @Path("{id}")
    @Produces("application/json")
    public Response updateCountry( @PathParam("id") String id,
                                   @FormParam("name") String nm,
                                   @FormParam("capital") String cap,
                                   @FormParam("surface") String surf,
                                   @FormParam("population") String pop) {

        WorldService service = ServiceProvider.getWorldService();

        if (!service.update(id, nm, cap, surf, pop)) {
            Map<String, String> messages = new HashMap<String, String>();
            messages.put("error", "Country does not exist!");
            return Response.status(409).entity(messages).build();
        }

        Country country = service.getCountryByCode(id);
        return Response.ok(country).build();
    }

    @PUT
    @Path("new")
    @Produces("application/json")
    public Response newCountry( @FormParam("code") String id,
                                   @FormParam("name") String nm,
                                   @FormParam("capital") String cap,
                                   @FormParam("surface") String surf,
                                   @FormParam("population") String pop) {

        WorldService service = ServiceProvider.getWorldService();

        if (!service.save(id, nm, cap, surf, pop)) {
            Map<String, String> messages = new HashMap<String, String>();
            messages.put("error", "Country not created!");
            return Response.status(409).entity(messages).build();
        }

        Country country = service.getCountryByCode(id);
        return Response.ok(country).build();
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("{id}")
    @Produces("application/json")
    public Response deleteCountry(@Context SecurityContext sc,
                                  @PathParam("id") String id) {
        WorldService service = ServiceProvider.getWorldService();

        if (!service.delete(id)) {
            return Response.status(404).build();
        }

        return Response.ok().build();
    }

}
