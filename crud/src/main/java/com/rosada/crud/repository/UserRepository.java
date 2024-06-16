package com.rosada.crud.repository;

import com.rosada.crud.model.UserCrud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCrud, Long> {
}
