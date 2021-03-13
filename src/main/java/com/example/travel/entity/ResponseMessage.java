package com.example.travel.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ResponseMessage {
    private Integer status;
    private String message;
    private Object body;
}