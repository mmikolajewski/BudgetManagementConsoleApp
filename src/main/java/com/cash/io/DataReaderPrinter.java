package com.cash.io;

import com.cash.model.Transaction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataReaderPrinter {

    private final Scanner scanner = new Scanner(System.in);

    public void close() {
        scanner.close();
    }

    public void printLine(String text) {
        System.out.println(text);
    }

    public int getInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public Transaction readAndCreateTransaction() {
        printLine("Opis transakcji");
        String description = scanner.nextLine();
        printLine("Typ transakcji:");
        String type = getType();
        printLine("Kwota w zł");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        printLine("Data transakcji yyyy-MM-dd");
        String date = scanner.nextLine();
        printLine("Wprowadzono zmiany");

        return new Transaction(type, description, amount, date);

    }

    public Transaction editTransaction() {

        int id = getId();
        printLine("Typ transakcji:");
        String type = getType();
        printLine("Opis transakcji");
        String description = scanner.nextLine();
        printLine("Kwota w zł");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        printLine("Data transakcji yyyy-MM-dd");
        String date = scanner.nextLine();
        printLine("Wprowadzono zmiany");

        return new Transaction(id, type, description, amount, date);

    }

    public String getType() {
        boolean choice = false;
        int number;
        String type = "";

        do {
            try {
                printLine(" 1 - przychód");
                printLine(" 2 - wydatek");
                number = getInt();
                if (number == 1) {
                    type = "przychod";
                    choice = true;
                } else if ( number == 2) {
                    type = "wydatek";
                    choice = true;
                }
            } catch (InputMismatchException e) {
            printLine("Nie podano liczby, spróbój ponownie");
            }
        } while (!choice);
        return type;
    }

    public int getId() {
        printLine("Podaj Id");
        return getInt();
    }

    public void printList(ArrayList<Transaction> transactionsList) {
        for (Transaction transaction : transactionsList) {
            printLine(transaction.toString());
        }
    }
}
