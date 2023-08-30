package com.logicea.card.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 
 * @author infilitry
 *
 * @param <T>
 */
public record PagedResult<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPages,
        @JsonProperty("isFirst") boolean isFirst,
        @JsonProperty("isLast") boolean isLast,
        @JsonProperty("hasNext") boolean hasNext,
        @JsonProperty("hasPrevious") boolean hasPrevious) {}
