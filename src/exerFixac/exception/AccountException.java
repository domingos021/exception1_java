package exerFixac.exception;

/**
 * Exception personalizada para representar violações de regras de negócio
 * relacionadas a operações bancárias (ex: saldo insuficiente, limite de
 * saque excedido).
 *
 * Estende RuntimeException (não Exception) de propósito: são erros de
 * regra de negócio detectados em tempo de execução, e não queremos forçar
 * toda a cadeia de chamadas até o main() a declarar "throws AccountException".
 * Ou seja, é uma "unchecked exception" — o compilador não obriga o tratamento,
 * mas o programa pode (e deve) capturá-la onde fizer sentido.
 */
public class AccountException extends RuntimeException {

    // Construtor que recebe apenas a mensagem de erro.
    // Usado nos casos simples, como "Saldo insuficiente" ou
    // "Valor do saque excede o limite permitido".
    public AccountException(String message) {
        super(message);
    }

    // Construtor que recebe a mensagem e a causa original (outra exception).
    // Útil caso essa exception precise "encapsular" um erro de origem
    // diferente (ex: falha de I/O ao registrar a operação), preservando
    // o rastro completo do problema (stack trace da causa original).
    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}


/*
 * FLUXO DE USO DESTA EXCEPTION NO PROJETO:
 *
 * [ProgramAccount (Main)]
 *        │
 *        ├──> Instancia PublicAccount (Polimorfismo / Upcasting)
 *        │
 *        └──> Chama list.getFirst().withdraw(amount)
 *                    │
 *                    ▼
 *          [PublicAccount (withdraw)]
 *                    │
 *                    ├──> Valida regras de negócio
 *                    └──> Se der erro: throw new AccountException("...")
 *                                │
 *                                ▼
 * [ProgramAccount (Catch)] <─────┘ (Captura o erro e exibe a mensagem amigável)
 */