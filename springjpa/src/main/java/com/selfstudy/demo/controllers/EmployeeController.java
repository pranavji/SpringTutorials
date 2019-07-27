package com.selfstudy.demo.controllers;

import com.google.gson.Gson;
import com.selfstudy.demo.Repositories.AddressRepository;
import com.selfstudy.demo.Repositories.EmployeeRepository;
import com.selfstudy.demo.model.Address;
import com.selfstudy.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(path = "/allemployee" , produces = "application/json")
    public ResponseEntity<?> getAllDetails()
    {

        StringBuilder payLoad = new StringBuilder();
        employeeRepository.findAll().stream().forEach(payLoad::append);

        return new ResponseEntity<String>(payLoad.toString(), HttpStatus.OK);
    }

    @PostMapping(path = "/save" )
    public ResponseEntity<?> saveemployee(@RequestBody Employee employee)
    {


       employeeRepository.save(employee);
       return new ResponseEntity<String>("created user with id"+employee.getId(),HttpStatus.OK);
    }
}
