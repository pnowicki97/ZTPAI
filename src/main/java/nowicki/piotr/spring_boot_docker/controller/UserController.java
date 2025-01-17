package nowicki.piotr.spring_boot_docker.controller;

import jakarta.validation.Valid;
import nowicki.piotr.spring_boot_docker.dto.UserDto;
import nowicki.piotr.spring_boot_docker.dto.UserResponseDto;
import nowicki.piotr.spring_boot_docker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserResponseDto> findAllUsers(){
        return userService.findAllUsers();
    }
    @PostMapping
    public UserResponseDto saveUser(@RequestBody @Valid UserDto dto){
        return userService.saveUser(dto);
    }
    @GetMapping("/{user-id}")
    public UserResponseDto findById(@PathVariable("user-id") String id){
        return userService.findById(id);
    }
    @GetMapping("/name/{user-name}")
    public UserResponseDto findByName(@PathVariable("user-name") String name){
        return userService.findByName(name);
    }
    @DeleteMapping("/{user-id}")
    public void deleteById(@PathVariable("user-id") String id){
        userService.deleteById(id);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
