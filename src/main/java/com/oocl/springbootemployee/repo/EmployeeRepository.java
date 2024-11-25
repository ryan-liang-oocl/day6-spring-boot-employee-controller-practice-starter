package com.oocl.springbootemployee.repo;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.pojo.Gender;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(1, 18, "ryan", Gender.MALE, new BigDecimal(8000)));
        employees.add(new Employee(2, 18, "mason", Gender.MALE, new BigDecimal(8000)));
        employees.add(new Employee(3, 18, "kevin", Gender.MALE, new BigDecimal(8000)));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee get(int id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }
}
