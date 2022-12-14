package spring.training.easycook.api;

import org.springframework.web.bind.annotation.*;
import spring.training.easycook.user.dto.CreateUserRequest;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) {
        return userService.update(id, request);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    /*пользователь:
    зарегистрироваться (просто созадть в бд). Проверки: пользователь уже существует, пароль слишком корткий/длинный,
    аутентификация*,
     */
}
