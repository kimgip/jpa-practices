package ex01.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={PersistenceConfig01.class})
class TestPersistenceConfig01 {
    @Autowired
    private TransactionManager tm;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @BeforeEach
    public void setUpBeforeEachTest() {
        log.info("[Before Testing] TransactionManager: " + tm);
        log.info("[Before Testing] EntityManagerFactory: " + emf);
    }

    @Test
    void testDummy() {
        log.info("test nothing...");
    }
}
