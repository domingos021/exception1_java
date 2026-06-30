package aplicatition;

import model.entities.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

// Solução temporária (Apenas para testes iniciais de fluxo de repetição)
public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        // PADRÃO DE MERCADO: Uso do DateTimeFormatter com STRICT para não permitir datas inválidas (substitui o setLenient)
        // Trocando 'yyyy' por 'uuuu' para funcionar perfeitamente com o ResolverStyle.STRICT
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        System.out.println("Quantos registros ?: ");
        int hospedes = sc.nextInt();

        for (int i = 1; i <= hospedes; i++) {

            if (hospedes > 1) {
                System.out.printf("Registro nº: %d%n", i);
            }

            System.out.print("Número do quarto: ");
            int roomNumber = sc.nextInt();

            System.out.print("Data do Check-in (dd/MM/yyyy): ");
            // PADRÃO DE MERCADO: LocalDate.parse substituindo o antigo sdf.parse
            LocalDate checkIn = LocalDate.parse(sc.next(), DATE_FORMAT);

            System.out.print("Data do Check-out (dd/MM/yyyy): ");
            LocalDate checkOut = LocalDate.parse(sc.next(), DATE_FORMAT);

            /*
             Para testar o fluxo repetitivo nesta versão, a instância e a impressão
              da reserva podem ser feitas DENTRO do laço 'for':
           */
            // Ajustado para checkIn e checkOut (com 'I' e 'O' maiúsculos) para corresponder ao seu código atual:
            // Reservation reservation = new Reservation(roomNumber, checkIn, checkOut);
            // System.out.println(reservation);

            int tentativas = 0;

            // Loop de validação: força o usuário a digitar uma data válida com limite de 2 tentativas
            // PADRÃO DE MERCADO: !checkout.isAfter(checkin) mantendo a lógica exata do seu código anterior
            while (!checkOut.isAfter(checkIn) && tentativas < 2) {
                tentativas++;
                System.out.println("Erro na reserva: a data do check-out(saida) precisa ser definida depois do check-in(entrada)");
                System.out.printf("[Tentativa %d de 2] Digite novamente a Data do Check-out (dd/MM/yyyy): ", tentativas);

                // Atualiza a variável de controle para tentar satisfazer a condição do loop
                checkOut = LocalDate.parse(sc.next(), DATE_FORMAT);
            }

            // Verifica se o loop terminou por sucesso ou por estourar o limite de tentativas
            if (!checkOut.isAfter(checkIn)) {
                System.out.println("Número máximo de tentativas esgotado. Operação cancelada.");
            } else {
                // Instanciação segura: garante que o objeto só é criado com datas coerentes
                Reservation reserva = new Reservation(roomNumber, checkIn, checkOut);
                System.out.println(reserva);

                // FLUXO DE ATUALIZAÇÃO DA RESERVA
                System.out.println();
                System.out.println("Insira  dados para atualização  da reserva ");
                System.out.print("Data do Check-in (dd/MM/yyyy): ");
                checkIn = LocalDate.parse(sc.next(), DATE_FORMAT);

                System.out.print("Data do Check-out (dd/MM/yyyy): ");
                checkOut = LocalDate.parse(sc.next(), DATE_FORMAT);


                // Executa a atualização da reserva.
               // Se alguma regra de negócio for violada, o método retorna uma mensagem de erro.
                String error = reserva.updateDates(checkIn, checkOut);

                if (error != null) {
                    System.out.println("Erro na reserva " + error);
                } else {
                    System.out.println(reserva);
                }

            }
        }
        sc.close();
    }


}
