package springboot.mssql.demo.service;

import java.util.List;
import org.json.simple.JSONObject;
import springboot.mssql.demo.entity.Customer;
import springboot.mssql.demo.exception.ResourceNotFoundException;
import springboot.mssql.demo.model.CustomerPutRequest;
import springboot.mssql.demo.model.CustomerRequest;

public interface CustomerService {
	
	List<Customer> createCustomers(List<Customer> customer);
	
	Customer create(CustomerRequest customer, List<String> errorMessage);
	
	List<Customer> creates(List<CustomerRequest> customer, List<String> errorMessage);
	
	List<Customer> searchCustomers(String page, String pageSize, String sortBy, String sortDir);
	
	List<Customer> findAll();
	
	Customer findById(long cusId) throws ResourceNotFoundException;
	
	boolean partialUpdate(int id, String field, String value);
	
	boolean putUpdate(int id, CustomerPutRequest request);
	
	boolean delete(int id);
	
	JSONObject exploreThirdAPI(int id);

}
