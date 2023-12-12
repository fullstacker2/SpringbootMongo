package com.example.springbootmongo.Repository;

import com.example.springbootmongo.Model.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AdminRepo extends MongoRepository<Admin, ObjectId> {
    @Query("{email: \"?0\"}")
    List<Admin> getAdminByEmail(String email);
}
