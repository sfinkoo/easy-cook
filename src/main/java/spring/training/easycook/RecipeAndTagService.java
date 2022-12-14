package spring.training.easycook;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.training.easycook.exception.ResourceException;
import spring.training.easycook.tag.ValueTypeForTagSearch;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.recipe.service.RecipeService;
import spring.training.easycook.tag.entity.Tag;
import spring.training.easycook.tag.service.TagService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeAndTagService {

    private final RecipeService recipeService;
    private final TagService tagService;

    public RecipeAndTagService(RecipeService recipeService, TagService tagService) {
        this.recipeService = recipeService;
        this.tagService = tagService;
    }

    public Set<Tag> getTags(Long idRecipe) {
        Recipe recipe = recipeService.findById(idRecipe);
        return recipe.getTags();
    }

    public String getRecipesByTag(ValueTypeForTagSearch type, String value) {
        Tag tag = tagService.findTag(type, value);
        Set<Recipe> recipesByTagSet = recipeService.getAll().stream()
                .filter(recipe -> recipe.getTags().contains(tag))
                .collect(Collectors.toSet());

        if (recipesByTagSet.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "По данному тегу нет ни одного рецепта.");
        }

        StringBuilder recipes = new StringBuilder(String.format("Найдены такие рецепты по тегу %s: " + "\n", tag.getName()));
        for (Recipe recipe : recipesByTagSet) {
            recipes.append(recipe.getId())
                    .append("\n")
                    .append(recipe.getName())
                    .append("\n");
        }
        return recipes.toString();
    }
}
