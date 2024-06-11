package com.service.submittask_service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String title;
    private Integer step;
    private Integer status;
    private String detail;
}
