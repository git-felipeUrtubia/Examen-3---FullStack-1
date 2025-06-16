package com.empresa.ecomarket2.service;

import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService {

    private List<MonitorDTO> errores = new ArrayList<>();

    public void agregarError(MonitorDTO error) {
        errores.add(error);
    }

    public List<MonitorDTO> obtenerErrores() {
        return errores;
    }
}
