package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserAddressRequest;
import ecommerce_store.ecommerce.dto.response.UserAddressResponse;
import ecommerce_store.ecommerce.entities.User;
import ecommerce_store.ecommerce.entities.UserAddress;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.UserAddressRepo;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepo userAddressRepo;
    private final UserRepo userRepo;
@Autowired
    public UserAddressServiceImpl(UserAddressRepo userAddressRepo, UserRepo userRepo) {
        this.userAddressRepo = userAddressRepo;
    this.userRepo = userRepo;
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
        userAddress.setCountry(userAddressRequest.getCountry());
        userAddress.setPostalCode(userAddressRequest.getPostalCode());
        userAddress.setMobile(userAddressRequest.getMobile());
        // Fetch the User entity by userId
        User user = userRepo.findById(userAddressRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userAddressRequest.getUserId()));

        userAddress.setUser(user);
        // Save userAddress to the repository
        userAddressRepo.save(userAddress);
        // Return the saved userAddress as a request DTO
        return toRequest(userAddress);
    }

    @Override
    public UserAddressRequest updateUserAddress(Long id, UserAddressRequest userAddressRequest) {
        // Find the existing UserAddress entity or throw an exception if not found
        UserAddress existingUserAddress = userAddressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Address not found for ID " + id));

        // Update the fields of the existing UserAddress entity
        existingUserAddress.setAddress(userAddressRequest.getAddress());
        existingUserAddress.setCity(userAddressRequest.getCity());
        existingUserAddress.setPostalCode(userAddressRequest.getPostalCode());
        existingUserAddress.setCountry(userAddressRequest.getCountry());
        existingUserAddress.setMobile(userAddressRequest.getMobile());

        // If a User ID is provided, set it on the UserAddress entity
        if (userAddressRequest.getUserId() != null) {
            User user = userRepo.findById(userAddressRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for ID " + userAddressRequest.getUserId()));
            existingUserAddress.setUser(user);
        }

        // Save the updated UserAddress entity
        UserAddress updatedUserAddress = userAddressRepo.save(existingUserAddress);

        // Return the updated UserAddressRequest
        return new UserAddressRequest(
                updatedUserAddress.getUser() != null ? updatedUserAddress.getUser().getId() : null,
                updatedUserAddress.getAddress(),
                updatedUserAddress.getCity(),
                updatedUserAddress.getPostalCode(),
                updatedUserAddress.getCountry(),
                updatedUserAddress.getMobile()
        );
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
                userAddress.getUser().getId(),
                userAddress.getAddress(),
                userAddress.getCity(),
                userAddress.getPostalCode(),
                userAddress.getCountry(),
                userAddress.getMobile()
        );
    }


    private UserAddressRequest toRequest(UserAddress userAddress) {
        return new UserAddressRequest(
                userAddress.getUser().getId(),
                userAddress.getAddress(),
                userAddress.getCity(),
                userAddress.getPostalCode(),
                userAddress.getCountry(),
                userAddress.getMobile()
        );
    }

}
