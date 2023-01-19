package com.murashkin.FirstRESTProject.services;

import com.murashkin.FirstRESTProject.models.Sensor;
import com.murashkin.FirstRESTProject.repositories.SensorRepository;
import com.murashkin.FirstRESTProject.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id){
        Optional<Sensor> foundPerson = sensorRepository.findById(id);
        return foundPerson.orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
