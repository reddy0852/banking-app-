package com.example.bank;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.Repository.BankRepository;
import com.example.bank.entities.Bank;

@RestController
@CrossOrigin({"http://localhost:4200/"})
public class BankRestController {

    @Autowired
    BankRepository repo;

    @PostMapping("/register")
    public ResponseEntity<Bank> registerUser(@RequestBody Bank bank) {
        Optional<Bank> existingBank = repo.findById(bank.getAccountNo());
        if (existingBank.isPresent()) {
            new ResponseEntity<Bank>(bank, HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(repo.save(bank));
    }

    @GetMapping("/fetchBalance/{account_no}/{ifsc}")
    public ResponseEntity<?> fetchBalance(@PathVariable("account_no") int accountNo,
            @PathVariable("ifsc") int ifsc) {
        Optional<Bank> existingBank = repo.findById(accountNo);
        if (existingBank.isPresent()) {
            Bank bank = existingBank.get();
            if (bank.getIfsc() == ifsc) {
                return ResponseEntity.ok(bank.getAmount());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid IFSC code");
            }
        } else {
            return ((BodyBuilder) ResponseEntity.notFound()).body("Account number not found: " + accountNo);
        }
    }

    @PostMapping("/deposit/{accountNo}/{ifsc}/{amount}")
    public ResponseEntity<?> depositAmount(@PathVariable("account_no") int accountNo,
            @PathVariable("ifsc") int ifsc,
            @PathVariable("amount") int amount) {

        Optional<Bank> existingBankOptional = repo.findById(accountNo);
        if (existingBankOptional.isPresent()) {
            Bank existingBank = existingBankOptional.get();
            if (existingBank.getIfsc() == ifsc) {
                int currentAmount = existingBank.getAmount();
                existingBank.setAmount(currentAmount + amount);
                repo.save(existingBank);
                return ResponseEntity.ok("Deposit successful. New balance: " + existingBank.getAmount());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid IFSC code");
            }
        } else {
            return ((BodyBuilder) ResponseEntity.notFound()).body("Account number not found: " + accountNo);
        }
    }

    @PostMapping("/withdrawal/{accountNo}/{ifsc}/{amount}")
    public ResponseEntity<?> withdrawAmount(@PathVariable("account_no") int accountNo,
                                            @PathVariable("ifsc") int ifsc,
                                            @PathVariable("amount") int amount) {
        Optional<Bank> existingBankOptional = repo.findById(accountNo);
        if (existingBankOptional.isPresent()) {
            Bank existingBank = existingBankOptional.get();
            if (existingBank.getIfsc() == ifsc) {
                int currentAmount = existingBank.getAmount();
                if (currentAmount >= amount) {
                    existingBank.setAmount(currentAmount - amount);
                    repo.save(existingBank);
                    return ResponseEntity.ok("Withdrawal successful. New balance: " + existingBank.getAmount());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid IFSC code");
            }
        } else {
            return ((BodyBuilder) ResponseEntity.notFound()).body("Account number not found: " + accountNo);
        }
    }

    @PostMapping("/chequeDeposit")
    public ResponseEntity<?> chequeDeposit(@RequestBody ChequeDepositRequest request) {
        int fromAccountNo = request.getFromAccountNo();
        int fromIfsc = request.getFromIfsc();
        int toAccountNo = request.getToAccountNo();
        int toIfsc = request.getToIfsc();
        int amount = request.getAmount();

        Optional<Bank> fromAccountOptional = repo.findById(fromAccountNo);
        Optional<Bank> toAccountOptional = repo.findById(toAccountNo);

        if (fromAccountOptional.isPresent() && toAccountOptional.isPresent()) {
            Bank fromAccount = fromAccountOptional.get();
            Bank toAccount = toAccountOptional.get();

            if (fromAccount.getIfsc() == fromIfsc && toAccount.getIfsc() == toIfsc) {
                int fromCurrentAmount = fromAccount.getAmount();
                if (fromCurrentAmount >= amount) {
                    fromAccount.setAmount(fromCurrentAmount - amount);
                    toAccount.setAmount(toAccount.getAmount() + amount);
                    repo.save(fromAccount);
                    repo.save(toAccount);
                    return ResponseEntity.ok("Cheque deposit successful. New balance in From Account: " +
                            fromAccount.getAmount() + ". New balance in To Account: " + toAccount.getAmount());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance in From Account");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid IFSC code");
            }
        } else {
            return ((BodyBuilder) ResponseEntity.notFound()).body("One or both of the account numbers not found");
        }
    }

}
