package com.rosada.crud.service;

import com.rosada.crud.model.Post;
import com.rosada.crud.model.UserCrud;
import com.rosada.crud.repository.PostRepository;
import com.rosada.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post post) {
        Post existingPost = postRepository.findById(postId).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            // Aquí podrías actualizar más campos según tus necesidades
            return postRepository.save(existingPost);
        }
        return null;
    }

    public boolean deletePost(Long userId, Long postId) {
        UserCrud userCrud = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));

        boolean postExists = userCrud.getPosts().stream().anyMatch(post -> post.getId().equals(postId));
        if (postExists) {
            userCrud.getPosts().removeIf(post -> post.getId().equals(postId));
            userRepository.save(userCrud); // Guarda los cambios en la base de datos
            return true;
        } else {
            throw new NoSuchElementException("Post not found with id " + postId + " for user with id " + userId);
        }
    }
}
