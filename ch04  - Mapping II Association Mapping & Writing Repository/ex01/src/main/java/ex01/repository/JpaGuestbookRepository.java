package ex01.repository;

import ex01.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaGuestbookRepository extends JpaRepository<Guestbook, Integer> {
	List<Guestbook> findAllByOrderByRegDateDesc();

	int deleteByIdAndPassword(Integer id, String password);
}