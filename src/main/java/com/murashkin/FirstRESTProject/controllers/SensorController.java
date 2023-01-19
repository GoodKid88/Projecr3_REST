package com.murashkin.FirstRESTProject.controllers;

import com.murashkin.FirstRESTProject.dto.SensorDTO;
import com.murashkin.FirstRESTProject.models.Sensor;
import com.murashkin.FirstRESTProject.services.SensorService;
import com.murashkin.FirstRESTProject.util.SensorErrorResponse;
import com.murashkin.FirstRESTProject.util.SensorNotCreatedException;
import com.murashkin.FirstRESTProject.util.SensorNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id wasn't found!", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
