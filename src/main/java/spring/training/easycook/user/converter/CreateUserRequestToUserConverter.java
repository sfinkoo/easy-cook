package spring.training.easycook.user.converter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import spring.training.easycook.user.dto.CreateUserRequest;
import spring.training.easycook.user.entity.User;

@Component("userRequestToUser")
public class CreateUserRequestToUserConverter implements Converter<CreateUserRequest, User> {

    @Override
    public User convert(CreateUserRequest source) {
         return User.builder()
                 .username(source.getUsername())
                 .password(source.getPassword())
                 .build();
    }
}
