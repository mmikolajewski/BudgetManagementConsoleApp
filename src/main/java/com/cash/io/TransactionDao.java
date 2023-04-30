package com.cash.io;

import com.cash.model.Transaction;

import java.sql.*;

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

    public void printAllTransactions() {
        String sql = "SELECT * FROM transaction";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");

                String transaction = new Transaction(id, type, description, amount, date).toString();
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void transactionsByType(String SearchType) {
        try {
            String sql = "SELECT * FROM transaction WHERE type = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, SearchType);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");

                String transaction = new Transaction(id, type, description, amount, date).toString();
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
