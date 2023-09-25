package com.bankapp.bank.service;

import com.bankapp.bank.model.AccountIbanInfo;

public interface IbanService {

    AccountIbanInfo getIban(AccountNoGenerator accountNoGenerator);

}
