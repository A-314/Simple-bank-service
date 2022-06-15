package com.example.superbank.model;

import com.example.superbank.BalanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BankService {

    private final BalanceRepository balanceRepository;

    public BigDecimal getBalance(Long accountId) {
        BigDecimal balance = balanceRepository.getBalanceForId(accountId);
        if (balance == null) {
            throw new IllegalArgumentException();
        }
        return balance;
    }

    public BigDecimal addMoney(Long to, BigDecimal amount) {
        BigDecimal currentBalance = balanceRepository.getBalanceForId(to);
        if(currentBalance == null){
            balanceRepository.save(to,amount);
            return amount;
        }else{
            BigDecimal updatedBalance = currentBalance.add(amount);
            balanceRepository.save(to,updatedBalance);
            return updatedBalance;
        }
    }

    public void makeTransfer(TransferBalance transferBalance) {
        final BigDecimal fromBalance = balanceRepository.getBalanceForId(transferBalance.getFrom());
        final BigDecimal toBalance   = balanceRepository.getBalanceForId(transferBalance.getTo());
        if(fromBalance == null|| toBalance == null){throw new IllegalArgumentException("no ids");}
        if(transferBalance.getAmount().compareTo(fromBalance)>0) throw new IllegalArgumentException("no money");

        BigDecimal updatedFromBalance = fromBalance.subtract(transferBalance.getAmount());
        BigDecimal updatedToBalance   = toBalance.add(transferBalance.getAmount());
        balanceRepository.save(transferBalance.getTo(),updatedFromBalance);
    }
}
