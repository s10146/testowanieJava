package com.example.restservicedemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;

public class CarManager {
	
	private Connection connection;
	
	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
//	private static final String CREATE_TABLE_CAR = "CREATE TABLE Car(c_id bigint GENERATED BY DEFAULT AS IDENTITY,"
//			+ "make varchar(20), model varchar(20), yop integer, owner_id bigint," 
//					+ "FOREIGN KEY (owner_id) references Person (p_id), PRIMARY KEY (c_id))";
	private static final String CREATE_TABLE_CAR = "CREATE TABLE Car(c_id bigint PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, make varchar(20), model varchar(20), yop integer, owner_id bigint FOREIGN KEY references Person(p_id))";

	private PreparedStatement addCarsStmt;
	private PreparedStatement sellCarStmt;
	private PreparedStatement deleteAllCarsStmt;
	private PreparedStatement getAllCarsStmt;
	private PreparedStatement getCarByIdStmt;
	private PreparedStatement getCarsByOwnerIdStmt;
	private PreparedStatement getCarWithOwnerStmt;
	
	private Statement statement;
	
	public CarManager() {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			
			while (rs.next()) {
				if ("Car".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			
			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_CAR);

			addCarsStmt = connection
					.prepareStatement("INSERT INTO Car (c_id, make, model, yop, owner_id) VALUES (?, ?, ?, ?, ?)");
			sellCarStmt = connection
					.prepareStatement("UPDATE Car SET owner_id = ? WHERE c_id = ?");
			deleteAllCarsStmt = connection
					.prepareStatement("DELETE FROM Car");
			getAllCarsStmt = connection
					.prepareStatement("SELECT c_id, make, model, yop, owner_id FROM Car");
			
			getCarByIdStmt = connection
					.prepareStatement("SELECT c_id, make, model, yop, owner_id FROM Car where c_id = ?");
			
			getCarsByOwnerIdStmt = connection
					.prepareStatement("SELECT c_id, make, model, yop, owner_id FROM Car where owner_id = ?");
			
			getCarWithOwnerStmt = connection
					.prepareStatement("SELECT p_id, name, yob, c_id, make, model, yop, owner_id FROM Person JOIN Car ON c_id = ?");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	Connection getConnnection() {
		return connection;
	}
	
	public void clearCars() {
		try {
			deleteAllCarsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addCar(Car car) {
		int count = 0;
		
		try {
			addCarsStmt.setLong(1, car.getId());
			addCarsStmt.setString(2, car.getMake());
			addCarsStmt.setString(3, car.getModel());
			addCarsStmt.setInt(4, car.getYop());
			addCarsStmt.setLong(5, car.getOwner().getId());
			
			count = addCarsStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<Car> getAllCars() {
		List<Car> cars = new ArrayList<Car>();
		
		try {
			ResultSet rs = getAllCarsStmt.executeQuery();
			
			while (rs.next()) {
				Car c = new Car();
				c.setId(rs.getInt("c_id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				
				Person owner = new Person();
				owner.setId(rs.getLong("owner_id"));
				c.setOwner(owner);
				
				cars.add(c);
			}
 		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
	}
	
	public Car getCar(Long id) {
		Car c = new Car();
		
		try {
			getCarByIdStmt.setLong(1, id);
			ResultSet rs = getCarByIdStmt.executeQuery();
			
			while (rs.next()) {
				c.setId(rs.getInt("c_id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				Person owner = new Person();
				owner.setId(rs.getLong("owner_id"));
				c.setOwner(owner);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public List<Car> getOwnerCars(Person owner) {
		List<Car> car = new ArrayList<Car>();
		
		try {
			getCarsByOwnerIdStmt.setLong(1, owner.getId());
			
			ResultSet rs = getCarsByOwnerIdStmt.executeQuery();
			
			while (rs.next()) {
				Car c = new Car();
				c.setId(rs.getLong("c_id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				owner.setId(rs.getLong("owner_id"));
				c.setOwner(owner);
				car.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return car;
	}
	
	
	
	
	public Car getCarWithOwner(Car car) {
		Car c = new Car();
		
		try {
			getCarWithOwnerStmt.setLong(1, car.getId());
			ResultSet rs = getCarWithOwnerStmt.executeQuery();
			
			while (rs.next()) {
				Person p = new Person();
				
				p.setId(rs.getInt("p_id"));
				p.setFirstName(rs.getString("name"));
				p.setYob(rs.getInt("yob"));
				
				c.setId(rs.getInt("c_id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				
				c.setOwner(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	public int sellCar(Car car, Person person) {
		int count = 0;
		
		try {
			sellCarStmt.setLong(1, person.getId());
			sellCarStmt.setLong(2, car.getId());
			
			count = sellCarStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
