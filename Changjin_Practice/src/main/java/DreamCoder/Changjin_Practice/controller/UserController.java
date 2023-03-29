package DreamCoder.Changjin_Practice.controller;

import DreamCoder.Changjin_Practice.domain.User;
import DreamCoder.Changjin_Practice.dto.UserRequestDto;
import DreamCoder.Changjin_Practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/new")
    public ResponseEntity create(UserRequestDto userRequestDto){
        User user = new User();
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        try{
            userService.join(user);
        }
        catch(IllegalStateException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body(userService.findOne(user.getUserId()));
    }
    @GetMapping("/find")
    public ResponseEntity read(@RequestParam("userId") Integer userId){
        User findUser;
        try{
            findUser = userService.findOne(userId);
        }
        catch(IllegalStateException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body(findUser);
    }
    @PostMapping("/update")
    public ResponseEntity update(Integer userId, UserRequestDto userRequestDto){
        User findUser;
        User updateUser = new User();
        updateUser.setUserName(userRequestDto.getUserName());
        updateUser.setEmail(userRequestDto.getEmail());
        updateUser.setPassword(userRequestDto.getPassword());
        try{
            findUser = userService.findOne(userId);
        }
        catch(IllegalStateException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body(userService.updateOne(userId, updateUser));
    }
    @PostMapping("/delete")
    public ResponseEntity delete(Integer userId){
        User findUser;
        try{
            findUser = userService.findOne(userId);
        }
        catch(IllegalStateException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body(userService.deleteOne(userId));
    }
}
