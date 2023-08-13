package com.cash.app;

import com.cash.exceptions.NoSuchOptionException;
import com.cash.io.DataReaderPrinter;
import com.cash.io.TransactionDao;
import com.cash.model.Transaction;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class HouseHoldControl {
    private final static String INCOME = "przychod";
    private final static String EXPENSE = "wydatek";

    private final TransactionDao transactionDao = new TransactionDao();

    private final DataReaderPrinter dataReaderPrinter = new DataReaderPrinter();

    void controlLoop() {
        MainOption mainOption;

        do {
            printOptions();
            mainOption = getOption();
            switch (mainOption) {
                case EXIT -> {
                    exit();
                }
                case ADD_TRANSACTION -> {
                    addTransaction();
                }
                case UPDATE_TRANSACTION -> {
                    updateTransaction();
                }
                case DELETE_TRANSACTIONS -> {
                    deleteTransaction();
                }
                case PRINT_INCOMES -> {
                    printIncomes();
                }
                case PRINT_EXPENSES -> {
                    printExpenses();
                }
                default -> dataReaderPrinter.printLine("Nie ma takiej opcji wprowadź ponownie");
            }
        } while (mainOption != MainOption.EXIT);
    }

    private void printExpenses() {
        dataReaderPrinter.printList(transactionDao.transactionsByType(EXPENSE));
    }

    private void printIncomes() {
        dataReaderPrinter.printList(transactionDao.transactionsByType(INCOME));

    }

    private void deleteTransaction() {
        dataReaderPrinter.printList(transactionDao.allTransactions());
        int id = dataReaderPrinter.getId();
        transactionDao.delete(id);
    }

    private void addTransaction() {
        Transaction transaction = dataReaderPrinter.readAndCreateTransaction();
        transactionDao.add(transaction);
    }

    private void updateTransaction() {
        dataReaderPrinter.printList(transactionDao.allTransactions());
        Transaction transaction = dataReaderPrinter.editTransaction();
        transactionDao.update(transaction);
    }

    private void exit() {
        dataReaderPrinter.close();
        transactionDao.close();
        dataReaderPrinter.printLine("Bajo Bajo");
    }

    private MainOption getOption() {
        boolean optionOk = false;
        MainOption mainOption = null;
        while (!optionOk) {
            try {
                mainOption = MainOption.createFromInt(dataReaderPrinter.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                dataReaderPrinter.printLine(e.getMessage() + ", podaj ponownie:");
            } catch (InputMismatchException e) {
                dataReaderPrinter.printLine("Nie podano liczby, spróbój ponownie");
            }
        }
        return mainOption;
    }

    private void printOptions() {
        dataReaderPrinter.printLine("Wybierz opcję: ");
        for (MainOption value : MainOption.values()) {
            dataReaderPrinter.printLine(value.toString());
        }
    }

}
