package spring.training.easycook.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.training.easycook.recipe.dto.CreateRecipeRequest;
import spring.training.easycook.recipe.dto.UpdateRecipeRequest;
import spring.training.easycook.recipe.dto.ValueTypeForRecipeSearch;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.recipe.service.RecipeService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public Recipe create(@Validated @RequestBody CreateRecipeRequest source) {
        return recipeService.create(source);
    }

    @DeleteMapping
    public void delete(@RequestParam("type") ValueTypeForRecipeSearch type,
                           @RequestParam("value") String value) {
        recipeService.delete(type, value);
    }

    @GetMapping("/all")
    public List<Recipe> getAll() {
        return recipeService.getAll();
    }

    @GetMapping("/id/{id}")
    public Recipe getById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Recipe getById(@PathVariable String name) {
        return recipeService.findByName(name);
    }

    @GetMapping("/{fromDate}/{toDate}")
    public List<Recipe> getByTimeInterval(@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime fromDate,
                                          @PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime toDate) {
        return recipeService.getByTimeInterval(fromDate, toDate);
    }

    @PutMapping
    public Recipe update(@RequestParam("type") ValueTypeForRecipeSearch type,
                         @RequestParam("value") String value,
                         @Valid @RequestBody UpdateRecipeRequest request) {
        return recipeService.update(type, value, request);
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
