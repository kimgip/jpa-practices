package ex01.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={PersistenceConfig03.class})
class TestPersistenceConfig03 {
    @Test
    void testDummy() {
        log.info("test nothing...");
    }
}
