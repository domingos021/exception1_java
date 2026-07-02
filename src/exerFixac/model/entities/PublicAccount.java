package exerFixac.model.entities;

import exerFixac.exception.AccountException;

public class PublicAccount extends Account {

    private double withdrawLimit;

    public PublicAccount(int number, double balance, String holderName, double withdrawLimit) {
        super(number, balance, holderName);
        this.withdrawLimit = withdrawLimit;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    /**
     * Realiza um depósito adicionando o valor ao saldo.
     */
    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Realiza um saque respeitando o limite permitido
     * e verificando se há saldo suficiente.
     */
    @Override
    public void withdraw(double amount) {

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