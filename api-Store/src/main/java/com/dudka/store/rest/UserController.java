package com.dudka.store.rest;

import com.dudka.store.dto.UserRetrieveDto;
import com.dudka.store.entity.User;
import com.dudka.store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, path = "/getUsers")
    @ResponseBody
    public ResponseEntity<List<UserRetrieveDto>> getUser (){
        List<UserRetrieveDto> userRetrieveDtos = userService.findAll().
                stream()
                .map(UserRetrieveDto::new).collect(Collectors.toList());
        return  ResponseEntity.ok(userRetrieveDtos);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, path = "/saveUser")
    @ResponseBody
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

}
