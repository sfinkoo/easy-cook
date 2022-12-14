package spring.training.easycook.recipe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.training.easycook.exception.ResourceException;
import spring.training.easycook.recipe.dto.CreateRecipeRequest;
import spring.training.easycook.recipe.dto.FieldName;
import spring.training.easycook.recipe.dto.UpdateRecipeRequest;
import spring.training.easycook.recipe.dto.ValueTypeForRecipeSearch;
import spring.training.easycook.recipe.entity.Recipe;
import spring.training.easycook.recipe.repository.RecipeRepository;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static spring.training.easycook.recipe.dto.FieldName.*;

@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final ConversionService conversionService;

    public RecipeService(RecipeRepository recipeRepository, UserService userService, ConversionService conversionService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.conversionService = conversionService;
    }

    public List<Recipe> getAll() {
        if (recipeRepository.findAll().isEmpty()) {
            log.info("Список рецептов пока пуст.");
        }
        return recipeRepository.findAll();
    }

    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Рецепт с таким id не найден.");
        }
        return recipe.get();
    }

    public Recipe findByName(String name) {
        Optional<Recipe> optionalRecipe = recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipe.getName().equals(name))
                .findFirst();
        if (optionalRecipe.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Рецепт с таким name не найден.");
        }
        return optionalRecipe.get();
    }


    public List<Recipe> getByTimeInterval(LocalDateTime fromDate, LocalDateTime toDate) {
        List<Recipe> recipes = recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipe.getCreated().isAfter(fromDate))
                .filter(recipe -> recipe.getCreated().isBefore(toDate))
                .toList();
        if (recipes.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Рецептов за данные промежуток времени не найдено.");
        }
        return recipes;
    }

    public Recipe create(CreateRecipeRequest source) {
        Recipe recipe = conversionService.convert(source, Recipe.class);
        //удалить после введения аутентификации
        User user = new User();
        user.setId((long) -1);
        user.setUsername("fffff");
        user.setPassword("ssss");
        //удалить после введения аутентификации
        recipe.setUser(userService.getById(user.getId()));
        log.info("Рецепт успешно создан.");
        return recipeRepository.save(recipe);
    }

    public void delete(ValueTypeForRecipeSearch type, String value) {
        if (type.equals(ValueTypeForRecipeSearch.ID)) {
            log.info("Рецепт успешно удален по id.");
            recipeRepository.deleteById(Long.parseLong(value));
        } else if (type.equals(ValueTypeForRecipeSearch.NAME)) {
            Long id = findByName(value).getId();
            recipeRepository.deleteById(id);
            log.info("Рецепт успешно удален по name.");
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, String.format("Рецепт с таким %s не найден.", type));
        }
    }

    public Recipe update(ValueTypeForRecipeSearch type, String value, UpdateRecipeRequest request) {
        Recipe recipe = findByTypeValue(type, value);
        Set<FieldName> updateMask = request.getUpdateMask();
        if (updateMask.contains(NAME)) {
            recipe.setName(request.getName());
            log.info("Поле name обновлено.");
        } else if (updateMask.contains(DESCRIPTION)) {
            recipe.setDescription(request.getDescrtiption());
            log.info("Поле description обновлено.");
        } else if (updateMask.contains(KCALS)) {
            recipe.setKcals(recipe.getKcals());
            log.info("Поле kcals обновлено.");
        } else if (updateMask.contains(FAT)) {
            recipe.setFat(recipe.getFat());
            log.info("Поле fat обновлено.");
        } else if (updateMask.contains(CARBON)) {
            recipe.setCarbon(recipe.getCarbon());
            log.info("Поле carbon обновлено.");
        } else if (updateMask.contains(PROTEIN)) {
            recipe.setProtein(recipe.getProtein());
            log.info("Поле protein обновлено.");
        }
        return recipe;
    }

    private Recipe findByTypeValue(ValueTypeForRecipeSearch type, String value) {
        Optional<Recipe> recipe;
        if (type.equals(ValueTypeForRecipeSearch.ID)) {
            recipe = recipeRepository.findById(Long.parseLong(value));
        } else if (type.equals(ValueTypeForRecipeSearch.NAME)) {
            Long id = findByName(value).getId();
            recipe = recipeRepository.findById(id);
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Некорректный тип значения.");
        }

        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new ResourceException(HttpStatus.NOT_FOUND, String.format("Рецепт с таким %s не найден.", type));
        }
    }
}
