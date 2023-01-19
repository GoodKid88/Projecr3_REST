package com.murashkin.FirstRESTProject.repositories;

import com.murashkin.FirstRESTProject.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
Sensor findSensorByName(String sensorName);
}
