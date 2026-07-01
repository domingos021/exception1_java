package aplicatition;

import aplicatition.model.exceptions.ReservationException;
import aplicatition.model.entities.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Program {

    // Formatter estático e imutável (padrão thread-safe de mercado)
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Scanner sc = new Scanner(System.in)) {
            // Lendo a quantidade de registros com segurança
            int hospedes = lerNumeroInteiro(sc, "Quantos registros ?: ");

            for (int i = 1; i <= hospedes; i++) {
                if (hospedes > 1) {
                    System.out.printf("%n--- Registro nº: %d ---%n", i);
                }

                // Leituras usando os métodos auxiliares limpos
                int roomNumber = lerNumeroInteiro(sc, "Número do quarto: ");
                LocalDate checkIn = lerData(sc, "Data do Check-in (dd/MM/yyyy): ");
                LocalDate checkOut = lerData(sc, "Data do Check-out (dd/MM/yyyy): ");

                // Instanciação da reserva
                //o construtor ja trata os erro para que checkout não venha antes do checkin
                Reservation reserva = new Reservation(roomNumber, checkIn, checkOut);
                System.out.println(reserva);

                // FLUXO DE ATUALIZAÇÃO
                System.out.println("\nInsira dados para atualização da reserva:");
                LocalDate updateCheckIn = lerData(sc, "Data do Check-in (dd/MM/yyyy): ");
                LocalDate updateCheckOut = lerData(sc, "Data do Check-out (dd/MM/yyyy): ");

                // Regras de negócio da própria Entidade
                reserva.updateDates(updateCheckIn, updateCheckOut);
                System.out.println(reserva);
            }
        } catch (ReservationException e) {
            // Captura erros de lógica de negócio e estouro de tentativas de data
            System.out.println("Erro na reserva: " + e.getMessage());
        } catch (InputMismatchException e) {
            // Captura especificamente o estouro de tentativas de números inteiros
            System.out.println("Erro de entrada: " + e.getMessage());
        } catch (RuntimeException e) {
            // Captura qualquer outro erro que não mapeamos (ex: NullPointerException, etc.)
            System.out.println("Erro inesperado no sistema.");
            System.out.println("Detalhes: " + e.getMessage());
        }
    }

    // --- MÉTODOS AUXILIARES PARA REUTILIZAÇÃO DE CÓDIGO ---
    /*
     Parâmetros do método:

     Scanner sc      -> recebe o mesmo objeto Scanner criado no main para realizar a leitura
                        da entrada do usuário (não cria um novo Scanner).

     String mensagem -> recebe o texto que será exibido ao usuário antes da leitura.

     Diagrama:

     main()

     Scanner sc ───────────────┐
                               │
                               ▼
     lerNumeroInteiro(Scanner sc, String mensagem)

             usa o MESMO Scanner

              //private static int lerNumeroInteiro(Scanner sc, String mensagem) {
    */

    // METODO GENÉRICO PARA VERIFICAR E VALIDAR NUMEROS INTEIROS, PARA EVITAR ERROS DE ENTRADA DE DADOS
    private static int lerNumeroInteiro(Scanner sc, String mensagem) {
        int tentativas = 0;
        while (tentativas < 2) {
            System.out.print(mensagem);
            // verifica se o valor inserido é um numero inteiro
            if (sc.hasNextInt()) {
                int valor = sc.nextInt(); // input de inserção de dados: se sim
                sc.nextLine(); // Limpa o buffer restante (boa prática após ler número)
                return valor; // o método retorna o valor inserido ja validado
            }

            // senão, exibe o erro e incrementa tentativa dando mais chances para o usuário acertar
            System.out.println("Valor inválido! Digite um número inteiro.");
            sc.nextLine(); // FIX: Limpa TODA a linha inválida do buffer (evita loops se o usuário digitar espaços)
            tentativas++;
        }
        // estourar as tentativas lança uma exceção e manda para o catch
        throw new InputMismatchException("Entrada de número inválida após 2 tentativas.");
    }

    // MÉTODO GENÉRICO PARA VERIFICAR E VALIDAR DATAS, EVITANDO ERROS DE FORMATAÇÃO
    private static LocalDate lerData(Scanner sc, String mensagem) {
        int tentativas = 0;
        while (tentativas < 2) {
            System.out.print(mensagem);
            // Modificado para nextLine().trim() para capturar a linha toda e remover espaços acidentais
            String entrada = sc.nextLine().trim();

            try {
                // Tenta converter o texto para LocalDate usando o formato STRICT definido
                return LocalDate.parse(entrada, DATE_FORMAT); // Se der certo, retorna a data validada
            } catch (DateTimeParseException e) {
                // Se a data for inválida ou mal formatada, captura o erro e dá mais uma chance
                System.out.println("Data inválida! Use o formato dd/MM/yyyy.");
                tentativas++;
            }
        }
        // Se estourar as 2 tentativas, lança a exceção de negócio para o bloco catch principal
        throw new ReservationException("Entrada de data inválida após 2 tentativas.");
    }
}