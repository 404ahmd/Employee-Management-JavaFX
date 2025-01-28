package com.example.demo;

import javafx.beans.property.*;

public class Employee {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty position;
    private DoubleProperty salary;

    public Employee(int id, String name, String position, Double salary) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
        this.salary = new SimpleDoubleProperty(salary);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPosition() {
        return position.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public double getSalary() {
        return salary.get();
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary.set(salary);
    }
}
