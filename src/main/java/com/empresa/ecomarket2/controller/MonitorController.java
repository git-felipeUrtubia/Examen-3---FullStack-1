package com.empresa.ecomarket2.controller;


import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/health")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/status")
    public List<MonitorDTO> verErrores() {
        return monitorService.obtenerErrores();
    }


}
