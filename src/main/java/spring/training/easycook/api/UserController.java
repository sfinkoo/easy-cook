package spring.training.easycook.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring.training.easycook.user.dto.CreateUserRequest;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @RequestBody CreateUserRequest request) {
        return userService.update(id, request);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        if (userService.getAll().isEmpty()) {
            return new ResponseEntity<>("Список пользователей пока пуст.", HttpStatus.OK);
        }
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    /*пользователь:
    зарегистрироваться (просто созадть в бд). Проверки: пользователь уже существует, пароль слишком корткий/длинный,
    аутентификация*,
     */
}
