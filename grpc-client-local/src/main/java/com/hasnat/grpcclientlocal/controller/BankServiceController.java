package com.hasnat.grpcclientlocal.controller;

import com.hasnat.grpcclientlocal.dto.BalanceResponse;
import com.hasnat.grpcclientlocal.service.BankService;
import com.hasnat.proto.bankservice.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BankServiceController {
    @Autowired
    private BankService bankService;

   @GetMapping(value = "client/{accountNumber}")
    public BalanceResponse getBalance(@PathVariable int accountNumber) {
        return bankService.getBalance(accountNumber);
    }
}
