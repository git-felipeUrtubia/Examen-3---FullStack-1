package com.empresa.ecomarket2.service;


import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.model.Monitor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class MonitorServiceTest {

    @Autowired
    private MonitorService monitorService;

    @Test
    public void testAgregarErrores() {
        Monitor monitor = new Monitor(
                1L,
                "Error",
                "Error de Conexión",
                LocalDateTime.now()
        );

        MonitorDTO error = new MonitorDTO(monitor);
        monitorService.agregarError(error);

        List<MonitorDTO> errores = monitorService.obtenerErrores();

        assertNotNull(errores);
    }

    @Test
    public void testObtenerErrores() {

        Monitor monitor = new Monitor(
                1L,
                "Error",
                "Error de Conexión",
                LocalDateTime.now()
        );

        MonitorDTO error = new MonitorDTO(monitor);
        monitorService.agregarError(error);

        List<MonitorDTO> errores = monitorService.obtenerErrores();

        assertEquals(1, errores.size());
        assertEquals("Error de Conexión", errores.get(0).getMessage());
        assertEquals("Error", errores.get(0).getStatus());
    }
}

