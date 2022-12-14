package spring.training.easycook.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import spring.training.easycook.user.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRecipeRequest {

    @NotNull
    @NotBlank(message = "Название рецепта не может состоять из пробелов.")
    @Length(min = 1, max = 100, message = "Название рецепта может иметь от 1 до 100 символов.")
    private String name;

    @NotNull
    @NotBlank(message = "Описание с пошаговым приготовлением блюда не может состоять из пробелов.")
    @Length(min = 1, max = 255, message = "Описание с пошаговым приготовлением блюда может иметь от 1 до 255 символов.")
    private String description;

    private LocalDateTime created;

    private Float kcals;

    private Float protein;

    private Float fat;

    private Float carbon;

}
