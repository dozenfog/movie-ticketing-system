package by.issoft.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionDTO {
    private String devMessage;
    private String userMessage;
    private int errorCode;
}
