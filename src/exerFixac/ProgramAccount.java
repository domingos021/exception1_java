package exerFixac;

import aplicatition.model.exceptions.ReservationException;
import exerFixac.exception.AccountException;
import exerFixac.model.entities.Account;
import exerFixac.model.entities.PubicAccount;

import java.util.*;

public class ProgramAccount {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try (Scanner sc = new Scanner(System.in)) {
            // Cria uma lista capaz de armazenar objetos do tipo Account.
            // Como a lista é do tipo Account, ela também pode armazenar
            // qualquer objeto das subclasses de Account
            // (ex.: PublicAccount, BusinessAccount, SavingsAccount, etc.).
            List<Account> list = new ArrayList<>();

            System.out.println("Insira os dados da conta");
            System.out.println("Digite o número da conta: ");
            int number = sc.nextInt();

            System.out.println("Digite o nome do titular: ");
            sc.nextLine(); // Limpa o buffer do scanner
            String holderName = sc.nextLine();

            System.out.println("Digite o saldo inicial: ");
            double initialBalance = sc.nextDouble();

            System.out.println("Digite o limite de saque: ");
            double withdrawLimit = sc.nextDouble();

            // Adiciona um objeto da classe PublicAccount na lista.
            //
            // Apesar do objeto ser um PublicAccount, ele é armazenado
            // como um Account. Isso é possível graças ao polimorfismo
            // (upcasting automático).
            list.add(new PubicAccount(number, initialBalance, holderName, withdrawLimit));

            //exibe os dados
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

            //SAQUE
            System.out.println("\nDigite o valor do saque: ");
            double amount = sc.nextDouble();
            // Como a lista é do tipo Account, podemos chamar o método withdraw()
            // diretamente, e o Java vai decidir em tempo de execução qual implementação
            // do método deve ser executada (polimorfismo dinâmico).

            list.getFirst().withdraw(amount); // Chama o método withdraw() do objeto na posição 0 da lista
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
            // Captura erros relacionados às regras de negócio da conta
            // (ex.: saldo insuficiente ou limite de saque excedido).
            System.out.println("Erro no saque: " + e.getMessage());

        } catch (InputMismatchException e) {
            // Captura erros de entrada quando o usuário informa
            // um tipo de dado diferente do esperado.
            // Ex.: digitar "abc" quando o programa espera um número.
            System.out.println("Erro de entrada: valor inválido.");

        } catch (RuntimeException e) {
            // Captura qualquer outra exceção não tratada anteriormente.
            System.out.println("Erro inesperado no sistema.");
            System.out.println("Detalhes: " + e.getMessage());
        }

    } // catchs aqui


}

