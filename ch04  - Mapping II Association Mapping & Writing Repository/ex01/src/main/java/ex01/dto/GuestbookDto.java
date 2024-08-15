package ex01.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@AllArgsConstructor
public class GuestbookDto {
    private Integer id;
    private String name;
    private String contents;
    private Date regDate;
}
