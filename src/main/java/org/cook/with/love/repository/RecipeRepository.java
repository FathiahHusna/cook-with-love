package org.cook.with.love.repository;

import org.cook.with.love.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, UUID> {

    String SEARCH_QUERY = """
            SELECT * FROM t_recipe a WHERE
            (
                LOWER(a.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
                OR
                LOWER(a.ingredients) LIKE LOWER(CONCAT('%', :searchText, '%'))
            )
            AND
            (:category IS NULL OR :category = '' OR a.category = :category);
            """;
    @Query(value = SEARCH_QUERY, nativeQuery = true)
    List<RecipeEntity> findAllRecipeBySearchText(@Param("searchText") String searchText, @Param("category") String category);
}
