package com.empresa.ecomarket2.dto.response;

import com.empresa.ecomarket2.model.Monitor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MonitorDTO {
    private String status;
    private String message;
    private LocalDateTime timestamp;

    public MonitorDTO(Monitor monitor) {
        this.status = monitor.getStatus();
        this.message = monitor.getMessage();
        this.timestamp = monitor.getTimestamp();
    }

}
