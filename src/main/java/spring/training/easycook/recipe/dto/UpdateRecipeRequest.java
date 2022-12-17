package spring.training.easycook.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UpdateRecipeRequest {

    @NotNull
    @NotBlank(message = "Название рецепта не может состоять из пробелов.")
    @Length(min = 1, max = 100, message = "Название рецепта может иметь от 1 до 100 символов.")
    private String name;

    @NotNull
    @NotBlank(message = "Описание с пошаговым приготовлением блюда не может состоять из пробелов.")
    @Length(min = 1, max = 255, message = "Описание с пошаговым приготовлением блюда может иметь от 1 до 255 символов.")
    private String descrtiption;

    private Float kcals;
    private Float protein;
    private Float fat;
    private Float carbon;
    private Set<FieldName> updateMask;
}
