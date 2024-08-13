package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserAddressRequest;
import ecommerce_store.ecommerce.dto.response.UserAddressResponse;
import ecommerce_store.ecommerce.entities.User;
import ecommerce_store.ecommerce.entities.UserAddress;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.UserAddressRepo;
import ecommerce_store.ecommerce.service.interfaces.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepo userAddressRepo;
@Autowired
    public UserAddressServiceImpl(UserAddressRepo userAddressRepo) {
        this.userAddressRepo = userAddressRepo;
    }

    @Override
    public List<UserAddressResponse> findAllUserAddress() {

    return userAddressRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserAddressResponse findUserAddressById(Long id) {
        return userAddressRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("resource not found against this "+id));
    }

    @Override
    public UserAddressRequest saveUserAddress(UserAddressRequest userAddressRequest) {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddress(userAddressRequest.getAddress());
        userAddress.setCity(userAddressRequest.getCity());
        userAddress.setCounty(userAddressRequest.getCounty());
        userAddress.setPostalCode(userAddressRequest.getPostalCode());
        userAddress.setMobile(userAddressRequest.getMobile());
        // Save userAddress to the repository
        userAddressRepo.save(userAddress);
        // Return the saved userAddress as a request DTO
        return toRequest(userAddress);
    }

    @Override
    public void deleteUserAddress(Long id) {
        Optional<UserAddress> userAddressOptional = userAddressRepo.findById(id);
        if (userAddressOptional.isPresent()) {
            userAddressRepo.deleteById(id);
        } else {
            throw new ResourceNotFoundException("User address not found against this ID: " + id);
        }
    }

    private UserAddressResponse toResponse(UserAddress userAddress) {
        return new UserAddressResponse(
                userAddress.getId(),
                userAddress.getUserId(),
                userAddress.getAddress(),
                userAddress.getCity(),
                userAddress.getPostalCode(),
                userAddress.getCounty(),
                userAddress.getMobile()
        );
    }


    private UserAddressRequest toRequest(UserAddress userAddress) {
        return new UserAddressRequest(
                userAddress.getUserId(),
                userAddress.getAddress(),
                userAddress.getCity(),
                userAddress.getPostalCode(),
                userAddress.getCounty(),
                userAddress.getMobile()
        );
    }

}
