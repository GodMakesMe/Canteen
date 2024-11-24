package iiitd.assignment4;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableRow;
import javafx.collections.ObservableList;
import java.io.*;

import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
	@FXML
	private Label welcomeText;
	private Stage stage;
	@FXML
	private String preSceneFile = "assignment-4.fxml";
	@FXML
	private String prevTitle;
	@FXML
	private void goToAdminMode(ActionEvent event) throws IOException {
		savePreviousScreen(event);
		gotToAnotherScreen(event, "AdminMode.fxml", "Byte Me - Admin Mode");
	}

	@FXML
	private void goToStudentMode(ActionEvent event) throws IOException {
		savePreviousScreen(event);
		gotToAnotherScreen(event, "StudentMode.fxml", "Byte Me - Student Mode");
	}

	@FXML
	private void addFoodItems() {

	}

	void savePreviousScreen(ActionEvent event) throws IOException {
		prevTitle = getstage(event).getTitle();
	}
	Stage getstage(ActionEvent event) {
		if (stage != null) { return stage; }
		return (Stage)((Node)event.getSource()).getScene().getWindow();
	}
	@FXML
	void goToPreviousScreen(ActionEvent event) throws IOException {
		Scene preScene = new Scene(new FXMLLoader(getClass().getResource(preSceneFile)).load());
		if (preScene != null) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(preScene);
			stage.setTitle(prevTitle);
			stage.show();
		} else {
			System.out.println("Previous screen or stage is not set.");
		}
	}


	void gotToAnotherScreen(ActionEvent event, String fxmlFile, String Title) throws IOException {
		stage = getstage(event);
		Scene save = new Scene(new FXMLLoader(getClass().getResource(fxmlFile)).load());
		stage.setScene(save);
		stage.setTitle(Title);
//		savePreviousScreen(save, stage, Title);
		stage.show();
	}
	void gotToAnotherScreen(ActionEvent event, String fxmlFile) throws IOException {
		stage = getstage(event);
		Scene save = new Scene(new FXMLLoader(getClass().getResource(fxmlFile)).load());
		stage.setScene(save);
//		savePreviousScreen(save, stage, prevTitle);
		stage.show();
	}

	@FXML
	private TableView<FoodItem> tableView = new TableView<>(); // Reference to the TableView

	@FXML
	private TableColumn<FoodItem, String> nameColumn = new TableColumn<>();  // Column for food name

	@FXML
	private TableColumn<FoodItem, Integer> priceColumn = new TableColumn<>(); // Column for price

	@FXML
	private TableColumn<FoodItem, String> typeColumn = new TableColumn<>();
	@FXML TableColumn<FoodItem, Integer> itemNoColumn = new TableColumn<>();
	@FXML
	private TableColumn<FoodItem, String> vegColumn = new TableColumn<>();
	@FXML
	private TableColumn<FoodItem, String> descriptionColumn = new TableColumn<>(); // Column for description

	private ObservableList<FoodItem> foodList = FXCollections.observableArrayList();


	@FXML
	private TableView<Map<String, Object>> VIPView = new TableView<>();  // Use the fx:id from Scene Builder

	@FXML
	private TableColumn<Map<String, Object>, String> orderIDColumn = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderedByColumn = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderStatusColumn = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderPriceColumn = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderSpecialRequestsColumn = new TableColumn<>();
	@FXML
	private TableView<Map<String, Object>> NONVIPView = new TableView<>();  // Use the fx:id from Scene Builder

	@FXML
	private TableColumn<Map<String, Object>, String> orderIDColumn1 = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderedByColumn1 = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderStatusColumn1 = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderPriceColumn1 = new TableColumn<>();

	@FXML
	private TableColumn<Map<String, Object>, String> orderSpecialRequestsColumn1 = new TableColumn<>();


	@FXML
	public void initialize() {
		// Bind the TableView columns to FoodItem properties
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfFood"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeDet"));
		vegColumn.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));
		itemNoColumn.setCellValueFactory(new PropertyValueFactory<>("FoodID"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("foodDescription"));
		loadFoodItemsFromFile("ItemData.txt");
		tableView.setItems(foodList);

		orderIDColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderID")).asString());
		orderedByColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderedBy")).asString());
		orderStatusColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderStatus")).asString());
		orderPriceColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderPrice")).asString());
		orderSpecialRequestsColumn.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderSpecialRequests")).asString());
		orderIDColumn1.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderID1")).asString());
		orderedByColumn1.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderedBy1")).asString());
		orderStatusColumn1.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderStatus1")).asString());
		orderPriceColumn1.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderPrice1")).asString());
		orderSpecialRequestsColumn1.setCellValueFactory(cellData ->
				new ReadOnlyObjectWrapper<>(cellData.getValue().get("OrderSpecialRequests1")).asString());
		ObservableList<Map<String, Object>> orders = FXCollections.observableArrayList();
		ObservableList<Map<String, Object>> orders1 = FXCollections.observableArrayList();
		loadOrderItemsFromFile("OrderDataForJavaFX.txt", orders, orders1);
		// Load data from file and add it to the table

		// Set the data to the TableView
	}
	private void loadOrderItemsFromFile(String filename, ObservableList<Map<String, Object>> orders, ObservableList<Map<String, Object>> orders1) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] itemData = line.split(",");
				if (itemData.length == 6) {
					if (itemData[0].equals("VIP")) {
						Map<String, Object> row = new HashMap<>();
						row.put("OrderID", itemData[1]);
						row.put("OrderedBy", itemData[2]);
						row.put("OrderStatus", itemData[3]);
						row.put("OrderPrice", itemData[4]);
						row.put("OrderSpecialRequests", itemData[5]);
						orders.add(row);
					}else{
						Map<String, Object> row = new HashMap<>();
						row.put("OrderID1", itemData[1]);
						row.put("OrderedBy1", itemData[2]);
						row.put("OrderStatus1", itemData[3]);
						row.put("OrderPrice1", itemData[4]);
						row.put("OrderSpecialRequests1", itemData[5]);
						orders1.add(row);
					}
//					FoodItem item = new FoodItem();
//					item.loaderDataFromString(itemData); // Load data into FoodItem
//					System.out.println(item.toString());
//					foodList.add(item); // Add item to the list

				}
			}
			VIPView.setItems(orders);
			NONVIPView.setItems(orders1);
		} catch (IOException e) {
			System.out.println("Error reading from file: " + e.getMessage());
		}
	}

	private void loadFoodItemsFromFile(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] itemData = line.split(",");
				if (itemData.length == 7) { // assuming each line has 7 values corresponding to the FoodItem fields
					FoodItem item = new FoodItem();
					item.loaderDataFromString(itemData); // Load data into FoodItem
					System.out.println(item.toString());
					foodList.add(item); // Add item to the list
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading from file: " + e.getMessage());
		}
	}


	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
}