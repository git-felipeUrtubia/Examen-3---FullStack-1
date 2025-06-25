package com.empresa.ecomarket2.controller;


import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Monitor", description = "Operaciones relacionadas con los errores")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/status")
    @Operation(summary = "Obtener todos los errores", description = "Obtiene una lista de todos los errores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Errores no encontrados")
    })
    public List<MonitorDTO> verErrores() {
        return monitorService.obtenerErrores();
    }

}
