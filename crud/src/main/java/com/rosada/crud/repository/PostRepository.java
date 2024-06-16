package com.rosada.crud.repository;

import com.rosada.crud.model.Post;
import com.rosada.crud.model.UserCrud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
   // List<Post> findByUser(UserCrud user);
    Page<Post> findByUserCrud(UserCrud user, Pageable pageable);
    Page<Post> findByUserCrudAndTitleContainingIgnoreCase(UserCrud user, String keyword, Pageable pageable);
}
