package ex02;

import ex02.domain01_05.Pet;
import ex02.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class TestPersistenceConfig04 {
    @Autowired
    private PetRepository petRepository;

    @Test
    @Transactional
    @Rollback(false)
    void testPetRepositorySave() {
        Pet pet = new Pet();
        pet.setId(2);

        petRepository.save(pet);
    }
}
