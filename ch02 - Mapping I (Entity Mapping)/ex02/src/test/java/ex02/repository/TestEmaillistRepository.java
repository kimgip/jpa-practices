package ex02.repository;

import ex02.domain.Emaillist;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
public class TestEmaillistRepository {
    @Autowired
    private EmaillistRepository emaillistRepository;

    @Test
    @Transactional
    void testEmaillistRepositorySave() {
        Emaillist emaillist = new Emaillist("또", "치", "ddochi@gmail.com");
        assertNull(emaillist.getId());

        emaillistRepository.save(emaillist);
        assertNotNull(emaillist.getId());
    }
}