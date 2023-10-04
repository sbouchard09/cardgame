package com.sb.cardgame.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Event implements Serializable {

    public static final long serialVersionUID = 3598319023597534863L;

    private String message;
    private LocalDateTime time;
}
