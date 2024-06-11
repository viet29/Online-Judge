package com.service.notification_service.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String title;
    private Integer step;
    private Integer status;
    private String detail;
}