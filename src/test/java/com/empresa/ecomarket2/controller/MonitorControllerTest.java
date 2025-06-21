package com.empresa.ecomarket2.controller;


import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.service.MonitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonitorController.class)
public class MonitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonitorService monitorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Monitor monitor;

    @BeforeEach
    void setUp() {
        monitor = new Monitor();
        monitor.setId_monitor(1L);
        monitor.setStatus("ERROR");
        monitor.setMessage("NullPointerException");
        monitor.setTimestamp(LocalDateTime.now());

    }

    @Test
    public void testVerErrores() throws Exception {
        List<MonitorDTO> errores = List.of(
                new MonitorDTO(monitor)
        );

        when(monitorService.obtenerErrores()).thenReturn(errores);

        mockMvc.perform(get("/api/v1/health/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ERROR"))
                .andExpect(jsonPath("$[0].message").value("NullPointerException"));
    }


}
