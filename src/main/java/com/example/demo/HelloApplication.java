package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

/*
*
* @author ahmdIbrahim
* @github 404ahmd
*
* */
public class HelloApplication extends Application {

    private TableView<Employee> tableView = new TableView<>();
    private TextField nameField = new TextField();
    private TextField positionField = new TextField();
    private TextField salaryField = new TextField();

    private Connection connection;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage){
        connectToDatabase();

        //UI LAYOUT
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        //TABLE VIEW
        setupTableView();

        //FORM FOR ADD EMPLOYEE
        HBox form = new HBox(10);
        nameField.setPromptText("Name");
        positionField.setPromptText("Position");
        salaryField.setPromptText("Salary");
        Button addButton = new Button("ADD");
        addButton.setOnAction(e -> addEmployee());
        form.getChildren().addAll(nameField, positionField, salaryField, addButton);

        //DELETE BUTTON
        Button deleteButton = new Button("DELETE");
        deleteButton.setOnAction(e -> deleteSelectedemployee());

        layout.getChildren().addAll(tableView, form, deleteButton);
        /* next */
        primaryStage.setTitle("Employee Management");
        primaryStage.setScene(new Scene(layout, 600, 400));
        primaryStage.show();

        loadEmployees();
    }

    @Override
    public void stop(){
        try{
            if (connection != null){
                connection.close();
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void connectToDatabase(){
        try{
            String url = "jdbc:mysql://localhost:3306/db_employee";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void setupTableView(){
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Employee, String> nameColumn = new TableColumn<>("NAME");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Employee, String> positionColumn = new TableColumn<>("POSITION");
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("SALARY");
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

        tableView.getColumns().addAll(idColumn, nameColumn, positionColumn, salaryColumn);

    }

    private void loadEmployees(){
        tableView.getItems().clear();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            while (resultSet.next()) {
                tableView.getItems().add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getDouble("salary")
                ));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private void addEmployee(){
        String name = nameField.getText();
        String position = positionField.getText();
        String salaryText = salaryField.getText();

        if (name.isEmpty() || position.isEmpty() || salaryText.isEmpty()){
            showAllert("Error", "All fileds is required");
        }

        try{

            double salary = Double.parseDouble(salaryText);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)"
            );

            statement.setString(1, name);
            statement.setString(2, position);
            statement.setDouble(3, salary);
            statement.executeUpdate();

            loadEmployees();
            nameField.clear();
            positionField.clear();
            salaryField.clear();

        } catch (NumberFormatException ex){
            showAllert("Error", "salary Must be a valid number");
        }

        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteSelectedemployee(){
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null){
            showAllert("Error", "No Employee Selected");
            return;
        }

        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
            statement.setInt(1, selectedEmployee.getId());
            statement.executeUpdate();

            loadEmployees();
        }catch (SQLException ex){
            ex.printStackTrace();
        }


    }

    private void showAllert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
     *
     * @author ahmdIbrahim
     * @github 404ahmd
     *
     * */
}