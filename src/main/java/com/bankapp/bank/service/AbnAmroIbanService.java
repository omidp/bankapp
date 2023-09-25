package com.bankapp.bank.service;

import com.bankapp.bank.dao.AccountDao;
import com.bankapp.bank.domain.AccountEntity;
import com.bankapp.bank.model.AccountIbanInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AbnAmroIbanService implements IbanService {

    private final AccountDao accountDao;

    /**
     * 2 letter country code.
     * 2 digit check number.
     * 4 characters from the bank's bank code.
     * 10 digit code for the bank account number.
     *
     * @retur iban
     */
    @Override
    public AccountIbanInfo getIban(AccountNoGenerator accountNoGenerator) {
        String accountNo = accountNoGenerator.generate();
        Optional<AccountEntity> account = accountDao.findByAccountNo("%"+accountNo+"%");
        if (account.isPresent()) {
            return getIban(accountNoGenerator);
        }
        String iban = "NL" + "91" + "ABNA" +  accountNo;
        return new AccountIbanInfo(accountNo, iban);
    }


}
