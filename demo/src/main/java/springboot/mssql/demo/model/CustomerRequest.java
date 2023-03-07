package springboot.mssql.demo.model;

import java.util.List;
import lombok.Data;

@Data
public class CustomerRequest {
	private String lastName;
	private String firstName;
	private String address;
	private String postcode;
	private String city;
	private String state;
	private String country;
	private String email;
	private String contact;
	private String joinDate;
	
	private List<CardRequest> card;
}
