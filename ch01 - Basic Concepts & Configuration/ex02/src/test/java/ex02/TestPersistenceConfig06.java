package ex02;

import ex02.domain06_07.Emaillist;
import ex02.repository.EmaillistRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
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
