package ex01.config;

import ex01.domain01_05.Pet;
import ex01.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={PersistenceConfig04.class})
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
