package io.ennate.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Alert {
    @Id
    private String id;

    private String message;
    private String priority;

    @ManyToOne
    private Vehicle vehicle;

    public Alert() {
    }

    public Alert(String message, String priority, Vehicle vehicle) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.priority = priority;
        this.vehicle = vehicle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
