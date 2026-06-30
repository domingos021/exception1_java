package model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private Integer roomNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;

    // PADRÃO DE MERCADO: DateTimeFormatter substituindo o antigo SimpleDateFormat
    // Alterado de 'yyyy' para 'uuuu' para alinhar com o padrão estrito adotado no Program
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu");

    public Reservation() {
    }

    public Reservation(Integer roomNumber, LocalDate checkin, LocalDate checkout) {
        this.roomNumber = roomNumber;
        this.checkIn = checkin;
        this.checkOut = checkout;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getCheckin() {
        return checkIn;
    }

    public LocalDate getCheckout() {
        return checkOut;
    }

    // Não existem setters para checkIn e checkOut.
    // As datas só podem ser alteradas através do método
    // updateDates(), garantindo que as regras de negócio
    // sejam respeitadas.

    //duração em dias (vai retornar o tipo long, que é o inteiro mais longo)
    //METODOS
    // Retorna um valor do tipo long (inteiro de 64 bits),
    // utilizado porque a quantidade de dias pode exceder
    // o limite do tipo int em alguns cenários.
    public long duration() {

        // O método ChronoUnit.DAYS.between() calcula a distância direta entre duas datas.
        // Ele ignora completamente relógios e milissegundos, funcionando como um calendário:
        // simplesmente conta quantos dias existem do 'checkin' até o 'checkout'.
        //
        // Isso elimina erros antigos causados por fusos horários ou horário de verão.
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public void updateDates(LocalDate checkin, LocalDate checkout) {
        this.checkIn = checkin;
        this.checkOut = checkout;
    }

    @Override
    public String toString() {
        // PADRÃO DE MERCADO: Usando o método .format(fmt) das próprias instâncias de LocalDate
        return "Dados da Reserva:\n" +
                " - Quarto: " + roomNumber + "\n" +
                " - Check-in: " + checkIn.format(DATE_FORMAT) + "\n" +
                " - Check-out: " + checkOut.format(DATE_FORMAT) + "\n" +
                " - Duração: " + duration() + " noites";
    }
}


//METODOS DE IMPLENTAÇAO DE DATAS ANTIGO SEM USO ATUAL
/*
EXEMPLO
    1. O Jeito Antigo (Pesado e Indireto)
    O Java tinha que converter tudo para a menor unidade de tempo possível (milissegundos)
    para depois tentar descobrir os dias. É como se, para medir a distância entre duas cidades em quilômetros,
     você contasse a distância em milímetros e depois fizesse uma divisão gigante.
=========================================== ANTIGO==================================
public long duration() {

    // O método getTime() transforma cada data em um número gigantesco de milissegundos
    // decorridos desde 01/01/1970 (Era Unix).
    // Subtraindo o check-in do check-out, descobrimos o "tempo bruto" da estadia.
    // Usamos o tipo 'long' porque esse número é grande demais para caber em um 'int'.
    long diff = checkout.getTime() - checkin.getTime();

    // Como o valor de 'diff' está em milissegundos, usamos o TimeUnit para calcular
    // quantas vezes o bloco de milissegundos de 1 dia (86.400.000 ms) cabe dentro desse total.
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
}


    [Check-in]  -> Transforma em Milissegundos -> 1.784.860.800.000 ms
                                                             |
                                                      (Subtração Bruta) -> Diff: 259.200.000 ms -> Dividido por 1 dia -> [ 3 Dias ]
                                                             |
    [Check-out] -> Transforma em Milissegundos -> 1.785.120.000.000 ms
================================================================================


==============================================MODERNO========================================
O Jeito Moderno (Leve e Direto)
O Java esquece as horas e os milissegundos. Ele apenas numera os dias do calendário mundial de forma sequencial.
 A conta vira uma subtração simples de números inteiros pequenos.
 ========================>
 [Check-in]  -> Dia do Calendário (Epoch Day) -> Dia 20.750
                                                     |
                                              (Subtração Simples) -> [ 3 Dias ]
                                                     |
[Check-out] -> Dia do Calendário (Epoch Day) -> Dia 20.753

  public long duration() {

        // O método ChronoUnit.DAYS.between() calcula a distância direta entre duas datas.
        // Ele ignora completamente relógios e milissegundos, funcionando como um calendário:
        // simplesmente conta quantos dias existem do 'checkin' até o 'checkout'.
        //
        // Isso elimina erros antigos causados por fusos horários ou horário de verão.
        return ChronoUnit.DAYS.between(checkin, checkout);
    }
 */