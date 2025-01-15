package nowicki.piotr.spring_boot_docker.mapper;

import nowicki.piotr.spring_boot_docker.dto.UserDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User toUser(UserDto dto){
        var user = new User();
        user.name = dto.name();
        user.email = dto.email();
        user.password = dto.password();
        user.photo_url = dto.photoUrl();
        return user;
    }

    public UserResponseDto toUserResponseDto(User user){
        return new UserResponseDto(user.name, user.email);
    }
}
