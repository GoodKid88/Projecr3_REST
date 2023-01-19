package com.murashkin.FirstRESTProject.util;

import com.murashkin.FirstRESTProject.dto.SensorDTO;
import com.murashkin.FirstRESTProject.models.Sensor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

//    private final SensorDTO sensorDTO;
//
//    public SensorValidator(SensorDTO sensorDTO) {
//        this.sensorDTO = sensorDTO;
//    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        return;
    }

//    @Override
//    public void validate(Object target, Errors errors) {
//        Sensor sensor = (Sensor) target;
//
//        Sensor sensorFromDB = sensorRepository.findSensorByName(sensor.getName());
//        if(sensorFromDB != null){
//            return false;
//        }
//    }
}
