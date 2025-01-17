package nowicki.piotr.spring_boot_docker.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nowicki.piotr.spring_boot_docker.dto.UserDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.mapper.UserMapper;
import nowicki.piotr.spring_boot_docker.model.User;
import nowicki.piotr.spring_boot_docker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private UserDto toUserDto(User user){
        return new UserDto(user.name, user.password, user.email, user.photo_url);
    }
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto saveUser(UserDto dto) {
        var user = userMapper.toUser(dto);
        var savedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(savedUser);
    }
    public UserResponseDto findById(@PathVariable("user-id") String id){
        return userRepository.findById(id).map(userMapper::toUserResponseDto).orElse(null);
    }
    public UserResponseDto findByName(@PathVariable("user-name") String name){
        return userRepository.findByNameLike(name).map(userMapper::toUserResponseDto).orElse(null);
    }

    public UserResponseDto findByEmail(@PathVariable("user-email") String email){
        return userRepository.findByEmail(email).map(userMapper::toUserResponseDto).orElse(null);
    }
    public void deleteById(@PathVariable("user-id") String id){
        userRepository.deleteById(id);
    }
}
