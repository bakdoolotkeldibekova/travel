package com.example.travel.service;

import com.example.travel.dto.ModeratorDTO;
import com.example.travel.dto.UserChangePasswordDTO;
import com.example.travel.dto.UserDTO;
import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    ResponseMessage createUser(UserDTO userDTO);
    ResponseMessage createModerator(ModeratorDTO moderatorDTO);

    ResponseMessage getById(Long id);

    ResponseMessage changeName(String email, String name);
    ResponseMessage changeSurname(String email, String surname);
    ResponseMessage changeRole(String email, Long roleId);

    ResponseMessage changePassword(UserChangePasswordDTO userChangePasswordDTO);
    ResponseMessage sendForgotPassword(String email);
    ResponseMessage changeForgotPassword(String email, String newPassword);

    List<User> getAll();
    List<User> getAllByName(String name);
    List<User> getAllBySurname(String surname);
    List<User> getAllByDateCreatedAfter(String after);
    List<User> getAllByDateCreatedBefore(String before);
    List<User> getALlByRoleId(Long roleId);

    Page<User> getByPage(List<User> userList, Pageable pageable);
}