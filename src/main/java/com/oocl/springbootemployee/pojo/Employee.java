package com.oocl.springbootemployee.pojo;

import java.math.BigDecimal;

public class Employee {
    private Integer id;
    private Integer age;
    private String name;
    private Gender gender;
    private BigDecimal salary;

    public Employee(Integer id, Integer age, String name, Gender gender, BigDecimal salary) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
