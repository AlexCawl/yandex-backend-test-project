package org.alexcawl.testapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ApplicationError {
    private Integer code;
    private String message;

    @Override
    public String toString() {
        return "{\n" +
                "\t\"code\":" + code + ",\n" +
                "\t\"message\":" + message + '\n' +
                '}';
    }
}
