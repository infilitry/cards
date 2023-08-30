package com.logicea.card.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import com.logicea.card.api.payload.CardQuery;
import com.logicea.card.api.payload.CreateCardRequest;
import com.logicea.card.api.payload.UpdateCardRequest;

import ch.qos.logback.classic.jul.LevelChangePropagator;


/**
 * 
 * @author infilitry
 *
 */
public interface CardService {
	
	public CardDTO createCard(CreateCardRequest createCardRequest);
	
	public CardDTO updateCard(Long id, UpdateCardRequest updateCardRequest);
	
	public CardDTO retrieveCard(Long id);
	
	public PagedResult<Card> listCardsByUser(Long userId, CardQuery cardQuery);
	
	public PagedResult<Card> listAllCards(CardQuery cardQuery);
	
	public Page<Card> fetchCardDataAsPageWithFiltering(
			String colorFilter, 
			String nameFilter, 
			String statusFilter,  
			int page, 
			int size,
			@RequestParam(defaultValue = "") List<String> sortList,
            @RequestParam(defaultValue = "DESC") String sortOrder);
	
	public void deleteCard(Long id);
	
	

}
