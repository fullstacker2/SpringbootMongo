package com.example.springbootmongo.Service;

import com.example.springbootmongo.Model.Admin;
import com.example.springbootmongo.Model.Employee;
import com.example.springbootmongo.Repository.EmployeeRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    //create employee
    public String createEmployee(Employee emp) {
        Employee savedEmployee = employeeRepo.save(emp);
        return "{" +
                "\"message\":"+"Successfully created employee\",\n"+
                "\"data\":"+savedEmployee+",\n"+
                "}";
    }

    //get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    // update employee
    public String updateEmployee(String id, Employee emp) {

        ObjectId objectId = new ObjectId(id);

        // Use findById to retrieve the entity by its ID
        Optional<Employee> OptionalEmp = employeeRepo.findById(objectId);

        if(OptionalEmp.isEmpty()) {
            throw new RuntimeException("given employee doesn't exist");
        }
        var empRec = OptionalEmp.get();

        if (emp.getName() != null)
            empRec.setName(emp.getName());
        if (emp.getSalary() != 0)
            empRec.setSalary(emp.getSalary());
        Employee savedEmp = employeeRepo.save(empRec);
        return "{" +
                "\"message\":"+"Successfully updated employee\",\n"+
                "\"data\":"+savedEmp+",\n"+
                "}";
    }

    // remove employee
    public String removeEmpById(String id) {

        ObjectId objectId = new ObjectId(id);

        // Use findById to retrieve the entity by its ID
        Optional<Employee> OptionalEmp = employeeRepo.findById(objectId);

        if(OptionalEmp.isEmpty()) {
            throw new RuntimeException("Employee id" + id + "doesn't exist");
        }
        employeeRepo.deleteById(objectId);
        return "{" +
                "\"message\":"+"Successfully deleted employee\",\n"+
                "\"id\":"+ id +",\n"+
                "}";
    }

    //remove all employees
    public void removeAllEmployees() { employeeRepo.deleteAll();}

}