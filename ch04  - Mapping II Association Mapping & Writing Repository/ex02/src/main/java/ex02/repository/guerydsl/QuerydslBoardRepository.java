package ex02.repository.guerydsl;

import ex02.domain.Board;
import ex02.domain.dto.BoardDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerydslBoardRepository {
    public BoardDto findById02(Integer id);

    public List<Board> findAllByOrderByRegDateDesc02();
    public List<BoardDto> findAllByOrderByRegDateDesc03();
    public List<BoardDto> findAllByOrderByRegDateDesc03(Integer page, Integer size);
    public List<BoardDto> findAll02(Pageable pageable);
    public List<BoardDto> findAll02(String keyword, Pageable pageable);

    public Long update(Board board);

    public Long delete(Integer id);
    public Long delete(Integer id, Integer userId);
}
