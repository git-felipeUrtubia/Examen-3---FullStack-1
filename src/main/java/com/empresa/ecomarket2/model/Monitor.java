package com.empresa.ecomarket2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Monitor {

    private Long id_monitor;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
