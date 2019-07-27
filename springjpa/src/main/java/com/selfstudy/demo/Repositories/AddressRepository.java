package com.selfstudy.demo.Repositories;

import com.selfstudy.demo.model.Address;
import com.selfstudy.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
