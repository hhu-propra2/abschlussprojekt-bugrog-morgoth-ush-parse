package de.hhu.propra.sharingplatform.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropayAccountService implements IBankAccountService {

    @Autowired
    IPaymentApi api;

    @Override
    public int getAccountBalance(String accountname) {
        return api.getAccountBalance(accountname);
    }

    @Override
    public void transferMoney(int amount, String recipient) {
        api.addMoney(recipient, amount);
    }

    @Override
    public void transferMoney(int amount, String recipient, String sender) {
        //TODO
    }
}