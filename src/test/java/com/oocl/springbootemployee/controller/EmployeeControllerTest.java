package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.pojo.Employee;
import com.oocl.springbootemployee.pojo.Gender;
import com.oocl.springbootemployee.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JacksonTester<List<Employee>> listJson;

    @Autowired
    private JacksonTester<Employee> json;

    @BeforeEach
    void setUp() {
        employeeRepository.getAll().clear();
        employeeRepository.create(new Employee(1, 18, "ryan", Gender.MALE, new BigDecimal(8000)));
        employeeRepository.create(new Employee(2, 18, "mason", Gender.MALE, new BigDecimal(8000)));
        employeeRepository.create(new Employee(3, 18, "kevin", Gender.MALE, new BigDecimal(8000)));
        employeeRepository.create(new Employee(4, 18, "Levin", Gender.MALE, new BigDecimal(8000)));
        employeeRepository.create(new Employee(5, 11, "Tom", Gender.MALE, new BigDecimal(8000)));
        employeeRepository.create(new Employee(6, 19, "Sam", Gender.MALE, new BigDecimal(8000)));
    }


    @Test
    void should_get_all_employees_when_getAll_given_employees() throws Exception {
        List<Employee> expectEmployeeList = employeeRepository.getAll();
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(6)))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(expectEmployeeList);
    }

    @Test
    void should_get_right_employee_when_getById_given_employee_id() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        final Integer employeeId = givenEmployees.get(0).getId();
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee.getId()).isEqualTo(employeeId);
    }

    @Test
    void should_get_right_employees_when_getByGender_given_gender() throws Exception {
        //Given
        List<Employee> expectList = employeeRepository.getByGender(Gender.MALE);
        //When
        //Then
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employees")
                .param("gender", "MALE" ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectList.size())))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(expectList);
    }

    @Test
    void should_create_employee_when_create_given_employee() throws Exception {
        //Given
        Employee employee = new Employee(100, 18, "Amy", Gender.FEMALE, new BigDecimal(8000));
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(employee).getJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void should_update_employee_when_update_given_employee_id_and_employee() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        final Integer employeeId = givenEmployees.get(0).getId();
        Employee employee = new Employee(1, 20, "James", Gender.MALE, new BigDecimal(6000));
        //When
        //Then
        String employeeJson = client.perform(MockMvcRequestBuilders.put("/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.write(employee).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Employee returnedEmployee = json.parseObject(employeeJson);
        assertThat(returnedEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void should_delete_employee_when_delete_given_employee_id() throws Exception {
        //Given
        final List<Employee> givenEmployees = employeeRepository.getAll();
        final Integer employeeId = givenEmployees.get(0).getId();
        //When
        //Then
        client.perform(MockMvcRequestBuilders.delete("/employees/" + employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(employeeRepository.getAll()).hasSize(5);
    }

    @Test
    void should_get_right_employees_when_getByPage_given_page_and_pageSize() throws Exception {
        //Given
        List<Employee> expectList = employeeRepository.getByPage(2, 2);
        //When
        //Then
        String employeesJSONString = client.perform(MockMvcRequestBuilders.get("/employees")
                .param("page", "2")
                .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectList.size())))
                .andReturn().getResponse().getContentAsString();

        List<Employee> employeeList = listJson.parseObject(employeesJSONString);
        assertThat(employeeList)
                .usingRecursiveComparison()
                .isEqualTo(expectList);
    }


}