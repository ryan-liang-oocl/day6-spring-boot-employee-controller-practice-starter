package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.repo.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    void should_get_all_employees_when_call_getAllEmployees_given_employees() throws Exception {
        List<Employee> expectEmployeeList = employeeRepository.getAll();
        client.perform(MockMvcRequestBuilders.get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(expectEmployeeList.get(0).getId()))
                .andExpect(jsonPath("$[0].age").value(expectEmployeeList.get(0).getAge()))
                .andExpect(jsonPath("$[0].name").value(expectEmployeeList.get(0).getName()));
    }

    @Test
    void should_get_right_employee_when_call_getEmployee_given_employee_id() throws Exception {
        List<Employee> expectEmployeeList = employeeRepository.getAll();
        client.perform(MockMvcRequestBuilders.get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectEmployeeList.get(0).getId()))
                .andExpect(jsonPath("$.age").value(expectEmployeeList.get(0).getAge()))
                .andExpect(jsonPath("$.name").value(expectEmployeeList.get(0).getName()));
    }
}