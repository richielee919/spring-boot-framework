package springboot.mssql.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import springboot.mssql.demo.entity.CardType;
import springboot.mssql.demo.entity.Customer;
import springboot.mssql.demo.exception.ResourceNotFoundException;
import springboot.mssql.demo.model.CustomerPutRequest;
import springboot.mssql.demo.model.CustomerRequest;
import springboot.mssql.demo.model.PatchCustomerEntity;
import springboot.mssql.demo.model.ResponseAPI;
import springboot.mssql.demo.model.ThirdPartyRequest;
import springboot.mssql.demo.service.CardTypeService;
import springboot.mssql.demo.service.CustomerService;
import springboot.mssql.demo.util.AppConstants;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	private final CardTypeService cardTypeService;

	// CREATE
	@PostMapping("create")
	public ResponseEntity<ResponseAPI> create(@RequestBody CustomerRequest cust,
			@RequestHeader("Authorization") String authorization) {
		List<String> errorMessage = new ArrayList<>();
		Customer customers = customerService.create(cust, errorMessage);
		
		if (errorMessage.size() == 0)
			return new ResponseEntity<ResponseAPI>(ResponseAPI.success(customers), HttpStatus.CREATED);
		else
			return new ResponseEntity<ResponseAPI>(ResponseAPI.fail(errorMessage), HttpStatus.CREATED);
	}
	
	@PostMapping("creates")
	public ResponseEntity<ResponseAPI> creates(@RequestBody List<CustomerRequest> cust,
			@RequestHeader("Authorization") String authorization) {
		List<String> errorMessage = new ArrayList<>();
		List<Customer> customers = customerService.creates(cust, errorMessage);
		return new ResponseEntity<ResponseAPI>(ResponseAPI.success(customers), HttpStatus.CREATED);
	}
	
	@PostMapping("/cardtype/create")
	public ResponseEntity<ResponseAPI> createCardType(@RequestBody List<CardType> cds) {
		List<CardType> cards = cardTypeService.createCardType(cds);
		return new ResponseEntity<ResponseAPI>(ResponseAPI.success(cards), HttpStatus.CREATED);
	}
	
	// READ
	@GetMapping("/search")
	public List<Customer> searchCustomer(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER_STR, required = false) String pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE_STR, required = false) String pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
		
		return customerService.searchCustomers(pageNo, pageSize, sortBy, sortDir);
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") int cusId)
		throws ResourceNotFoundException {
		Customer customer = customerService.findById(Long.valueOf(cusId));
		return ResponseEntity.ok().body(customer);
	}
	
	// UPDATE
	@PatchMapping("/update/{id}")
	ResponseEntity<ResponseAPI> updatePartialPatch(@PathVariable(name = "id") int id,
		      @RequestBody PatchCustomerEntity request){
		List<String> errorMessage = new ArrayList<>();
		boolean status = false;
		if (request.getOp().equalsIgnoreCase("update")) {
			status = customerService.partialUpdate(id, request.getKey(), request.getValue());
			
		} else {
			errorMessage.add("This API on available update.");
		}
		
		if (errorMessage.size() == 0 && status)
			return new ResponseEntity<ResponseAPI>(ResponseAPI.success("Customer ID: " + id + " updated."), HttpStatus.OK);
		else
			return new ResponseEntity<ResponseAPI>(ResponseAPI.fail(errorMessage), HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/update/{id}")
	ResponseEntity<ResponseAPI> updatePartialPut(@PathVariable(name = "id") int id,
		      @RequestBody CustomerPutRequest request){
		List<String> errorMessage = new ArrayList<>();
		boolean status = customerService.putUpdate(id, request);
		
		if (errorMessage.size() == 0 && status)
			return new ResponseEntity<ResponseAPI>(ResponseAPI.success("Customer ID: " + id + " updated."), HttpStatus.OK);
		else
			return new ResponseEntity<ResponseAPI>(ResponseAPI.fail(errorMessage), HttpStatus.FORBIDDEN);
	}
	
	// DELETE
	@DeleteMapping("/delete/{id}")
	ResponseEntity<ResponseAPI> delete(@PathVariable(name = "id") int id){
		boolean status = customerService.delete(id);
		
		if (status)
			return new ResponseEntity<ResponseAPI>(ResponseAPI.success("Customer ID: " + id + " deleted."), HttpStatus.OK);
		else
			return new ResponseEntity<ResponseAPI>(ResponseAPI.fail("Failed to delete."), HttpStatus.FORBIDDEN);
	}
	
	// Explore an api which will nested calling another api from 3rd party.
	@PostMapping("explore/thirdparty")
	ResponseEntity<ResponseAPI> explore(@RequestBody ThirdPartyRequest request){
		JSONObject object = customerService.exploreThirdAPI(Integer.valueOf(request.getId()));
		
		if (object != null)
			return new ResponseEntity<ResponseAPI>(ResponseAPI.success(object), HttpStatus.OK);
		else
			return new ResponseEntity<ResponseAPI>(ResponseAPI.fail("Fail to extract third party data."), HttpStatus.BAD_REQUEST);
	}
	
}
