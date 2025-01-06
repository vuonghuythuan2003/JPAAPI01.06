package ra.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper <T>{
    private HttpStatus status;
    private int code;
    private T data;
}
