package com.techelevator.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.controller.UserController;
import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserRepository;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.JpaParametersParameterAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = TestingDatabaseConfig.class)
//@Configuration
//@EnableAutoConfiguration
//@SpringBootTest
//@ComponentScan("src.main.java.com.techelevator.tenmo.dao")
//@ContextConfiguration
//@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = TestingDatabaseConfig.class)
//@SpringBootTest(classes=AccountRepository.class);

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    //@Autowired
    //private TestEntityManager entityManager;


    @Test
    public void accountRepositoryIsNotNull(){
        assertNotNull(accountRepository);
    }

    /*
    @Test
    public void entityManagerIsNotNull(){
        assertNotNull(entityManager);
    } */


    @Test
    public void findAccountByUsernameTestFindsAccount(){
        User user = new User("tester", "password");

        User newUser = userRepository.save(user);
        int newUserId = newUser.getId();

        Account account = new Account(newUserId, 1000);
        Account newAccount = accountRepository.save(account);
        assertThat(newAccount.getBalance()).isEqualTo(1000);
    }
}
