package com.example.superbank.model;

import com.example.superbank.BalanceRepository;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    private BalanceRepository balanceRepository = new BalanceRepository();
    private BankService bankService = new BankService(balanceRepository);

    @Test
    void getBalance() {
        final BigDecimal balance = bankService.getBalance(1l);
        assertEquals(balance, BigDecimal.TEN);
    }
    @Test
    void addMoney() {
        final BigDecimal balance = bankService.addMoney(1l,BigDecimal.ONE);
        assertEquals(balanceRepository.getBalanceForId(1l),BigDecimal.valueOf(11l));
    }
}