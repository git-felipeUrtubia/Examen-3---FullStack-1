package com.empresa.ecomarket2.exception;

import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.dto.response.UsuarioDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @Autowired
    private MonitorService monitorService;

    //MethodArgumentTypeMismatchException -> es un tipo de error que ocurre cuando se ingresa un tipo de dato erroneo
    //Si ocurre una excepción de tipo MethodArgumentTypeMismatchException, ejecutara el metodo de abajo.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> ExceptionMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String error = "Error 400 - Parámetro inválido: " + ex.getMessage();
        Monitor monitor = new Monitor();
        monitor.setStatus("ERROR");
        monitor.setMessage(error);
        monitor.setTimestamp(LocalDateTime.now());
        MonitorDTO monitorDTO = new MonitorDTO(monitor);
        monitorService.agregarError(monitorDTO);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    //"HttpMessageNotReadableException"
    //Ocurre cuando el JSON enviado en el body está mal formado.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> ExceptionHttpMessage(HttpMessageNotReadableException ex) {
        String error = "Error 400 - Json mal formado: " + ex.getMessage();
        Monitor monitor = new Monitor();
        monitor.setStatus("ERROR");
        monitor.setMessage(error);
        monitor.setTimestamp(LocalDateTime.now());
        MonitorDTO monitorDTO = new MonitorDTO(monitor);
        monitorService.agregarError(monitorDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    //Este error ocurre cuando te equivocas al usar algun metodo como GET, POST, PUT, etc.
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> manejarMetodoIncorrecto(HttpRequestMethodNotSupportedException ex) {
        String error = "Error 405 - Método HTTP no permitido: " + ex.getMessage();
        Monitor monitor = new Monitor();
        monitor.setStatus("ERROR");
        monitor.setMessage(error);
        monitor.setTimestamp(LocalDateTime.now());
        MonitorDTO monitorDTO = new MonitorDTO(monitor);
        monitorService.agregarError(monitorDTO);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    //Este error ocurre cuando no ingresas el parametro por ejemplo -> /buscar?nombre=.... <- falta parametro.
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> manejarParametroFaltante(MissingServletRequestParameterException ex) {
        String error = "Error 400 - Falta el parámetro: " + ex.getMessage();
        Monitor monitor = new Monitor();
        monitor.setStatus("ERROR");
        monitor.setMessage(error);
        monitor.setTimestamp(LocalDateTime.now());
        MonitorDTO monitorDTO = new MonitorDTO(monitor);
        monitorService.agregarError(monitorDTO);
        return ResponseEntity.badRequest().body(error);
    }

    //Este error salta cuando hay un error en el Path.
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<String> manejarPathVariableFaltante(MissingPathVariableException ex) {
        String error = "Error - Falta una variable en la ruta: " + ex.getMessage();
        Monitor monitor = new Monitor();
        monitor.setStatus("ERROR");
        monitor.setMessage(error);
        monitor.setTimestamp(LocalDateTime.now());
        MonitorDTO monitorDTO = new MonitorDTO(monitor);
        monitorService.agregarError(monitorDTO);
        return ResponseEntity.status(500).body(error);
    }


}
