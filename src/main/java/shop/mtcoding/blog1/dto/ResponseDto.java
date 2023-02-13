package shop.mtcoding.blog1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ResponseDto<T> { // 제네릭 사용 (new 할때 타입 결정)
    private int code; // 1 , -1
    private String msg; // 상세사유
    private T data;
}
