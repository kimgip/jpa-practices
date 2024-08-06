package ex01.config;

import ex01.domain06.Emaillist;
import ex01.repository.EmaillistRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={PersistenceConfig06.class})
class TestPersistenceConfig06 {
    @Autowired
    private EmaillistRepository emaillistRepository;

    @Test
    @Transactional
    @Rollback(false)
    void testEmaillistRepositorySave() {
        Emaillist emaillist = new Emaillist("둘", "리", "dooly@gmail.com");
        assertNull(emaillist.getId());

        emaillistRepository.save(emaillist);
        assertNotNull(emaillist.getId());
    }
}
