package springboot.mssql.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "card_type")
@Data
public class CardType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ctId;
	private String ctName;
	private String ctCode;
}
