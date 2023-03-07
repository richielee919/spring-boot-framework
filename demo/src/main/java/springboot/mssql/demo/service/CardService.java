package springboot.mssql.demo.service;

import java.util.List;
import springboot.mssql.demo.entity.Card;

public interface CardService {
	
	List<Card> createCard(List<Card> cards);
	
}
