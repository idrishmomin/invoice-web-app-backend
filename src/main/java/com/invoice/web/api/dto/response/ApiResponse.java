package com.invoice.web.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String responseMessage ;
    private T data ;
    public ApiResponse(String responseMessage) {
        this.responseMessage = responseMessage;
        this.data = null;
    }
}
