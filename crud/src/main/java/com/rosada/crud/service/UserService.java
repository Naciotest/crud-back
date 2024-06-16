package com.rosada.crud.service;

import com.rosada.crud.Mapper.UserMapper;
import com.rosada.crud.dto.UserDTO;
import com.rosada.crud.model.Post;
import com.rosada.crud.model.UserCrud;
import com.rosada.crud.repository.PostRepository;
import com.rosada.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserCrud> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserCrud> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserCrud createUser(UserDTO user) {
        UserCrud newUserCrud = new UserCrud();
        newUserCrud.setName(user.getName());
        newUserCrud.setEmail(user.getEmail());
        newUserCrud.setCountry(user.getCountry());
        newUserCrud.setBirthDate(user.getBirthDate());
        newUserCrud.setState(user.getState());

        return userRepository.save(newUserCrud);
    }

    public void deleteUser (Long id) {
        UserCrud userCrud = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
        userRepository.delete(userCrud);
    }

    public UserCrud updateUser (Long id, UserDTO userDetails) {
        UserCrud userCrud = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.mapperUser(userDetails, userCrud);
        return userRepository.save(userCrud);
    }

    public Page<Post> getPostsByUserId(Long userId, String keyword, Pageable pageable) {
        UserCrud user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));

        // Aplicar filtrado si se proporciona una palabra clave
        if (keyword != null && !keyword.isEmpty()) {
            return postRepository.findByUserCrudAndTitleContainingIgnoreCase(user, keyword, pageable);
        } else {
            return postRepository.findByUserCrud(user, pageable);
        }
    }

//    public List<Post> getAllPostsByUserId(Long userId) {
//        UserCrud user = userRepository.findById(userId)
//                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
//        return postRepository.findByUser(user);
//    }
}
