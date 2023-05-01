package com.cash.io;

import com.cash.model.Transaction;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDao {

    private final Connection connection;

    public TransactionDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/household_budget?serverTimezone=UTC", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Transaction transaction) {
        final String sql = String.format("INSERT INTO transaction (type, description, amount, date) VALUES ('%s', '%s', '%f', '%s')",
                transaction.getType(), transaction.getDescription(), transaction.getAmount(), transaction.getDate());

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Transaction> allTransactions() {
        ArrayList<Transaction> allTransactions;
        String sql = "SELECT * FROM transaction";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            allTransactions = collectResultFromSqlAndAddToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allTransactions;
    }

    private static ArrayList<Transaction> collectResultFromSqlAndAddToList(ResultSet resultSet) throws SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");
            String date = resultSet.getString("date");

            Transaction transaction = new Transaction(id, type, description, amount, date);
            transactions.add(transaction);
        }
        return transactions;
    }

    public ArrayList<Transaction> transactionsByType(String SearchType) {
        ArrayList<Transaction> transactionsByType;
        try {
            String sql = "SELECT * FROM transaction WHERE type = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, SearchType);
            ResultSet resultSet = preparedStatement.executeQuery();

            transactionsByType = collectResultFromSqlAndAddToList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionsByType;
    }


    public boolean update(Transaction transaction) {
        final String sql = String.format("""
                        UPDATE
                             transaction
                         SET
                             type = '%s',
                             description = '%s',
                             amount = '%f',
                             date = '%s'
                         WHERE
                             id = '%d'
                             """,
                transaction.getType(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getId());

        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        final String sql = "DELETE FROM transaction WHERE id= " + id;
        try (Statement statement = connection.createStatement()) {
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows != 0;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
