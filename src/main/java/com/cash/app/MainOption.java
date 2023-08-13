package com.cash.app;

import com.cash.exceptions.NoSuchOptionException;

public enum MainOption {

    EXIT(0, "Wyjście z programu"),
    ADD_TRANSACTION(1, "Dodanie transakcji"),
    UPDATE_TRANSACTION(2, "Modyfikacja transakcji"),
    DELETE_TRANSACTIONS(3, "usuwanie transakcji"),
    PRINT_INCOMES(4, "wyświetlanie listy przychodów"),
    PRINT_EXPENSES(5, "wyświetlanie listy wydatków");

    private int value;
    private String description;

    MainOption(int value, String desc) {
        this.value = value;
        this.description = desc;
    }

    @Override
    public String toString() {
        return value + " - " + description;
    }

    static MainOption createFromInt(int option) throws NoSuchOptionException {
        try {
            return MainOption.values()[option];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("Brak opcji o id " + option);
        }
    }
}
