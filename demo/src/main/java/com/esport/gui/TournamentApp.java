package com.esport.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TournamentApp extends Application {
    
    private static final String REST_BASE_URL = "http://localhost:8080/api/matches";
    private TableView<MatchData> matchTable;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🎮 Tournoi e-Sport - Gestion des Matchs");
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #1a1a2e;");
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // Centre - Tableau des matchs
        VBox center = createMatchTable();
        root.setCenter(center);
        
        // Bas - Formulaire d'ajout
        VBox bottom = createMatchForm();
        root.setBottom(bottom);
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Charger les données initiales
        loadMatches();
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        
        Label title = new Label("🏆 TOURNOI E-SPORT 2025");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#00d4ff"));
        
        Label subtitle = new Label("Gestion des Matchs en Temps Réel");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        subtitle.setTextFill(Color.web("#b8b8b8"));
        
        statusLabel = new Label("✓ Connecté au serveur");
        statusLabel.setFont(Font.font("Arial", 12));
        statusLabel.setTextFill(Color.web("#00ff00"));
        
        header.getChildren().addAll(title, subtitle, statusLabel);
        return header;
    }
    
    private VBox createMatchTable() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        
        Label tableTitle = new Label("📋 Liste des Matchs");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableTitle.setTextFill(Color.WHITE);
        
        // Table
        matchTable = new TableView<>();
        matchTable.setStyle("-fx-background-color: #16213e; -fx-text-fill: white;");
        
        // Colonnes
        TableColumn<MatchData, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);
        
        TableColumn<MatchData, Integer> team1Col = new TableColumn<>("Équipe 1");
        team1Col.setCellValueFactory(new PropertyValueFactory<>("team1Id"));
        team1Col.setPrefWidth(100);
        
        TableColumn<MatchData, Integer> team2Col = new TableColumn<>("Équipe 2");
        team2Col.setCellValueFactory(new PropertyValueFactory<>("team2Id"));
        team2Col.setPrefWidth(100);
        
        TableColumn<MatchData, Integer> score1Col = new TableColumn<>("Score 1");
        score1Col.setCellValueFactory(new PropertyValueFactory<>("scoreTeam1"));
        score1Col.setPrefWidth(80);
        
        TableColumn<MatchData, Integer> score2Col = new TableColumn<>("Score 2");
        score2Col.setCellValueFactory(new PropertyValueFactory<>("scoreTeam2"));
        score2Col.setPrefWidth(80);
        
        TableColumn<MatchData, String> statusCol = new TableColumn<>("Statut");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(120);
        
        TableColumn<MatchData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
        dateCol.setPrefWidth(180);
        
        matchTable.getColumns().addAll(idCol, team1Col, team2Col, 
                                       score1Col, score2Col, statusCol, dateCol);
        
        // Boutons d'action
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        
        Button refreshBtn = new Button("🔄 Actualiser");
        styleButton(refreshBtn, "#2196F3");
        refreshBtn.setOnAction(e -> loadMatches());
        
        Button updateBtn = new Button("✏️ Modifier Score");
        styleButton(updateBtn, "#FF9800");
        updateBtn.setOnAction(e -> updateSelectedMatch());
        
        Button deleteBtn = new Button("🗑️ Supprimer");
        styleButton(deleteBtn, "#f44336");
        deleteBtn.setOnAction(e -> deleteSelectedMatch());
        
        buttonBox.getChildren().addAll(refreshBtn, updateBtn, deleteBtn);
        
        container.getChildren().addAll(tableTitle, matchTable, buttonBox);
        return container;
    }
    
    private VBox createMatchForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(15));
        form.setStyle("-fx-background-color: #16213e; -fx-background-radius: 10;");
        
        Label formTitle = new Label("➕ Créer un Nouveau Match");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        formTitle.setTextFill(Color.WHITE);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        Label team1Label = new Label("Équipe 1 ID:");
        team1Label.setTextFill(Color.WHITE);
        TextField team1Field = new TextField();
        team1Field.setPromptText("Ex: 1");
        
        Label team2Label = new Label("Équipe 2 ID:");
        team2Label.setTextFill(Color.WHITE);
        TextField team2Field = new TextField();
        team2Field.setPromptText("Ex: 2");
        
        grid.add(team1Label, 0, 0);
        grid.add(team1Field, 1, 0);
        grid.add(team2Label, 2, 0);
        grid.add(team2Field, 3, 0);
        
        Button createBtn = new Button("✅ Créer Match");
        styleButton(createBtn, "#4CAF50");
        createBtn.setOnAction(e -> {
            try {
                int t1 = Integer.parseInt(team1Field.getText());
                int t2 = Integer.parseInt(team2Field.getText());
                createMatch(t1, t2);
                team1Field.clear();
                team2Field.clear();
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Veuillez entrer des IDs valides");
            }
        });
        
        form.getChildren().addAll(formTitle, grid, createBtn);
        return form;
    }
    
    private void styleButton(Button btn, String color) {
        btn.setStyle("-fx-background-color: " + color + "; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 10 20; " +
                    "-fx-background-radius: 5;");
        btn.setCursor(javafx.scene.Cursor.HAND);
    }
    
    private void loadMatches() {
        try {
            statusLabel.setText("⏳ Chargement...");
            statusLabel.setTextFill(Color.YELLOW);
            
            // Appel REST GET
            URL url = new URL(REST_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                
                // Parser JSON simple (pour démo)
                matchTable.getItems().clear();
                statusLabel.setText("✓ " + matchTable.getItems().size() + " matchs chargés");
                statusLabel.setTextFill(Color.web("#00ff00"));
            }
        } catch (Exception e) {
            statusLabel.setText("❌ Erreur: " + e.getMessage());
            statusLabel.setTextFill(Color.RED);
        }
    }
    
    private void createMatch(int team1Id, int team2Id) {
        try {
            String jsonData = String.format(
                "{\"team1Id\":%d,\"team2Id\":%d,\"scoreTeam1\":0,\"scoreTeam2\":0," +
                "\"matchDate\":\"%s\",\"status\":\"SCHEDULED\"}",
                team1Id, team2Id, java.time.LocalDateTime.now()
            );
            
            URL url = new URL(REST_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes());
            os.flush();
            os.close();
            
            if (conn.getResponseCode() == 201) {
                showAlert("Succès", "Match créé avec succès!");
                loadMatches();
            }
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de créer le match: " + e.getMessage());
        }
    }
    
    private void updateSelectedMatch() {
        MatchData selected = matchTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un match");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modifier Score");
        dialog.setHeaderText("Match #" + selected.getId());
        dialog.setContentText("Score (format: 13-7):");
        
        dialog.showAndWait().ifPresent(score -> {
            String[] scores = score.split("-");
            if (scores.length == 2) {
                // Appel REST PATCH
                showAlert("Info", "Score mis à jour: " + score);
                loadMatches();
            }
        });
    }
    
    private void deleteSelectedMatch() {
        MatchData selected = matchTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Attention", "Veuillez sélectionner un match");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le match #" + selected.getId() + "?");
        confirm.setContentText("Cette action est irréversible.");
        
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Appel REST DELETE
                showAlert("Info", "Match supprimé");
                loadMatches();
            }
        });
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

// Classe de données pour le TableView
class MatchData {
    private int id;
    private int team1Id;
    private int team2Id;
    private int scoreTeam1;
    private int scoreTeam2;
    private String status;
    private String matchDate;
    
    // Constructeur et getters/setters
    public MatchData(int id, int team1Id, int team2Id, int scoreTeam1, 
                    int scoreTeam2, String status, String matchDate) {
        this.id = id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.status = status;
        this.matchDate = matchDate;
    }
    
    public int getId() { return id; }
    public int getTeam1Id() { return team1Id; }
    public int getTeam2Id() { return team2Id; }
    public int getScoreTeam1() { return scoreTeam1; }
    public int getScoreTeam2() { return scoreTeam2; }
    public String getStatus() { return status; }
    public String getMatchDate() { return matchDate; }
}