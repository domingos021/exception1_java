package exerFixac.exception;

public class AccountException extends RuntimeException {
    public AccountException(String message) {
        super(message);
    }
}



/*
[ProgramAccount (Main)]
       │
       ├──> Instancia PublicAccount (Polimorfismo / Upcasting)
       │
       └──> Chama list.getFirst().withdraw(amount)
                   │
                   ▼
         [PublicAccount (withdraw)]
                   │
                   ├──> Valida regras de negócio
                   └──> Se der erro: throw new AccountException("...")
                               │
                               ▼
[ProgramAccount (Catch)] <─────┘ (Captura o erro e exibe a mensagem amigável)
 */
