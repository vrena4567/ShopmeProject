package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // this is a custom JPA query
    public User getUserByEmail(@Param("email") String email);
}
