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


    public List<Employee> getAll() {
        return employees;
    }

    public Employee get(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getByGender(Gender gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .toList();
    }

    public Employee create(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee update(int id, Employee employee) {
        Employee targetEmployee = employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        if (targetEmployee != null) {
            targetEmployee.setAge(employee.getAge());
            targetEmployee.setName(employee.getName());
            targetEmployee.setGender(employee.getGender());
            targetEmployee.setSalary(employee.getSalary());
        }

        return targetEmployee;
    }

    public void delete(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        return employees.subList((page - 1) * pageSize, page * pageSize);
    }
}
