package model.exceptions;

/**
 * Exceção específica para regras de negócio relacionadas à Reservation.
 *
 * Essa classe representa erros que acontecem exclusivamente no contexto
 * de reservas, como:
 * - data de check-in/check-out inválida
 * - violação de regras da reserva
 *
 * Ela herda BusinessException para manter a hierarquia de erros de negócio
 * do sistema organizada e centralizada.
 */
public class ReservationException extends BusinessException {

    /**
     * Construtor que recebe a mensagem de erro e repassa
     * para a classe pai (BusinessException).
     *
     * @param message mensagem explicando o erro da reserva
     */
    public ReservationException(String message) {
        super(message);
    }
}