package com.logicea.card.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.logicea.card.api.payload.CardQuery;
import com.logicea.card.api.payload.CreateCardRequest;
import com.logicea.card.api.payload.UpdateCardRequest;
import com.logicea.card.domain.Card;
import com.logicea.card.domain.CardDTO;
import com.logicea.card.domain.CardService;
import com.logicea.card.domain.PagedResult;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

/**
 * 
 * @author infilitry
 *
 */

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/card")
@Tag(name = "Cards", description = "Cards management APIs")
public class CardController {
	
	@Autowired private CardService cardService;
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    PagedResult<Card> findCards(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize) {
        CardQuery cardQuery = new CardQuery(pageNo, pageSize);
        return cardService.listAllCards(cardQuery);
    }
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    ResponseEntity<CardDTO> createCard(@RequestBody @Validated CreateCardRequest request) {
        
        CardDTO cardDTO = cardService.createCard(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/v1/card/{id}")
                .buildAndExpand(cardDTO.id()).toUri();
        return ResponseEntity.created(location).body(cardDTO);
    }
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	ResponseEntity<CardDTO> updateCard(
				@PathVariable(name = "id") Long id,
                @RequestBody @Validated UpdateCardRequest request) {
       CardDTO cardDTO = cardService.updateCard(id, request);
       URI location = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/v1/card/{id}")
               .buildAndExpand(cardDTO.id()).toUri();
       return ResponseEntity.created(location).body(cardDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    ResponseEntity<CardDTO> findById(@PathVariable(name = "id") Long id) {
        CardDTO cardDTO = cardService.retrieveCard(id);
        return ResponseEntity.ok(cardDTO);
    }
    
    
    @GetMapping("/search/cards")
    public Page<Card> fetchCustomersWithPageInterfaceAndSorted(@RequestParam(defaultValue = "") String colorFilter,
                                                                   @RequestParam(defaultValue = "") String nameFilter,
                                                                   @RequestParam(defaultValue = "") String statusFilter,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "30") int size,
                                                                   @RequestParam(defaultValue = "") List<String> sortList,
                                                                   @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
    	
    	return cardService.fetchCardDataAsPageWithFiltering(colorFilter, nameFilter, statusFilter, page, size, sortList, sortOrder.toString());
    	
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    void delete(@PathVariable(name = "id") Long id) {
        cardService.deleteCard(id);
    }

}
