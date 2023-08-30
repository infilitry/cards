package com.logicea.card.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;import org.jooq.conf.UpdateUnchangedRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import com.logicea.card.api.payload.CardQuery;
import com.logicea.card.api.payload.CreateCardRequest;
import com.logicea.card.api.payload.UpdateCardRequest;
import com.logicea.exceptions.CardNotFoundException;
import com.logicea.security.domain.User;
import com.logicea.security.domain.UserDetailsImpl;
import com.logicea.security.domain.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author infilitry
 *
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
	
	@Autowired private CardRepository cardRepository;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired private UserService userService;
	
	@Transactional
	@Override
	public CardDTO createCard(CreateCardRequest createCardRequest) {
		// TODO Auto-generated method stub	
		
		//Status sts = Status.get(createCardRequest.status());
		
		System.out.println("this is the status ...." + createCardRequest.getStatus().name());
		
		Card card = new Card();
		card.setDatecreated(LocalDateTime.now());
		card.setName(createCardRequest.getName());
		card.setColor(createCardRequest.getColor());
		card.setDescription(createCardRequest.getDescription());
		card.setStatus(createCardRequest.getStatus());
		card.setUserId(getUser().getId());
		return CardDTO.from(cardRepository.save(card));
	}

	@Transactional
	@Override
	public CardDTO updateCard(Long id, UpdateCardRequest updateCardRequest) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id).orElseThrow(() -> CardNotFoundException.of(id));
		card.setDatemodified(LocalDateTime.now());
		card.setName(updateCardRequest.getName());
		card.setColor(updateCardRequest.getColor());
		card.setDescription(updateCardRequest.getDescription());
		card.setStatus(updateCardRequest.getStatus());
		card.setUserId(getUser().getId());
		return CardDTO.from(cardRepository.save(card));
	}

	@Override
	public CardDTO retrieveCard(Long id) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id).orElseThrow(() -> CardNotFoundException.of(id));
		return CardDTO.from(card);
	}

	@Override
	public PagedResult<Card> listCardsByUser(Long userId, CardQuery cardQuery) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Sort.Direction.DESC, "datecreated");
        int pageNo = cardQuery.pageNo() > 0 ? cardQuery.pageNo() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNo, cardQuery.pageSize(), sort);
        Page<Card> page = cardRepository.findByUserId(userId, pageable);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
	}

	@Override
	public PagedResult<Card> listAllCards(CardQuery cardQuery) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Sort.Direction.DESC, "datecreated");
        int pageNo = cardQuery.pageNo() > 0 ? cardQuery.pageNo() - 1 : 0;
        Pageable pageable = PageRequest.of(pageNo, cardQuery.pageSize(), sort);
        Page<Card> page = cardRepository.findAll(pageable);
        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );
	}

	@Transactional
	@Override
	public void deleteCard(Long id) {
		// TODO Auto-generated method stub
		Card card = cardRepository.findById(id).orElseThrow(() -> CardNotFoundException.of(id));
		cardRepository.delete(card);
	}
	
	
	private final User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userService.findByEmail(userDetails.getUsername()).get();
		return user;
	}

	@Override
	public Page<Card> fetchCardDataAsPageWithFiltering(
			String colorFilter, 
			String nameFilter, 
			String statusFilter,
			int page, 
			int size, 
			List<String> sortList, 
			String sortOrder) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
		
		return cardRepository.findByColorLikeAndNameLikeAndStatusLike(colorFilter, nameFilter, statusFilter, pageable);
	}
	
	
	private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

	
}
