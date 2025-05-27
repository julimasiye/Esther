package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Account;
import model.Bank;
import model.TransactionLogger;

public class BankingApp extends Application {
    private Bank bank = new Bank();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Banking System");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextField accNumberField = new TextField();
        accNumberField.setPromptText("Account Number");

        TextField holderNameField = new TextField();
        holderNameField.setPromptText("Account Holder");

        Button createBtn = new Button("Create Account");
        Label msgLabel = new Label();

        createBtn.setOnAction(e -> {
            String accNo = accNumberField.getText();
            String name = holderNameField.getText();
            if (!accNo.isEmpty() && !name.isEmpty()) {
                bank.createAccount(accNo, name);
                TransactionLogger.log("Account created: " + accNo);
                msgLabel.setText("Account Created!");
            } else {
                msgLabel.setText("Fill in all fields.");
            }
        });

        TextField depositField = new TextField();
        depositField.setPromptText("Deposit Amount");
        Button depositBtn = new Button("Deposit");

        depositBtn.setOnAction(e -> {
            try {
                Account acc = bank.getAccount(accNumberField.getText());
                double amount = Double.parseDouble(depositField.getText());
                acc.deposit(amount);
                TransactionLogger.log("Deposited " + amount + " to " + acc.getAccountNumber());
                msgLabel.setText("Deposited!");
            } catch (Exception ex) {
                msgLabel.setText("Deposit Failed");
            }
        });

        TextField withdrawField = new TextField();
        withdrawField.setPromptText("Withdraw Amount");
        Button withdrawBtn = new Button("Withdraw");

        withdrawBtn.setOnAction(e -> {
            try {
                Account acc = bank.getAccount(accNumberField.getText());
                double amount = Double.parseDouble(withdrawField.getText());
                acc.withdraw(amount);
                TransactionLogger.log("Withdrew " + amount + " from " + acc.getAccountNumber());
                msgLabel.setText("Withdrawn!");
            } catch (Exception ex) {
                msgLabel.setText("Withdraw Failed: " + ex.getMessage());
            }
        });

        TextField targetField = new TextField();
        targetField.setPromptText("Target Account");
        TextField transferAmount = new TextField();
        transferAmount.setPromptText("Amount");
        Button transferBtn = new Button("Transfer");

        transferBtn.setOnAction(e -> {
            try {
                bank.transfer(accNumberField.getText(), targetField.getText(), Double.parseDouble(transferAmount.getText()));
                TransactionLogger.log("Transferred " + transferAmount.getText() + " from " + accNumberField.getText() + " to " + targetField.getText());
                msgLabel.setText("Transfer Successful");
            } catch (Exception ex) {
                msgLabel.setText("Transfer Failed: " + ex.getMessage());
            }
        });

        Button checkBtn = new Button("Check Balance");
        checkBtn.setOnAction(e -> {
            Account acc = bank.getAccount(accNumberField.getText());
            if (acc != null) {
                msgLabel.setText("Balance: MWK" + acc.getBalance());
            } else {
                msgLabel.setText("Account not found.");
            }
        });

        root.getChildren().addAll(
            new Label("Create Account"), accNumberField, holderNameField, createBtn,
            new Separator(),
            depositField, depositBtn,
            withdrawField, withdrawBtn,
            new Separator(),
            new Label("Transfer Money"), targetField, transferAmount, transferBtn,
            new Separator(),
            checkBtn, msgLabel
        );

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
