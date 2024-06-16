package com.rosada.crud.controller;

import com.rosada.crud.model.Post;
import com.rosada.crud.model.UserCrud;
import com.rosada.crud.repository.UserRepository;
import com.rosada.crud.service.PostService;
import com.rosada.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            if (post != null) {
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> createPost(@PathVariable Long userId, @RequestBody Post post) {
        try {
            Optional<UserCrud> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                UserCrud user = optionalUser.get();
                post.setUserCrud(user);
                Post createdPost = postService.createPost(post);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(postId, post);
            if (updatedPost != null) {
                return ResponseEntity.ok(updatedPost);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        try {
            boolean deleted = postService.deletePost(userId, postId);
            if (deleted) {
                return ResponseEntity.ok("Post with id " + postId + " deleted successfully from user with id " + userId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete post: " + e.getMessage());
        }
    }
}
