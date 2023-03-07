package springboot.mssql.demo.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.mssql.demo.entity.CardType;

@Repository
public interface CardTypeRepository extends JpaRepository<CardType, Long>  {
	@Query(value = "SELECT * FROM card_type ", nativeQuery = true)
	List<CardType> searchCustomers(Pageable pageable);
}
