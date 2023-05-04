package pe.edu.cibertec.class3.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    UserRepository userRepository;

    @GetMapping("users")
    public List<UserDto> list() {
        return userRepository.findAll().stream().map(userEntity -> {
            var userDto = new UserDto();
            userDto.setId(userEntity.id);
            userDto.setEmail(userEntity.email);
            userDto.setFirstName(userEntity.firstName);
            return userDto;
        }).collect(Collectors.toList());

        // return userRepository.findAll();
    }

}
