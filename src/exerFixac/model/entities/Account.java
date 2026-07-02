package exerFixac.model.entities;

/**
 * Classe abstrata que representa uma conta bancária genérica.
 *
 * Ela reúne os atributos e comportamentos comuns a todas as contas.
 * Como é abstrata, não pode ser instanciada diretamente.
 */
public abstract class Account {

    // Número da conta
    private int number;

    // Nome do titular
    private String holderName;

    // Saldo da conta.
    // É protected para que as subclasses possam acessá-lo diretamente.
    protected double balance;

    /**
     * Construtor padrão.
     */
    public Account() {
    }

    /**
     * Construtor com parâmetros.
     *
     * @param number Número da conta.
     * @param balance Saldo inicial.
     * @param holderName Nome do titular.
     */
    public Account(int number, double balance, String holderName) {
        this.number = number;
        this.balance = balance;
        this.holderName = holderName;
    }

    // ===========================
    // GETTERS E SETTERS
    // ===========================

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    // ===========================
    // MÉTODOS ABSTRATOS
    // ===========================

    /**
     * Realiza um depósito.
     * Cada tipo de conta deverá implementar sua própria lógica.
     *
     * @param amount Valor do depósito.
     */
    public abstract void deposit(double amount);

    /**
     * Realiza um saque.
     * Cada tipo de conta deverá implementar sua própria lógica.
     *
     * @param amount Valor do saque.
     */
    public abstract void withdraw(double amount);
}