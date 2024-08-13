package ecommerce_store.ecommerce.service.implementation;

import ecommerce_store.ecommerce.dto.request.UserRequest;
import ecommerce_store.ecommerce.dto.response.UserResponse;
import ecommerce_store.ecommerce.entities.User;
import ecommerce_store.ecommerce.exception.ResourceNotFoundException;
import ecommerce_store.ecommerce.repository.UserRepo;
import ecommerce_store.ecommerce.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
   private final UserRepo userRepo;

@Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponse> findAllUser() {
       return userRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(Long id) {
        return userRepo.findById(id).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("User not found against this "+id));
    }

    @Override
    public UserRequest saveUser(UserRequest userRequest) {
        User user=new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return toRequest(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> optionalUser=userRepo.findById(id);
        if (optionalUser.isPresent()){
            userRepo.deleteById(id);
        }else {
           throw new  ResourceNotFoundException("user not found against this "+id);
        }
    }
    private UserResponse toResponse(User user){
    UserResponse userResponse=new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setEmail(user.getEmail());
    userResponse.setUsername(user.getUsername());
    return userResponse;
    }
    private UserRequest toRequest(User user){
    UserRequest userRequest=new UserRequest();
    userRequest.setEmail(user.getEmail());
    userRequest.setPassword(user.getPassword());
    userRequest.setUsername(userRequest.getUsername());
    return userRequest;
    }
}
