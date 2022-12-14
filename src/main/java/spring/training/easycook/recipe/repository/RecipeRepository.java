package spring.training.easycook.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.training.easycook.recipe.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

//    @Query(/**/)
//    Recipe findByIdWithUser(Long id);

}
