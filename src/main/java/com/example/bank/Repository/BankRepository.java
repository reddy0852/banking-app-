package com.example.bank.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bank.entities.Bank;

public interface BankRepository extends JpaRepository<Bank,Integer>{

}
