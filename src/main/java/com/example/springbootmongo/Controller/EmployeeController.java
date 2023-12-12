package com.example.springbootmongo.Controller;

import com.example.springbootmongo.Model.Employee;
import com.example.springbootmongo.Service.EmployeeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // add an employee
    @PostMapping("/add")
    public String saveEmployee(@RequestBody Employee emp) {
        return employeeService.createEmployee(emp);
    }

    // get all employees
    @GetMapping("/get-employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // update employee
    @PutMapping("/update")
    public String updateEmployee(@RequestBody Employee emp) {return employeeService.updateEmployee(emp);}

    //delete employee by id
    @PostMapping("/del-employee/{id}")
    public String  removeEmpById(@PathVariable ObjectId id) {
        return employeeService.removeEmpById(id);
    }

    // remove all employees
    @GetMapping("/del-employees")
    public void removeAllEmployees() {
        employeeService.removeAllEmployees();
    }
}