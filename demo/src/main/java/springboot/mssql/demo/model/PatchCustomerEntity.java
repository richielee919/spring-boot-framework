package springboot.mssql.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PatchCustomerEntity {
	String op;
	
	String key;
	
	String value;
}
