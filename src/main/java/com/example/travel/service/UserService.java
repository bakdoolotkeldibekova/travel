package com.example.travel.service;

import com.example.travel.dto.AdminDTO;
import com.example.travel.dto.UserChangePasswordDTO;
import com.example.travel.dto.UserDTO;
import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {

    ResponseMessage sendInvitationForAdmin(String email);

    ResponseMessage createSuperAdmin(User user);
    ResponseMessage createUser(UserDTO userDTO);
    ResponseMessage createAdmin(AdminDTO adminDTO);

    ResponseMessage getById(Long id);
    ResponseMessage getByEmail(String email);

    ResponseMessage changeName(String email, String name);
    ResponseMessage changeSurname(String email, String surname);
    ResponseMessage changeRole(String email, Long roleId);

    ResponseMessage changePassword(UserChangePasswordDTO userChangePasswordDTO);
    ResponseMessage sendForgotPassword(String email);
    ResponseMessage changeForgotPassword(String email, String newPassword);

    ResponseMessage blockByEmail(String email);
    ResponseMessage unblockByEmail(String email);

    ResponseMessage deleteByEmail(String email, String principalEmail) throws AccessDeniedException;

    List<User> getAll();
    List<User> getAllByName(String name);
    List<User> getAllBySurname(String surname);
    List<User> getAllByDateCreatedAfter(String after);
    List<User> getAllByDateCreatedBefore(String before);
    List<User> getALlByRoleId(Long roleId);
    List<User> getAllByDeleted(Boolean deleted);
    List<User> getAllByActive(Boolean active);

    Page<User> getByPage(List<User> userList, Pageable pageable);
}