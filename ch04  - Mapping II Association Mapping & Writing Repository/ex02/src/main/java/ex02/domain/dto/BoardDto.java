package ex02.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private Integer hit;
    private String title;
    private String contents;
    private Date regDate;
    private String userName;
}
