package com.gamexd.specification;

import com.gamexd.domain.dto.GameFilterDto;
import com.gamexd.domain.entity.Games;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GameSpecification {
    public static Specification<Games> withFilters(GameFilterDto filter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getReleaseDateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"), filter.getReleaseDateFrom()));
            }

            if (filter.getReleaseDateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("releaseDate"), filter.getReleaseDateTo()));
            }

            if (filter.getMinRating() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalRating"), filter.getMinRating()));
            }

            if (filter.getMaxRating() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalRating"), filter.getMaxRating()));
            }

            if (filter.getGenreIds() != null && !filter.getGenreIds().isEmpty()) {
                Join<Object, Object> genres = root.join("genres", JoinType.INNER);
                predicates.add(genres.get("id").in(filter.getGenreIds()));

                if (query != null) {
                    query.distinct(true);
                }
            }

            if (filter.getPlatformIds() != null && !filter.getPlatformIds().isEmpty()) {
                Join<Object, Object> platforms = root.join("platforms", JoinType.INNER);
                predicates.add(platforms.get("id").in(filter.getPlatformIds()));

                if (query != null) {
                    query.distinct(true);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
