package exerFixac.model.entities;

import exerFixac.exception.AccountException;

public class PubicAccount extends Account {

    private Double withdrawLimit;

    public PubicAccount(Integer number, Double balance, String holderName, Double withdrawLimit) {
        super(number, balance, holderName); // Ajustado para casar com a ordem (Integer, Double, String) de Account
        this.withdrawLimit = withdrawLimit;
    }

    public Double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(Double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    /**
     * Realiza um depósito adicionando o valor ao saldo.
     */
    @Override
    public void deposit(Double amount) {
        balance += amount;
    }

    /**
     * Realiza um saque respeitando o limite permitido
     * e verificando se há saldo suficiente.
     */
    @Override
    public void withdraw(Double amount) {

        // Verifica se o valor ultrapassa o limite de saque.
        if (amount > withdrawLimit) {
            throw new AccountException("Valor do saque excede o limite permitido");
        }

        // Verifica se existe saldo suficiente.
        if (amount > balance) {
            throw new AccountException("Saldo insuficiente");
        }

        // Efetua o saque.
        balance -= amount;
    }
}