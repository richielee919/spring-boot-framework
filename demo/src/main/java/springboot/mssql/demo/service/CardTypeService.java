package springboot.mssql.demo.service;

import java.util.List;
import springboot.mssql.demo.entity.CardType;

public interface CardTypeService {
	
	List<CardType> createCardType(List<CardType> cardTypes);
	
}
