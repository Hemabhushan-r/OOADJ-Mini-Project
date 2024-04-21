package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.StringWrapper;
import Service.RequestsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioController {
    @FXML
    private Label usernameField;

    @FXML
    private AnchorPane appPane;

    private Stage primaryStage;

    private PageController pageController;

    @FXML
    public void handleLogOutClick() {
        pageController.setEmail("");
        pageController.setJwtToken("");
        pageController.setUsername("");
        pageController.setRole("");
        pageController.navigateToSignInPage();
    }

    @FXML
    public void handlePortfolioClick() {
        AnchorPane graphPane1 = new AnchorPane();
        graphPane1.setLayoutX(426);
        graphPane1.setLayoutY(33);
        graphPane1.setPrefHeight(350);
        graphPane1.setPrefWidth(512);
        graphPane1.setStyle("-fx-background-color: #004E64;");
        graphPane1.setEffect(new javafx.scene.effect.DropShadow());

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart1 = new LineChart(xAxis, yAxis);
        lineChart1.setTitle("SENSEX");
        lineChart1.setPrefSize(492, 272);
        lineChart1.setLayoutY(4);
        lineChart1.setBlendMode(BlendMode.ADD);
        graphPane1.getChildren().add(lineChart1);
        AnchorPane graphPane2 = new AnchorPane();
        graphPane2.setLayoutX(426);
        graphPane2.setLayoutY(410);
        graphPane2.setPrefHeight(212);
        graphPane2.setPrefWidth(512);
        graphPane2.setStyle("-fx-background-color: #004E64;");
        graphPane2.setEffect(new javafx.scene.effect.DropShadow());
        LineChart<String, Number> lineChart2 = new LineChart(new CategoryAxis(), new NumberAxis());
        lineChart2.setTitle("NIFTY");
        lineChart2.setPrefSize(492, 272);
        lineChart2.setLayoutX(-6.0);
        lineChart2.setBlendMode(BlendMode.ADD);
        lineChart2.setPrefSize(501, 262);
        Button buyButton = new Button("BUY");
        buyButton.setLayoutX(30);
        buyButton.setLayoutY(154);
        buyButton.setPrefWidth(140);
        buyButton.setPrefHeight(40);
        buyButton.setTextFill(Color.WHITE);
        buyButton.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");
        buyButton.setOnMouseClicked((MouseEvent event) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> requestMap = new HashMap<>();
            requestMap.put("ticker", lineChart1.getTitle());
            requestMap.put("email", pageController.getEmail());
            String jsonRequest;
            try {
                jsonRequest = objectMapper.writeValueAsString(requestMap);
                String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/stock/buy-stock",
                        jsonRequest, pageController.getJwtToken());
                handlePortfolioClick();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        Button sellButton = new Button("SELL");
        sellButton.setLayoutX(200);
        sellButton.setLayoutY(154);
        sellButton.setPrefWidth(140);
        sellButton.setPrefHeight(40);
        sellButton.setTextFill(Color.WHITE);
        sellButton.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");
        sellButton.setOnMouseClicked((MouseEvent event) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> requestMap = new HashMap<>();
            requestMap.put("ticker", lineChart1.getTitle());
            requestMap.put("email", pageController.getEmail());
            String jsonRequest;
            try {
                jsonRequest = objectMapper.writeValueAsString(requestMap);
                String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/stock/sell-stock",
                        jsonRequest, pageController.getJwtToken());
                handlePortfolioClick();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        graphPane2.getChildren().add(buyButton);
        graphPane2.getChildren().add(sellButton);
        // graphPane2.getChildren().add(lineChart2);
        AnchorPane summarizeStocksPane = new AnchorPane();
        summarizeStocksPane.setLayoutX(40.0);
        summarizeStocksPane.setLayoutY(33.0);
        summarizeStocksPane.setPrefSize(340.0, 589.0);
        summarizeStocksPane.setStyle("-fx-background-color: #004E64;");
        summarizeStocksPane.setEffect(new javafx.scene.effect.DropShadow());
        if (pageController.getRole() == "ROLE_USER") {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Object> jsonRequestMap = new HashMap<>();
                jsonRequestMap.put("email", pageController.getEmail());
                String jsonRequest = objectMapper.writeValueAsString(jsonRequestMap);
                String jsonResponse = RequestsService.postRequest(
                        "http://localhost:8081/api/v1/stock/get-stocks-associated-with-email", jsonRequest,
                        pageController.getJwtToken());
                HashMap<String, Object> jsonResponseMap = objectMapper.readValue(jsonResponse, HashMap.class);
                System.out.println(jsonResponseMap);
                summarizeStocksPane.getChildren()
                        .add(createScrollPaneForSummarizeStocks(
                                (List<HashMap<String, Object>>) jsonResponseMap.get("results"),
                                lineChart1,
                                yAxis));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (pageController.getRole() == "ROLE_SEBI") {
            graphPane2.getChildren().remove(sellButton);
            buyButton.setText("Verify");

            StringWrapper email = new StringWrapper();
            buyButton.setOnMouseClicked((MouseEvent event) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    HashMap<String, Object> requestMap = new HashMap();
                    requestMap.put("email", email.getString());
                    String jsonRequest = objectMapper.writeValueAsString(requestMap);
                    String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/sebi/verify-pan",
                            jsonRequest, pageController.getJwtToken());
                    handlePortfolioClick();
                } catch (JsonProcessingException e) {
                    // TODO: handle exception
                }
            });
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Object> jsonRequestMap = new HashMap<>();
                jsonRequestMap.put("email", pageController.getEmail());
                String jsonRequest = objectMapper.writeValueAsString(jsonRequestMap);
                String jsonResponse = RequestsService.getRequest(
                        "http://localhost:8081/api/v1/sebi/pending-users",
                        pageController.getJwtToken());
                HashMap<String, Object> jsonResponseMap = objectMapper.readValue(jsonResponse, HashMap.class);
                System.out.println(jsonResponseMap);
                summarizeStocksPane.getChildren()
                        .add(createScrollPane(
                                (List<HashMap<String, Object>>) jsonResponseMap.get("results"), email));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        appPane.getChildren().clear();
        appPane.getChildren().addAll(graphPane1, graphPane2, summarizeStocksPane);
    }

    @FXML
    public void handleOrdersClick() {

    }

    @FXML
    public void handleWatchlistClick() {

    }

    @FXML
    public void handleDiscoverClick() {
        AnchorPane graphPane1 = new AnchorPane();
        graphPane1.setLayoutX(426);
        graphPane1.setLayoutY(33);
        graphPane1.setPrefHeight(350);
        graphPane1.setPrefWidth(512);
        graphPane1.setStyle("-fx-background-color: #004E64;");
        graphPane1.setEffect(new javafx.scene.effect.DropShadow());
        NumberAxis yAxis = new NumberAxis();
        NumberAxis xAxis = new NumberAxis();
        LineChart<String, Number> lineChart1 = new LineChart(xAxis, yAxis);
        lineChart1.setTitle("Stock");
        lineChart1.setPrefSize(492, 290);
        lineChart1.setLayoutY(4);
        lineChart1.setBlendMode(BlendMode.ADD);
        graphPane1.getChildren().add(lineChart1);
        AnchorPane infoPane1 = new AnchorPane();
        infoPane1.setLayoutX(426);
        infoPane1.setLayoutY(410);
        infoPane1.setPrefHeight(212);
        infoPane1.setPrefWidth(512);
        infoPane1.setStyle("-fx-background-color: #004E64;");
        infoPane1.setEffect(new javafx.scene.effect.DropShadow());
        Button buyButton = new Button("BUY");
        buyButton.setLayoutX(175);
        buyButton.setLayoutY(154);
        buyButton.setPrefWidth(140);
        buyButton.setPrefHeight(40);
        buyButton.setTextFill(Color.WHITE);
        buyButton.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");
        buyButton.setOnMouseClicked((MouseEvent event) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String, Object> requestMap = new HashMap<>();
            requestMap.put("ticker", lineChart1.getTitle());
            requestMap.put("email", pageController.getEmail());
            String jsonRequest;
            try {
                jsonRequest = objectMapper.writeValueAsString(requestMap);
                String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/stock/buy-stock",
                        jsonRequest, pageController.getJwtToken());

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        infoPane1.getChildren().add(buyButton);
        AnchorPane searchStocksPane = new AnchorPane();
        searchStocksPane.setLayoutX(40.0);
        searchStocksPane.setLayoutY(33.0);
        searchStocksPane.setPrefSize(340.0, 589.0);
        searchStocksPane.setStyle("-fx-background-color: #004E64;");
        searchStocksPane.setEffect(new javafx.scene.effect.DropShadow());
        TextField searchField = new TextField();
        searchField.setLayoutX(20.0);
        searchField.setLayoutY(21.0);
        searchField.setPrefHeight(47.0);
        searchField.setPrefWidth(225.0);
        searchField.setPromptText("Search Stocks");

        // Create Button
        Button searchButton = new Button("Search");
        searchButton.setLayoutX(265.0);
        searchButton.setLayoutY(30.0);
        searchButton.setStyle("-fx-background-radius: 0.6em; -fx-background-color:  #00A5CF;");
        searchButton.setTextFill(Color.WHITE);
        searchButton.setOnMouseClicked((MouseEvent event) -> {
            String keyword = searchField.getText();
            HashMap<String, Object> data = new HashMap<>();
            data.put("keyword", keyword);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString;
            try {
                jsonString = objectMapper.writeValueAsString(data);
                // System.out.println(jsonString + " " + pageController.getJwtToken());
                String jsonResponse = RequestsService.postRequest("http://localhost:8081/api/v1/stock/search-stock",
                        jsonString, pageController.getJwtToken());
                // System.out.println(jsonResponse);
                HashMap<String, Object> map = objectMapper.readValue(jsonResponse, HashMap.class);
                if (searchStocksPane.getChildren().size() > 2) {
                    searchStocksPane.getChildren().remove(2);
                }
                // System.out.println(jsonResponse);
                searchStocksPane.getChildren()
                        .add(createScrollPane((List<HashMap<String, Object>>) map.get("bestMatches"), lineChart1,
                                yAxis));
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        searchStocksPane.getChildren().addAll(searchField, searchButton);
        appPane.getChildren().clear();
        appPane.getChildren().addAll(graphPane1, infoPane1, searchStocksPane);
    }

    public ScrollPane createScrollPane(List<HashMap<String, Object>> listElems, LineChart lineChart, NumberAxis yAxis) {
        ObservableList<Pane> items = FXCollections.observableArrayList();
        for (int i = 0; i < listElems.size(); i++) {
            Pane pane = createAnchorPaneWithLabel(listElems.get(i), lineChart, yAxis);
            items.add(pane);
        }
        ListView<Pane> listView = new ListView<>();
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell());
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setLayoutX(12.0);
        scrollPane.setLayoutY(135.0);
        scrollPane.setPrefSize(315.0, 430.0);
        scrollPane.setFitToWidth(true);
        return scrollPane;

    }

    public ScrollPane createScrollPane(List<HashMap<String, Object>> listElems, StringWrapper email) {
        ObservableList<Pane> items = FXCollections.observableArrayList();
        for (int i = 0; i < listElems.size(); i++) {
            Pane pane = createAnchorPaneWithLabelForVerification(listElems.get(i), email);
            items.add(pane);
        }
        ListView<Pane> listView = new ListView<>();
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell());
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setLayoutX(12.0);
        scrollPane.setLayoutY(135.0);
        scrollPane.setPrefSize(315.0, 430.0);
        scrollPane.setFitToWidth(true);
        return scrollPane;

    }

    public ScrollPane createScrollPaneForSummarizeStocks(List<HashMap<String, Object>> listElems, LineChart lineChart,
            NumberAxis yAxis) {
        ObservableList<Pane> items = FXCollections.observableArrayList();
        for (int i = 0; i < listElems.size(); i++) {
            Pane pane = createAnchorPaneWithLabelForSummarizeStocks(listElems.get(i), lineChart, yAxis);
            items.add(pane);
        }
        ListView<Pane> listView = new ListView<>();
        listView.setItems(items);
        listView.setCellFactory(param -> new ListCell());
        ScrollPane scrollPane = new ScrollPane(listView);
        scrollPane.setLayoutX(12.0);
        scrollPane.setLayoutY(135.0);
        scrollPane.setPrefSize(315.0, 430.0);
        scrollPane.setFitToWidth(true);
        return scrollPane;

    }

    private Pane createAnchorPaneWithLabel(HashMap<String, Object> data, LineChart lineChart, NumberAxis yAxis) {
        AnchorPane anchorPane = new AnchorPane();
        // anchorPane.setLayoutX(12.0);
        // anchorPane.setLayoutY(95.0);
        anchorPane.setPrefWidth(270.0);
        anchorPane.setPrefHeight(160.0);
        anchorPane.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");
        // {
        // "1. symbol": "INTC",
        // "2. name": "Intel Corp",
        // "3. type": "Equity",
        // "4. region": "United States",
        // "5. marketOpen": "09:30",
        // "6. marketClose": "16:00",
        // "7. timezone": "UTC-04",
        // "8. currency": "USD",
        // "9. matchScore": "1.0000"
        // }
        // Create labels and add them to the AnchorPane
        Label symLabel = createLabelForSearchRes(String.format("SYM: %s", data.get("1. symbol")), 14.0, 14.0);
        Label nameLabel = createLabelForSearchRes(String.format("Name: %s", data.get("2. name")), 14.0, 35.0);
        Label typeLabel = createLabelForSearchRes(String.format("Type: %s", data.get("3. type")), 140.0, 14.0);
        Label regionLabel = createLabelForSearchRes(String.format("Region: %s", data.get("4. region")), 14.0, 56.0);
        Label marketOpenLabel = createLabelForSearchRes(String.format("Market Open: %s", data.get("5. marketOpen")),
                14.0, 77.0);
        Label marketCloseLabel = createLabelForSearchRes(String.format("Market Close: %s", data.get("6. marketClose")),
                14.0, 97.0);
        Label currencyLabel = createLabelForSearchRes(String.format("Currency: %s", data.get("8. currency")), 14.0,
                117.0);
        symLabel.setPrefWidth(100);
        nameLabel.setPrefWidth(260.0);
        regionLabel.setPrefWidth(260);
        marketOpenLabel.setPrefWidth(170);
        marketCloseLabel.setPrefWidth(170);
        currencyLabel.setPrefWidth(150);
        // Add labels to the AnchorPane
        anchorPane.setOnMouseClicked((MouseEvent event) -> {
            HashMap<String, Object> jsonRequestData = new HashMap<>();
            jsonRequestData.put("ticker", data.get("1. symbol"));
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequestString;
            try {
                jsonRequestString = objectMapper.writeValueAsString(jsonRequestData);
                String jsonResponse = RequestsService.postRequest(
                        "http://localhost:8081/api/v1/stock/get-stock-intraday-history", jsonRequestString,
                        pageController.getJwtToken());
                HashMap<String, Object> jsonResponseMap = objectMapper.readValue(jsonResponse, HashMap.class);
                populateStockLineChart(jsonResponseMap, lineChart, yAxis);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        anchorPane.getChildren().addAll(symLabel, nameLabel, typeLabel, regionLabel, marketOpenLabel, marketCloseLabel,
                currencyLabel);

        return anchorPane;
    }

    private Pane createAnchorPaneWithLabelForSummarizeStocks(HashMap<String, Object> data, LineChart lineChart,
            NumberAxis yAxis) {
        AnchorPane anchorPane = new AnchorPane();
        // anchorPane.setLayoutX(12.0);
        // anchorPane.setLayoutY(95.0);
        anchorPane.setPrefWidth(270.0);
        anchorPane.setPrefHeight(60);
        anchorPane.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");
        // {
        // "1. symbol": "INTC",
        // "2. name": "Intel Corp",
        // "3. type": "Equity",
        // "4. region": "United States",
        // "5. marketOpen": "09:30",
        // "6. marketClose": "16:00",
        // "7. timezone": "UTC-04",
        // "8. currency": "USD",
        // "9. matchScore": "1.0000"
        // }
        // Create labels and add them to the AnchorPane
        Label symLabel = createLabelForSearchRes(String.format("SYM: %s", data.get("symbol")), 14.0, 14.0);
        Label quantityLabel = createLabelForSearchRes(String.format("QTY: %s", data.get("quantity")), 90.0, 14.0);
        Label typeLabel = createLabelForSearchRes(String.format("Type: %s", data.get("orderType")), 160.0, 14.0);
        symLabel.setPrefWidth(70);
        typeLabel.setPrefWidth(70);
        quantityLabel.setPrefWidth(70);
        // Add labels to the AnchorPane
        anchorPane.setOnMouseClicked((MouseEvent event) -> {
            HashMap<String, Object> jsonRequestData = new HashMap<>();
            jsonRequestData.put("ticker", data.get("symbol"));
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequestString;
            try {
                jsonRequestString = objectMapper.writeValueAsString(jsonRequestData);
                String jsonResponse = RequestsService.postRequest(
                        "http://localhost:8081/api/v1/stock/get-stock-intraday-history", jsonRequestString,
                        pageController.getJwtToken());
                HashMap<String, Object> jsonResponseMap = objectMapper.readValue(jsonResponse, HashMap.class);
                populateStockLineChart(jsonResponseMap, lineChart, yAxis);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
        anchorPane.getChildren().addAll(symLabel, quantityLabel, typeLabel);

        return anchorPane;
    }

    private Pane createAnchorPaneWithLabelForVerification(HashMap<String, Object> data, StringWrapper email) {
        AnchorPane anchorPane = new AnchorPane();
        // anchorPane.setLayoutX(12.0);
        // anchorPane.setLayoutY(95.0);
        anchorPane.setPrefWidth(270.0);
        anchorPane.setPrefHeight(160.0);
        anchorPane.setStyle("-fx-background-color: #00A5CF; -fx-background-radius: 0.6em;");

        Label usernameLabel = createLabelForSearchRes(String.format("Username: %s", data.get("username")), 14.0, 14.0);
        Label emailLabel = createLabelForSearchRes(String.format("Email: %s", data.get("email")), 14.0, 35.0);
        Label panLabel = createLabelForSearchRes(String.format("PAN: %s", data.get("panNumber")), 14.0, 56.0);
        Label phoneNumLabel = createLabelForSearchRes(String.format("Ph No: %s", data.get("phoneNumber")),
                14.0, 77.0);
        usernameLabel.setPrefWidth(260);
        emailLabel.setPrefWidth(260.0);
        panLabel.setPrefWidth(260);
        phoneNumLabel.setPrefWidth(260);
        anchorPane.setOnMouseClicked((MouseEvent event) -> {
            email.setString((String) data.get("email"));
        });
        // Add labels to the AnchorPane
        anchorPane.getChildren().addAll(usernameLabel, emailLabel, panLabel, phoneNumLabel);

        return anchorPane;
    }

    private Label createLabelForSearchRes(String text, double layoutX, double layoutY) {
        Label label = new Label(text);
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefWidth(80.0);
        label.setPrefHeight(19.0);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 15px;");
        return label;
    }

    private class ListCell extends javafx.scene.control.ListCell<Pane> {
        @Override
        protected void updateItem(Pane item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(item);
            }
        }
    }

    private void populateStockLineChart(HashMap<String, Object> dataMap, LineChart lineChart, NumberAxis yAxis) {
        try {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Open");
            double minVal = Double.MAX_VALUE, maxVal = Double.MIN_VALUE;
            for (int i = 0; i < (Integer) ((List<Object>) dataMap.get("stockunits")).size(); i++) {
                HashMap<String, Object> stockData = (HashMap<String, Object>) ((List<Object>) dataMap.get("stockunits"))
                        .get(i);
                series.getData().add(
                        new XYChart.Data<Number, Number>(i + 1, (Double) stockData.get("open")));
                minVal = Math.min(minVal, (Double) stockData.get("open"));
                maxVal = Math.max(maxVal, (Double) stockData.get("open"));
                // series.getData().add(new XYChart.Data<>(i + 1, i + 1));
                // System.out.println((Double) stockData.get("open"));
            }
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(minVal);
            yAxis.setUpperBound(maxVal);
            lineChart.setTitle((String) ((HashMap<String, Object>) dataMap.get("metadata")).get("symbol"));
            // System.out.println((String) ((HashMap<String, Object>)
            // dataMap.get("metadata")).get("symbol"));

            lineChart.getData().clear();
            lineChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
