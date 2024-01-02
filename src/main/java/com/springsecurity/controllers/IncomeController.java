package com.springsecurity.controllers;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class IncomeController {

  @GetMapping("/income")
  public ResponseEntity<?> getIncomes() {
    return ResponseEntity.ok("ALL INCOMES RETRIEVED SUCCESSFULLY");
  }

  @PostMapping("/income")
  public ResponseEntity<?> addIncome() {
    return ResponseEntity.ok("ADDING INCOME.....");
  }

  @PutMapping("/income/{id}")
  public ResponseEntity<?> updateIncome(@PathVariable Long id) {
    return ResponseEntity.ok("INCOME WITH ID: " + id + " UPDATED SUCCESSFULLY");
  }

  @DeleteMapping("/income/{id}")
  public ResponseEntity<?> deleteIcome(@PathVariable Long id) {
    return ResponseEntity.ok("INCOME WITH ID: " + id + " DELETED SUCCESSFULLY");
  }
}
