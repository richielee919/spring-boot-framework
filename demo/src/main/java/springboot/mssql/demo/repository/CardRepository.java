package springboot.mssql.demo.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.mssql.demo.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>  {
	@Query(value = "SELECT * FROM card ", nativeQuery = true)
	List<Card> searchCustomers(Pageable pageable);
}
