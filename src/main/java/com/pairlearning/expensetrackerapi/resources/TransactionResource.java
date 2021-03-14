package com.pairlearning.expensetrackerapi.resources;

import com.pairlearning.expensetrackerapi.domain.Transaction;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.repositories.TransactionRepositoryI;
import com.pairlearning.expensetrackerapi.service.TransactionServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionResource {

    @Autowired
    TransactionServiceI transactionServiceI;

    @PostMapping()
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId")final Integer categoryId,
                                                      @RequestBody final Map<String,Object> transactionMap){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        final Optional<Double> optAmount = Optional.ofNullable( (Double) transactionMap.get("amount"));
        final Optional<String> optNote = Optional.ofNullable( (String) transactionMap.get("note"));
        final Optional<Long> optTransactionDate = Optional.ofNullable( ((Number) transactionMap.get("transactionDate")).longValue());
        System.out.println(optAmount.toString()+optAmount+optNote+optTransactionDate);
        return optUserId.flatMap(userId->{
            return optAmount.flatMap(amount -> {
                return optNote.flatMap(note-> optTransactionDate.map(trasactionDate->{
                    var transactionResult= transactionServiceI.addTransaction(userId,categoryId,
                            amount,note,trasactionDate);
                     return new ResponseEntity<>(transactionResult, HttpStatus.CREATED);
            }));
            });
        }).orElseThrow(()->new EtBadRequestExeption("requiered fields not found"));
    }
}
