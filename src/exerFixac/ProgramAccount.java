package exerFixac;

import exerFixac.exception.AccountException;
import exerFixac.model.entities.Account;
import exerFixac.model.entities.PublicAccount;
import exerFixac.utils.Leitor;

import java.util.*;

public class ProgramAccount {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Scanner sc = new Scanner(System.in)) {
            List<Account> list = new ArrayList<>();

            System.out.println("Insira os dados da conta");
            // Agora chamando a classe utilitária Leitor
            int number = Leitor.lerNumeroInteiro(sc, "Número da conta: ");
            String holderName = Leitor.lerTexto(sc, "Nome do titular: ");
            double initialBalance = Leitor.lerNumeroDouble(sc, "Digite o saldo inicial: ");
            double withdrawLimit = Leitor.lerNumeroDouble(sc, "Digite o limite de saque: ");

            // Adiciona na lista usando Polimorfismo
            list.add(new PublicAccount(number, initialBalance, holderName, withdrawLimit));

            // Exibe os dados iniciais
            for (Account account : list) {
                System.out.printf("""
                                
                                Dados da conta:
                                Número da conta: %d
                                Nome do titular: %s
                                Saldo atual: %.2f
                                
                                """,
                        account.getNumber(),
                        account.getHolderName(),
                        account.getBalance()
                );
            }

            // SAQUE
            double amount = Leitor.lerNumeroDouble(sc, "Digite o valor do saque: ");

            // Executa o saque utilizando Polimorfismo Dinâmico
            list.getFirst().withdraw(amount);

            // Exibe os dados atualizados
            for (Account account : list) {
                System.out.printf("""
                                
                                Dados da conta com saldo atual:
                                Número da conta: %d
                                Nome do titular: %s
                                Novo saldo: %.2f
                                
                                """,
                        account.getNumber(),
                        account.getHolderName(),
                        account.getBalance()
                );
            }

        } catch (AccountException e) {
            System.out.println("Erro no saque: " + e.getMessage());
        } catch (Leitor.LeituraInvalidaException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro inesperado no sistema.");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }
}


/*
exerFixac/
└── src/
    └── exerFixac/
        ├── ProgramAccount.java       <-- Contém o método main (Ponto de entrada)
        │
        ├── model/
        │   └── entities/            <-- REGRAS DE NEGÓCIO (O "Mundo Real")
        │       ├── Account.java
        │       └── PubicAccount.java  <-- Renomear para PublicAccount.java
        │
        ├── exception/                <-- TRATAMENTO DE ERROS DO NEGÓCIO
        │   └── AccountException.java
        │
        └── utils/                    <-- FERRAMENTAS DE SUPORTE (Sua Biblioteca)
            └── Leitor.java
 */