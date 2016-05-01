package com.rodrigosasaki.taxi.resource;

import com.overload.loc.Node;
import com.rodrigosasaki.taxi.agents.Passenger;
import com.rodrigosasaki.taxi.agents.Taxi;
import com.rodrigosasaki.taxi.stage.Stage;
import com.rodrigosasaki.taxi.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class StageResource {

    @Autowired
    private Stage stage;

    @GET
    @Path("state")
    @Produces(MediaType.TEXT_HTML)
    public String getCurrentState(){
        return stage.toHtml();
    }

    @POST
    @Path("step")
    @Produces(MediaType.TEXT_HTML)
    public String advanceStep(){
        stage.nextStep();
        return stage.toHtml();
    }

    @POST
    @Path("restart")
    @Produces(MediaType.TEXT_HTML)
    public String restart(){
        stage.restart();
        return stage.toHtml();
    }

    @POST
    @Path("taxi")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String addTaxi(
            @FormParam("X") Integer x,
            @FormParam("Y") Integer y) {

        String error = ValidationUtils.validCoordinates(stage, x, y);
        if(error != null) {
            return error;
        }

        Taxi taxi = new Taxi(new Node(x, y), stage);
        stage.addTaxi(taxi);
        return stage.toHtml();

    }

    @POST
    @Path("passenger")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String addPassenger(
            @FormParam("SourceX")      Integer sourceX,
            @FormParam("SourceY")      Integer sourceY,
            @FormParam("DestinationX") Integer destinationX,
            @FormParam("DestinationY") Integer destinationY) {

        String error = ValidationUtils.validCoordinates(stage, sourceX, sourceY);
        if(error != null) {
            return error;
        }

        error = ValidationUtils.validCoordinates(stage, destinationX, destinationY);
        if(error != null) {
            return error;
        }

        Passenger passenger = new Passenger(new Node(sourceX, sourceY), new Node(destinationX, destinationY));
        error = ValidationUtils.passengerRouteValid(stage, passenger);
        if (error != null){
            return error;
        }

        stage.addPassenger(passenger);
        return stage.toHtml();

    }

}
