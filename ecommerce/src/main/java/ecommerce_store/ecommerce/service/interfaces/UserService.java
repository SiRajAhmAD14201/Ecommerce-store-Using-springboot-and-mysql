package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.UserRequest;
import ecommerce_store.ecommerce.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAllUser();
    UserResponse findUserById(Long id);
    UserRequest saveUser(UserRequest userRequest);
    void deleteUser(Long id);
}
