package com.rosada.crud.controller;

import com.rosada.crud.dto.UserDTO;
import com.rosada.crud.model.Post;
import com.rosada.crud.model.UserCrud;
import com.rosada.crud.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserCrud>> getAllUsers(){
        try {
            List<UserCrud> userCrudList = userService.getAllUsers();
            return ResponseEntity.ok(userCrudList);
        } catch (Exception e) {
            //500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCrud> getUserById(@PathVariable Long id){
        Optional<UserCrud> optional = userService.getUserById(id);
        // return optionalUser.map(user -> ResponseEntity.ok(user))
        //                       .orElse(ResponseEntity.notFound().build());
        //ME ACONSEJA REEMPLAZAR EL LAMBDA
        //404
        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    /* Debería manejar la excepción ConstraintViolationException para capturar errores de validación de
    entrada y devolver un 400 Bad Request en caso de que la validación falle. */
    //El uso de <?> indica que el método puede devolver cualquier tipo de objeto en la respuesta.
    //@Valid se usa para los objetos DTO
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO createUserDTO) {
        try {
            UserCrud createdUserCrud = userService.createUser(createUserDTO);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdUserCrud.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (ConstraintViolationException ex) {
            // Captura excepciones de validación de entrada
            return ResponseEntity.badRequest().body("Invalid input: " + ex.getMessage());
        } catch (Exception e) {
            // Captura otras excepciones internas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserCrud> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserCrud updatedUserCrud = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUserCrud);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/{userId}/posts")
//    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Long userId) {
//        try {
//            List<Post> posts = userService.getAllPostsByUserId(userId);
//            return ResponseEntity.ok(posts);
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<Page<Post>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword // Ejemplo de filtro por palabra clave
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> postsPage = userService.getPostsByUserId(userId, keyword, pageable);
            return ResponseEntity.ok(postsPage);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
