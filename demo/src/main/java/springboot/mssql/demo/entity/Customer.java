package springboot.mssql.demo.entity;

import java.util.Date;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cusId;
	private String cusLastName;
	private String cusFirstName;
	private String cusAddress;
	private String cusPostcode;
	private String cusCity;
	private String cusState;
	private String cusCountry;
	private String cusEmail;
	private String cusContact;
	private Date cusJoinDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Card> cards;
	
	public String getName() {
		return this.cusLastName + " " + this.cusFirstName;
	}
	
	public String getAddress() {
		return this.cusAddress + ",\n " 
				+ this.cusPostcode + " " 
				+ this.cusCity + ",\n " 
				+ this.cusState + ", " 
				+ this.cusCountry + ".";
	}
}
