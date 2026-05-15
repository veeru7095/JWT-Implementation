package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.demo.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String username);

}
