package spring.training.easycook.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserRequest {

    @NotNull
    @NotBlank(message = "Имя пользователя не может состоять из пробелов.")
    @Length(min = 1, max = 50, message = "Имя пользователя может иметь от 1 до 50 символов.")
    private String username;

    @NotNull
    @Length(min = 1, max = 8, message = "Пароль может иметь от 1 до 8 символов.")
    @NotBlank(message = "Пароль не должен содержать пробел.")
    private String password;
}
