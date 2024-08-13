package ecommerce_store.ecommerce.service.interfaces;

import ecommerce_store.ecommerce.dto.request.UserAddressRequest;
import ecommerce_store.ecommerce.dto.response.UserAddressResponse;

import java.util.List;

public interface UserAddressService {
    List<UserAddressResponse> findAllUserAddress();
    UserAddressResponse findUserAddressById(Long id);
    UserAddressRequest saveUserAddress(UserAddressRequest userAddressRequest);
    void deleteUserAddress(Long id);
}
