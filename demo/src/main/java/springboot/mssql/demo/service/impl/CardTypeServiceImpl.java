package springboot.mssql.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.mssql.demo.entity.CardType;
import springboot.mssql.demo.repository.CardTypeRepository;
import springboot.mssql.demo.service.CardTypeService;

@Service
public class CardTypeServiceImpl implements CardTypeService{
	
	private CardTypeRepository cardTypeRepository;
	
	public CardTypeServiceImpl(CardTypeRepository cardTypeRepository) {
		this.cardTypeRepository = cardTypeRepository; 
	}

	@Transactional
	public List<CardType> createCardType(List<CardType> cardTypes) {
		// TODO Auto-generated method stub
		return cardTypeRepository.saveAll(cardTypes);
	}
	
}
