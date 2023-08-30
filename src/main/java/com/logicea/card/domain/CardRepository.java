package com.logicea.card.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author infilitry
 *
 */

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	
	String FILTER_COLOR_AND_NAME_AND_STATUS = 
			"select c from Card c where UPPER(c.color) like CONCAT('%',UPPER(?1),'%') and UPPER(c.name) like CONCAT('%',UPPER(?2),'%') and UPPER(c.status) like CONCAT('%',UPPER(?3),'%')";
	
	@Query(FILTER_COLOR_AND_NAME_AND_STATUS)
    Page<Card> findByColorLikeAndNameLikeAndStatusLike(String colorFilter, String nameFilter, String statusFilter, Pageable pageable);

	Page<Card> findByUserId(Long userId, Pageable pageable);
}
