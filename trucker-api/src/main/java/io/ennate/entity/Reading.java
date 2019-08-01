package io.ennate.entity;

import com.mysql.cj.protocol.ColumnDefinition;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Reading {
    @Id
    private String id;

    private String vin;

    private Double latitude;
    private Double longitude;
    private String timestamp;
    private Double fuelVolume;
    private Integer speed;
    private Integer engineHp;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean checkEngineLightOn;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean engineCoolantLow;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean cruiseControlOn;

    private Integer engineRpm;

    @Embedded
    private Tire tires;

    public Reading() {
        this.id = UUID.randomUUID().toString();
    }

    public Reading( String vin, Double latitude, Double longitude, String timestamp, Double fuelVolume, Integer speed, Integer engineHp, Boolean checkEngineLightOn, Boolean engineCoolantLow, Boolean cruiseControlOn, Integer engineRpm, Tire tires) {
        this.id = UUID.randomUUID().toString();;
        this.vin = vin;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.fuelVolume = fuelVolume;
        this.speed = speed;
        this.engineHp = engineHp;
        this.checkEngineLightOn = checkEngineLightOn;
        this.engineCoolantLow = engineCoolantLow;
        this.cruiseControlOn = cruiseControlOn;
        this.engineRpm = engineRpm;
        this.tires = tires;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getEngineHp() {
        return engineHp;
    }

    public void setEngineHp(Integer engineHp) {
        this.engineHp = engineHp;
    }

    public Boolean getCheckEngineLightOn() {
        return checkEngineLightOn;
    }

    public void setCheckEngineLightOn(Boolean checkEngineLight) {
        this.checkEngineLightOn = checkEngineLight;
    }

    public Boolean getEngineCoolantLow() {
        return engineCoolantLow;
    }

    public void setEngineCoolantLow(Boolean engineCoolantLow) {
        this.engineCoolantLow = engineCoolantLow;
    }

    public Boolean getCruiseControlOn() {
        return cruiseControlOn;
    }

    public void setCruiseControlOn(Boolean cruiseControlOn) {
        this.cruiseControlOn = cruiseControlOn;
    }

    public Integer getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(Integer engineRpm) {
        this.engineRpm = engineRpm;
    }

    public Tire getTires() {
        return tires;
    }

    public void setTires(Tire tires) {
        this.tires = tires;
    }
}
