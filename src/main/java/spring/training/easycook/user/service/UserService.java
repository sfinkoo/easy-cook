package spring.training.easycook.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import spring.training.easycook.exception.ResourceException;
import spring.training.easycook.user.dto.CreateUserRequest;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserService(UserRepository userRepository,
                       ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    public User create(CreateUserRequest request) {
        User user = conversionService.convert(request, User.class);
        userRepository.save(user);
        return user;
    }

    public String update(Long id, CreateUserRequest request) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        }
        User user = conversionService.convert(request, User.class);
        user.setId(id);
        userRepository.save(user);
        return String.format("%s, ваши данные успешно обновлены.", user.getUsername());
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceException(HttpStatus.OK, "Список пользователей пока пуст.");
        }
        return users;
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден.");
        }
        return user.get();
    }
}
