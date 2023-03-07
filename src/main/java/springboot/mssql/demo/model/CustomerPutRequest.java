package springboot.mssql.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerPutRequest {
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
}
