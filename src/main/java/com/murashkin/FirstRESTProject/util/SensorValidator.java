package com.murashkin.FirstRESTProject.util;

import com.murashkin.FirstRESTProject.models.Sensor;
import com.murashkin.FirstRESTProject.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;
    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;

         if (sensorService.findByName(sensor.getName()).isPresent()){
             errors.rejectValue("name", "Sensor is already exist!");
         }
    }
}
