package spring.training.easycook.api;

import org.springframework.web.bind.annotation.*;
import spring.training.easycook.recipe.service.RecipeAndTagService;
import spring.training.easycook.tag.ValueTypeForTagSearch;
import spring.training.easycook.tag.entity.Tag;

import java.util.Set;

@RestController
@RequestMapping("/recipe")
public class RecipeAndTagController {

    RecipeAndTagService recipeAndTagService;

    public RecipeAndTagController(RecipeAndTagService recipeAndTagService) {
        this.recipeAndTagService = recipeAndTagService;
    }

    @GetMapping("/tags/{idRecipe}")
    public Set<Tag> getTags(@PathVariable Long idRecipe) {
        return recipeAndTagService.getTags(idRecipe);
    }


    @GetMapping("recipes/tags")
    public String getRecipesByTag(@RequestParam("type") ValueTypeForTagSearch type,
                                  @RequestParam("value") String value) {
        return recipeAndTagService.getRecipesByTag(type, value);
    }

}
