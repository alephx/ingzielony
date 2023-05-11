package pl.apala.ing.transactions;

import pl.apala.ing.transactions.model.Account;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class TransactionsSolver  {

    private final Map<String, Account> accountsMap = new TreeMap<>();

    public Collection<Account> getSolution() {
        return accountsMap.values();  // TreeMap zwroci posortowane wg. klucza czyli numeru konta
    }

    public void processTransaction(String creditAccountIn, String debitAccountIn, BigDecimal amountIn) {
        var creditAccount = accountsMap.computeIfAbsent(creditAccountIn, Account::new);
        var debitAccount = accountsMap.computeIfAbsent(debitAccountIn, Account::new);
        doTransaction(debitAccount, creditAccount, amountIn);
    }

    private void doTransaction(Account from, Account to, BigDecimal amount) {
        from.setDebitCount(from.getDebitCount()+1);
        from.setBalance(from.getBalance().subtract(amount));

        to.setCreditCount(to.getCreditCount()+1);
        to.setBalance(to.getBalance().add(amount));
    }

}
