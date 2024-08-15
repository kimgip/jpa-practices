package ex02.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ex02.domain.Board;
import ex02.repository.guerydsl.QuerydslBoardRepository;

public interface JpaBoardRepository extends JpaRepository<Board, Integer>, QuerydslBoardRepository {
    List<Board> findAllByOrderByRegDateDesc();
}