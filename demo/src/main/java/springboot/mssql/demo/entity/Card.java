package springboot.mssql.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "card")
@Data
public class Card {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cdId;
	private String cdNo;
	private String cdType;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cd_cusid", nullable=false)
    private Customer customer;
	
}
