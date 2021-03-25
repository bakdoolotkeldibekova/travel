package com.example.travel.controller;

import com.example.travel.dto.AdminDTO;
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

import java.nio.file.AccessDeniedException;
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

    @PostMapping("/createAdmin")
    public ResponseMessage createAdmin(@RequestBody AdminDTO moderatorDTO){
        return userService.createAdmin(moderatorDTO);
    }

    @PostMapping("/sendInvitationForAdmin/{email}")
    public ResponseMessage sendInvitationForAdmin(@PathVariable String email){
        return userService.sendInvitationForAdmin(email);
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

    @PutMapping("/block/{email}")
    public ResponseMessage blockByEmail(@PathVariable String email){
        return userService.blockByEmail(email);
    }

    @PutMapping("/unblock/{email}")
    public ResponseMessage unblockByEmail(@PathVariable String email){
        return userService.unblockByEmail(email);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseMessage deleteByEmail(@PathVariable String email, Principal principal) throws AccessDeniedException {
        return userService.deleteByEmail(email, principal.getName());
    }

    @GetMapping("/id/{id}")
    public ResponseMessage getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseMessage getByEmail(@PathVariable String email){
        return userService.getByEmail(email);
    }

    @GetMapping("/get")
    public Page<User> getAllByParam(Pageable pageable,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String surname,
                                    @RequestParam(required = false) Long roleId,
                                    @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active,
                                    @RequestParam(value = "deleted", required = false, defaultValue = "false") Boolean deleted,
                                    @ApiParam(value="yyyy-MM-dd HH:mm") @RequestParam(required = false) String dateAfter,
                                    @ApiParam(value="yyyy-MM-dd HH:mm") @RequestParam(required = false) String dateBefore){

        Set<User> fooSet = new LinkedHashSet<>(userService.getAll());

        if (name != null)
            fooSet.retainAll(userService.getAllByName(name));
        if (surname != null)
            fooSet.retainAll(userService.getAllBySurname(surname));
        if (roleId != null)
            fooSet.retainAll(userService.getALlByRoleId(roleId));
        if (deleted != null)
            fooSet.retainAll(userService.getAllByDeleted(deleted));
        if (active != null)
            fooSet.retainAll(userService.getAllByActive(active));
        if (dateAfter != null)
            fooSet.retainAll(userService.getAllByDateCreatedAfter(dateAfter));
        if (dateBefore != null)
            fooSet.retainAll(userService.getAllByDateCreatedBefore(dateBefore));

        List<User> userList = new ArrayList<>(fooSet);
        return userService.getByPage(userList, pageable);
    }

}
