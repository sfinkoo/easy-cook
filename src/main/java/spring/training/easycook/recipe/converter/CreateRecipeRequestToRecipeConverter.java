package spring.training.easycook.recipe.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import spring.training.easycook.recipe.dto.CreateRecipeRequest;
import spring.training.easycook.recipe.entity.Recipe;

@Component("recipeRequestToRecipe")
public class CreateRecipeRequestToRecipeConverter implements Converter<CreateRecipeRequest, Recipe> {

    @Override
    public Recipe convert(CreateRecipeRequest source) {
        return Recipe.builder()
                .name(source.getName())
                .description(source.getDescription())
                .carbon(source.getCarbon())
                .fat(source.getFat())
                .kcals(source.getKcals())
                .protein(source.getProtein())
                .build();
    }
}
