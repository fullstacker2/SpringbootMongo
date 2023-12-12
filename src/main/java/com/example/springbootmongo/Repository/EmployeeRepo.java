package com.example.springbootmongo.Repository;

import com.example.springbootmongo.Model.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepo extends MongoRepository<Employee, ObjectId> {
}
