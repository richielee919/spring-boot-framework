package springboot.mssql.demo.service.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.micrometer.common.util.StringUtils;
import springboot.mssql.demo.entity.Card;
import springboot.mssql.demo.entity.Customer;
import springboot.mssql.demo.exception.ResourceNotFoundException;
import springboot.mssql.demo.model.CardRequest;
import springboot.mssql.demo.model.CustomerPutRequest;
import springboot.mssql.demo.model.CustomerRequest;
import springboot.mssql.demo.repository.CardRepository;
import springboot.mssql.demo.repository.CustomerRepository;
import springboot.mssql.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	private CustomerRepository customerRepository;
	private CardRepository cardRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, CardRepository cardRepository) {
		this.customerRepository = customerRepository;
		this.cardRepository = cardRepository; 
	}

	public List<Customer> createCustomers(List<Customer> customers) {
		customerRepository.saveAll(customers);
		return customers;
	}

	@Transactional
	public Customer create(CustomerRequest custReq, List<String> errorMessage) {
		Customer customer = null;
		
		if (StringUtils.isEmpty(custReq.getFirstName()))
			errorMessage.add("Please fill in First Name");
		
		if (StringUtils.isEmpty(custReq.getLastName()))
			errorMessage.add("Please fill in Last Name");
		
		if (StringUtils.isEmpty(custReq.getEmail()))
			errorMessage.add("Please fill in Email");
		
		if (errorMessage.size() == 0) {
			customer = new Customer();
			customer.setCusLastName(custReq.getLastName());
			customer.setCusFirstName(custReq.getFirstName());
			customer.setCusAddress(custReq.getAddress());
			customer.setCusPostcode(custReq.getPostcode());
			customer.setCusCity(custReq.getCity());
			customer.setCusState(custReq.getState());
			customer.setCusCountry(custReq.getCountry());
			customer.setCusEmail(custReq.getEmail());
			customer.setCusContact(custReq.getContact());
			try {
				customer.setCusJoinDate(new SimpleDateFormat("dd/MM/yyyy").parse(custReq.getJoinDate()));
			} catch(Exception ex) {}
			
			customerRepository.save(customer);
			
			List<CardRequest> cards = custReq.getCard();
			
			List<Card> newCard = new ArrayList<>();
			
			for (Iterator<CardRequest> iterator = cards.iterator(); iterator.hasNext();) {
				CardRequest req = iterator.next();
				
				Card card = new Card();
				card.setCdNo(req.getNo());
				card.setCdType(req.getType());
				card.setCustomer(customer);
				cardRepository.save(card);
				newCard.add(card);
			}
			customer.setCards(newCard);
		}
		
		return customer;
	}
	
	@Transactional
	public List<Customer> creates(List<CustomerRequest> custReq, List<String> errorMessage) {
		List<Customer> customerList = new ArrayList<>();
		custReq.forEach(e-> {
			
			if (StringUtils.isEmpty(e.getFirstName()))
				errorMessage.add("Please fill in First Name");
			
			if (StringUtils.isEmpty(e.getLastName()))
				errorMessage.add("Please fill in Last Name");
			
			if (StringUtils.isEmpty(e.getEmail()))
				errorMessage.add("Please fill in Email");
			
			Customer customer = new Customer();
			customer.setCusLastName(e.getLastName());
			customer.setCusFirstName(e.getFirstName());
			customer.setCusAddress(e.getAddress());
			customer.setCusPostcode(e.getPostcode());
			customer.setCusCity(e.getCity());
			customer.setCusState(e.getState());
			customer.setCusCountry(e.getCountry());
			customer.setCusEmail(e.getEmail());
			customer.setCusContact(e.getContact());
			try {
				customer.setCusJoinDate(new SimpleDateFormat("dd/MM/yyyy").parse(e.getJoinDate()));
			} catch(Exception ex) {}
			
			customerRepository.save(customer);
			
			List<CardRequest> cards = e.getCard();
			
			List<Card> newCard = new ArrayList<>();
			
			for (Iterator<CardRequest> iterator = cards.iterator(); iterator.hasNext();) {
				CardRequest req = iterator.next();
				
				Card card = new Card();
				card.setCdNo(req.getNo());
				card.setCdType(req.getType());
				card.setCustomer(customer);
				cardRepository.save(card);
				newCard.add(card);
			}
			customer.setCards(newCard);
			customerList.add(customer);
		});
		return customerList;
	}

	@Transactional
	public List<Customer> searchCustomers(String page, String pageSize, String sortBy, String sortDir) {
		List<Customer> dataSource = null;
		
		if (StringUtils.isEmpty(page) && StringUtils.isEmpty(pageSize) && StringUtils.isEmpty(sortBy) && StringUtils.isEmpty(sortDir)) {
			dataSource = customerRepository.findAll();
		} else {
			Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
	                : Sort.by(sortBy).descending();
			
	        Pageable pageable = PageRequest.of(Integer.valueOf(page) - 1, Integer.valueOf(pageSize), sort);
			
			dataSource = customerRepository.findCustomers(pageable);
		}
		
		return dataSource;
	}

	@Transactional
	public List<Customer> findAll() {
		List<Customer> dataSource = customerRepository.findAll();
		return dataSource;
	}

	@Transactional
	public Customer findById(long cusId) 
			throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(Long.valueOf(cusId))
				  .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + cusId));
		return customer;
	}
	
	@Transactional
	public boolean partialUpdate(int id, String key, String value){
		Optional<Customer> optional = customerRepository.findById(Long.valueOf(id));
		if (optional.isPresent()) {
			Customer customer = optional.get();
	
			switch (key) {
			case "cusLastName":{
				customer.setCusLastName(value);
				break;
			}
			case "cusFirstName":{
				customer.setCusFirstName(value);
				break;
			}
			case "cusAddress":{
				customer.setCusAddress(value);
				break;
			}
			case "cusPostcode":{
				customer.setCusPostcode(value);
				break;
			}
			case "cusCity":{
				customer.setCusCity(value);
				break;
			}
			case "cusState":{
				customer.setCusState(value);
				break;
			}
			case "cusCountry":{
				customer.setCusCountry(value);
				break;
			}
			case "cusEmail":{
				customer.setCusEmail(value);
				break;
			}
			case "cusContact":{
				customer.setCusContact(value);
				break;
			}
			case "cusJoinDate":{
				try {
					customer.setCusJoinDate(new SimpleDateFormat("dd/MM/yyyy").parse(value));
				} catch(Exception ex) {}
				break;
			}
			default:
				break;
			}
			customerRepository.save(customer);
	      return true;
	    } else {
	      return false;
	    }
	}
	
	@Transactional
	public boolean putUpdate(int id, CustomerPutRequest request){
		boolean status = false;
		
		Optional<Customer> optional = customerRepository.findById(Long.valueOf(id));
		
		if (optional.isPresent()) {
			Customer customer = optional.get();
			
			if (StringUtils.isNotEmpty(request.getLastName()))
				customer.setCusLastName(request.getLastName());
			
			if (StringUtils.isNotEmpty(request.getFirstName()))
				customer.setCusFirstName(request.getFirstName());
			
			if (StringUtils.isNotEmpty(request.getAddress()))
				customer.setCusAddress(request.getAddress());
			
			if (StringUtils.isNotEmpty(request.getPostcode()))
				customer.setCusPostcode(request.getPostcode());
			
			if (StringUtils.isNotEmpty(request.getCity()))
				customer.setCusCity(request.getCity());
			
			if (StringUtils.isNotEmpty(request.getState()))
				customer.setCusState(request.getState());
			
			if (StringUtils.isNotEmpty(request.getCountry()))
				customer.setCusCountry(request.getCountry());
			
			if (StringUtils.isNotEmpty(request.getEmail()))
				customer.setCusEmail(request.getEmail());
			
			if (StringUtils.isNotEmpty(request.getContact()))
				customer.setCusContact(request.getContact());
			
			customer = customerRepository.save(customer);
			
			if (customer != null)
				status = true;
		}
		
		return status;
	}
	
	@Transactional
	public boolean delete(int id){
		boolean status = false;
		
		Optional<Customer> optional = customerRepository.findById(Long.valueOf(id));
		
		if (optional.isPresent()) {
			Customer customer = optional.get();
			
			customerRepository.delete(customer);
			status = true;
		}
		
		return status;
	}
	
	@Transactional
	public JSONObject exploreThirdAPI(int cusId) {
		Optional<Customer> optional = customerRepository.findById(Long.valueOf(cusId));
		JSONObject object = null;
		if (optional.isPresent()) {
			object = new JSONObject();
			object.put("User", optional.get());
			
			// Setting URL
			String url_str = "https://v6.exchangerate-api.com/v6/88bc3d1e826085f301d3a2ce/latest/USD";

			try {
				// Making Request
				URL url = new URL(url_str);
				HttpURLConnection request = (HttpURLConnection) url.openConnection();
				request.connect();

				// Convert to JSON
				JSONParser jp = new JSONParser();
				JSONObject root = (JSONObject) jp.parse(new InputStreamReader((InputStream) request.getContent()));

				object.put("thirdParty", root);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return object;
	}
	
}
