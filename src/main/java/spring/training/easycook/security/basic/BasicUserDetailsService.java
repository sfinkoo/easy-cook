package spring.training.easycook.security.basic;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.training.easycook.user.entity.User;
import spring.training.easycook.user.repository.UserRepository;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BasicUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new BasicUserDetails(user);
    }
}
