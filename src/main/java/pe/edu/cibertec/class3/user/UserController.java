package pe.edu.cibertec.class3.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    UserRepository userRepository;

    @GetMapping("users")
    public List<User> list() {
        return userRepository.findAll();

    }

}
