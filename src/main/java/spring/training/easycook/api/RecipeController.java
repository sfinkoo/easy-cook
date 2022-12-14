package spring.training.easycook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.training.easycook.recipe.dto.CreateRecipeRequest;
import spring.training.easycook.recipe.dto.CreateRecipeResponse;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.recipe.service.RecipeService;

import javax.websocket.server.PathParam;
import java.util.Set;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    /*
    получить все рецепты
    получить рецепт по имени
    получить рецепты по тегу
    получить рецепты созданные в указанный период времени

    создать рецепт
    удалить рецепт

    изменить имя (старое, новое)
    изменить описание (по имени)
    изменить калории (по имени)
    изменить бжу (по имени)
    добавить (список имен рецептов, список тегов)
    удалить тег (список имен рецептов, список тегов)
     */


//    @GetMapping("/user/{userId}")
//    public Set<Recipe> getUserRecipes(@PathParam("userId") String userId) {
//        return recipeService.getUserRecipes(userId);
//        return null;
//    }

}
