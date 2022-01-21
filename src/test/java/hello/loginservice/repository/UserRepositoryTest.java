package hello.loginservice.repository;

import hello.loginservice.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired private EntityManager em;
    @Autowired private UserRepository userRepository;


    @Test
    void findByEmail() {
        User user = User.makeUser("kkkk@", "1111");
        em.persist(user);

        User findUser = userRepository.findByEmail("kkkk@");
        Assertions.assertThat(user).isEqualTo(findUser);

    }
}