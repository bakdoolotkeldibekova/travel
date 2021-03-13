package com.example.travel.controller;

import com.example.travel.dto.ModeratorDTO;
import com.example.travel.dto.UserChangePasswordDTO;
import com.example.travel.dto.UserDTO;
import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.User;
import com.example.travel.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseMessage createUser(@RequestBody UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @PostMapping("/createModerator")
    public ResponseMessage createModerator(@RequestBody ModeratorDTO moderatorDTO){
        return userService.createModerator(moderatorDTO);
    }

    @PutMapping("/name/{name}")
    public ResponseMessage changeName(@PathVariable String name, Principal principal){
        return userService.changeName(principal.getName(), name);
    }

    @PutMapping("/surname/{surname}")
    public ResponseMessage changeSurname(@PathVariable String surname, Principal principal){
        return userService.changeSurname(principal.getName(), surname);
    }

    @PutMapping("/roleId/{roleId}")
    public ResponseMessage changeRole(@PathVariable Long roleId, Principal principal){
        return userService.changeRole(principal.getName(), roleId);
    }

    @PutMapping("/changePassword")
    public ResponseMessage changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO){
        return userService.changePassword(userChangePasswordDTO);
    }

    @PostMapping("/sendForgotPassword/{email}") //на email юзера приходит уникальная ссылка для изменения пароля
    public ResponseMessage sendForgotPassword(@PathVariable String email){
        return userService.sendForgotPassword(email);
    }

    @PutMapping("/changeForgotPassword/{email}/{newPassword}")
    public ResponseMessage changeForgotPassword(@PathVariable String email, @PathVariable String newPassword){
        return userService.changeForgotPassword(email, newPassword);
    }

    @GetMapping("/id/{id}")
    public ResponseMessage getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("/get")
    public Page<User> getAllByParam(Pageable pageable,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String surname,
                                    @RequestParam(required = false) Long roleId,
                                    @ApiParam(value="yyyy-MM-dd HH:mm") @RequestParam(required = false) String dateAfter,
                                    @ApiParam(value="yyyy-MM-dd HH:mm") @RequestParam(required = false) String dateBefore){

        Set<User> fooSet = new LinkedHashSet<>(userService.getAll());

        if (name != null)
            fooSet.retainAll(userService.getAllByName(name));
        if (surname != null)
            fooSet.retainAll(userService.getAllBySurname(surname));
        if (roleId != null)
            fooSet.retainAll(userService.getALlByRoleId(roleId));
        if (dateAfter != null)
            fooSet.retainAll(userService.getAllByDateCreatedAfter(dateAfter));
        if (dateBefore != null)
            fooSet.retainAll(userService.getAllByDateCreatedBefore(dateBefore));

        List<User> userList = new ArrayList<>(fooSet);
        return userService.getByPage(userList, pageable);
    }

}
