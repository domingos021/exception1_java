package aplicatition.model.exceptions;

import java.io.Serial;

/**
 * Classe base para todas as exceções de regra de negócio da aplicação.
 *
 * Ela representa erros causados por violações das regras de negócio,
 * como saldo insuficiente, datas inválidas, usuário inexistente, etc.
 *
 * Todas as exceções específicas do domínio da aplicação devem herdar
 * desta classe para manter uma hierarquia organizada e facilitar
 * o tratamento das exceções.
 *
 * Esta classe herda de RuntimeException, tornando-se uma
 * Unchecked Exception (exceção não verificada). Assim, os métodos
 * não são obrigados a declarar "throws" nem a tratar a exceção
 * obrigatoriamente com "try/catch".
 */
public class BusinessException extends RuntimeException {

    /**
     * Identificador da versão da classe durante o processo de serialização.
     *
     * Como RuntimeException implementa Serializable, todas as classes que
     * herdam dela também são serializáveis.
     *
     * O serialVersionUID permite que diferentes versões da mesma classe
     * sejam identificadas durante a serialização e desserialização,
     * evitando problemas de incompatibilidade.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Construtor que recebe a mensagem de erro e a repassa
     * para a classe pai (RuntimeException).
     *
     * @param message mensagem descrevendo o motivo da exceção.
     */
    public BusinessException(String message) {
        super(message);
    }
}

/*
 Hierarquia das exceções personalizadas

 RuntimeException
        ↑
 BusinessException      (Exceção genérica de regra de negócio)
        ↑
 ReservationException   (Exceção específica do módulo de reservas)

 Quando utilizar cada uma?

 ✔ BusinessException

 Utilize quando o erro representar uma regra de negócio genérica,
 comum a toda a aplicação.

 Exemplos:
    throw new BusinessException("Saldo insuficiente.");
    throw new BusinessException("Usuário não encontrado.");

 ✔ ReservationException

 Utilize quando o erro estiver relacionado especificamente
 ao domínio de reservas.

 Exemplos:
    throw new ReservationException("A data de check-out deve ser posterior ao check-in.");
    throw new ReservationException("As datas da reserva devem ser futuras.");
 */