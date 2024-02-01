package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        // running this test Hibernate can initialize the creation of the tables in MySQL after creating the entity pck
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userRenata = new User("renata@gmail.com", "password", "Renata", "Vegh");
        userRenata.addRole(roleAdmin);

        User savedUser = repo.save(userRenata);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userGina = new User("gina@gmail.com", "password", "Gina", "Hollo");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userGina.addRole(roleEditor);
        userGina.addRole(roleAssistant);

        User savedUser = repo.save(userGina);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userRenata = repo.findById(1).get();
        System.out.println(userRenata);
        assertThat(userRenata).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userRenata = repo.findById(1).get();
        userRenata.setEnabled(true);
        userRenata.setEmail("renata@email.com");

        repo.save(userRenata);
    }

    @Test
    public void testUpdateUserRoles(){
        User userGina = repo.findById(2).get();
        Role roleEditor = new Role(3);
        userGina.getRoles().remove(roleEditor);

        Role roleSalesperson = new Role(2);
        userGina.addRole(roleSalesperson);

        repo.save(userGina);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }
}
