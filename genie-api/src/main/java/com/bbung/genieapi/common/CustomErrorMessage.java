package com.bbung.genieapi.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class CustomErrorMessage {

    private int statusCode;
    private List<String> messages;

    public CustomErrorMessage(int statusCode, List<String> messages) {
        this.statusCode = statusCode;
        this.messages = messages;
    }

}
