package com.example.restservicedemo.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.service.PersonManager;

@Path("car")
public class CarRESTService {
	
	PersonManager pm = new PersonManager();
	
	@GET
	@Path("/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("carId") Long id){
		Car c = pm.getCar(id);
		return c;
	}
	
	@GET
	@Path("/carWithOwner/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCarWithOwner(@PathParam("carId") Long id){
		Car c = new Car();
		c.setId(id);
		Car car = pm.getCarWithOwner(c);
		return car;
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getAllCars() {
		List<Car> cars = pm.getAllCars();
		return cars;
	}
	

	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCar(Car car) {
		pm.addCar(car);
		return Response.status(201).entity("Car").build();
	}
	
	@DELETE
	public Response clearCars() {
		pm.clearCars();
		return Response.status(200).build();
	}

}
