package springboot.mssql.demo.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.mssql.demo.entity.Customer;

@Repository
public interface  CustomerRepository extends JpaRepository<Customer, Long>  {
	@Query(value = "SELECT * FROM customer ", nativeQuery = true)
	List<Customer> findCustomers(Pageable pageable);
}
