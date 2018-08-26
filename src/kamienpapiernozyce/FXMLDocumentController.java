/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamienpapiernozyce;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author MMusidlowski
 */
public class FXMLDocumentController implements Initializable {
    
    ArrayList<String> mozliwosci = new ArrayList<>(Arrays.asList("kamien","papier","nozyce"));
    String[] strategie = {"Losowa","Uczenie"};
    Random generator;
    Boolean czyWygralemOstatniaGre = false;
    Boolean czyBylRemis = false;
    Boolean czyPierwszaRunda = true;
    String poprzedniRuchGracza = "";
    String poprzedniRuchKomputera = "";
    String wyborKomputera = "";
    Boolean ifPattern = false;
    ArrayList<String> historia = new ArrayList<>();
    
    @FXML
    private ComboBox<String> comboStrategie;
    
    @FXML
    private TextArea logArea;
    
    @FXML
    private Label labelScoreGracz;
    
    @FXML
    private Label labelScoreKomputer;
    
    @FXML
    private Button buttonKamien;
    
    @FXML
    private Button buttonPapier;
    
    @FXML
    private Button buttonNozyce;
    
    @FXML
    private Button buttonReset;
    
    @FXML
    private void handleButtonKamien(ActionEvent event) {
        logArea.appendText("\nWybrales Kamien. ");
        if(comboStrategie.getSelectionModel().getSelectedItem().equals("Losowa"))wyborKomputera = wybierzLosowa();    
        else wyborKomputera = wybierzUczenie();
        logArea.appendText("Komputer wybrał " + wyborKomputera + ". ");
            switch(wyborKomputera){
                case "kamien":
                    logArea.appendText("Remis.");
                    czyBylRemis = true;
                    break;
                case "papier":
                    logArea.appendText("Komputer wygrał.");
                    labelScoreKomputer.setText(Integer.toString(Integer.parseInt(labelScoreKomputer.getText())+1));
                    czyWygralemOstatniaGre = true;
                    czyBylRemis = false;
                    break;
                case "nozyce":
                    logArea.appendText("Gracz wygrał.");
                    labelScoreGracz.setText(Integer.toString(Integer.parseInt(labelScoreGracz.getText())+1));
                    czyWygralemOstatniaGre = false;
                    czyBylRemis = false;
                    if(ifPattern){
                        ifPattern = false;
                        historia.clear();
                    }
                    break;
            }
        logArea.appendText("\nKomputer wybrał i czeka na Twój ruch.");
        poprzedniRuchKomputera = wyborKomputera;
        poprzedniRuchGracza = "kamien";
        historia.add("kamien");
        if(historia.size() > 3)historia.remove(0);
    }
    
    @FXML
    private void handleButtonPapier(ActionEvent event) {
        logArea.appendText("\nWybrales Papier. ");
        if(comboStrategie.getSelectionModel().getSelectedItem().equals("Losowa"))wyborKomputera = wybierzLosowa();
        else wyborKomputera = wybierzUczenie();
        logArea.appendText("Komputer wybrał " + wyborKomputera + ". ");
            switch(wyborKomputera){
                case "kamien":
                    logArea.appendText("Gracz wygrał.");
                    labelScoreGracz.setText(Integer.toString(Integer.parseInt(labelScoreGracz.getText())+1));
                    czyWygralemOstatniaGre = false;
                    czyBylRemis = false;
                    if(ifPattern){
                        ifPattern = false;
                        historia.clear();
                    }
                    break;
                case "papier":
                    logArea.appendText("Remis.");
                    czyBylRemis = true;
                    if(ifPattern){
                        ifPattern = false;
                        historia.clear();
                    }
                    break;
                case "nozyce":
                    logArea.appendText("Komputer wygrał.");
                    labelScoreKomputer.setText(Integer.toString(Integer.parseInt(labelScoreKomputer.getText())+1));
                    czyWygralemOstatniaGre = true;
                    czyBylRemis = false;
                    break;
            }
        logArea.appendText("\nKomputer wybrał i czeka na Twój ruch.");
        poprzedniRuchKomputera = wyborKomputera;
        poprzedniRuchGracza = "papier";
        if(historia.size() > 2)historia.remove(0);
        historia.add("papier");
        
    }
    
    @FXML
    private void handleButtonNozyce(ActionEvent event) {
        logArea.appendText("\nWybrales Nozyce. ");
        if(comboStrategie.getSelectionModel().getSelectedItem().equals("Losowa"))wyborKomputera = wybierzLosowa();
        else wyborKomputera = wybierzUczenie();
        logArea.appendText("Komputer wybrał " + wyborKomputera + ". ");
            switch(wyborKomputera){
                case "kamien":
                    logArea.appendText("Komputer wygrał.");
                    labelScoreKomputer.setText(Integer.toString(Integer.parseInt(labelScoreKomputer.getText())+1));
                    czyWygralemOstatniaGre = true;
                    czyBylRemis = false;
                    break;
                case "papier":
                    logArea.appendText("Gracz wygrał.");
                    labelScoreGracz.setText(Integer.toString(Integer.parseInt(labelScoreGracz.getText())+1));
                    czyWygralemOstatniaGre = false;
                    czyBylRemis = false;
                   if(ifPattern){
                        ifPattern = false;
                        historia.clear();
                    }
                    break;
                case "nozyce":
                    logArea.appendText("Remis.");
                    czyBylRemis = true;
                    if(ifPattern){
                        ifPattern = false;
                        historia.clear();
                    }
                    break;
            }
        logArea.appendText("\nKomputer wybrał i czeka na Twój ruch.");
        poprzedniRuchKomputera = wyborKomputera;
        poprzedniRuchGracza = "nozyce";
        historia.add("nozyce");
        if(historia.size() > 3)historia.remove(0);
    }
    
    @FXML
    private void handleButtonReset(ActionEvent event){
        resetGry();
        logArea.appendText("Wynik został zresetowany.");
    }
    
    @FXML
    private void handleComboChanged(ActionEvent event){
        resetGry();
        logArea.appendText("Nowa strategia została wybrana, wynik został zresetowany.");
        logArea.appendText("\nKomputer wybrał i czeka na Twój ruch.");
        buttonKamien.setDisable(false);
        buttonPapier.setDisable(false);
        buttonNozyce.setDisable(false);
        buttonReset.setDisable(false);
    }
    
    public void resetGry(){
        labelScoreGracz.setText("0");
        labelScoreKomputer.setText("0");
        logArea.clear();
        czyPierwszaRunda = true;
    }
    
    public String wybierzLosowa(){
        generator = new Random();
        return mozliwosci.get(generator.nextInt(100)%3);
    }
    
    public String wybierzUczenie(){
        if(czyPierwszaRunda){
            czyPierwszaRunda=false;
            return "papier";}
        
        if(checkIfPattern3()){
            ifPattern = true;
            switch(historia.get(0)){
                case "kamien": return "papier";
                case "papier": return "nozyce";
                case "nozyce": return "kamien";
                default: return "papier";
            }
        }
        if(checkIfPattern2()){
            ifPattern = true;
            switch(historia.get(1)){
                case "kamien": return "papier";
                case "papier": return "nozyce";
                case "nozyce": return "kamien";
                default: return "papier";
            }
        }
        
        else{
            if(czyBylRemis){
                switch(poprzedniRuchGracza){
                        case "kamien": return "papier";
                        case "papier": return "nozyce";
                        case "nozyce": return "kamien";
                        default: return "papier";
                    }
            }
            else{
                if(czyWygralemOstatniaGre)return poprzedniRuchGracza;
                else return poprzedniRuchGracza;
            }
        }
    }
    
    public Boolean checkIfPattern2(){
        if(historia.size()==3){
            if(historia.get(0).equals(historia.get(2)))return true;
        }
        return false;
    } 
    
    public Boolean checkIfPattern3(){
        if(historia.size()==3){
            if(historia.contains("papier")&&historia.contains("kamien")&&historia.contains("nozyce"))return true;
        }
        return false;
    }
    
    public String wybierzOpcjeKtorejNieByloWPoprzedniejRundzie(){
        mozliwosci.remove(poprzedniRuchKomputera);
        mozliwosci.remove(poprzedniRuchGracza);
        String wybor = mozliwosci.get(0);
        mozliwosci = new ArrayList<>(Arrays.asList("kamien","papier","nozyce"));
        return wybor;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logArea.textProperty().addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                logArea.setScrollTop(Double.MAX_VALUE);
            }
        });
        
        comboStrategie.getItems().addAll(strategie);
        logArea.appendText("Wybierz strategie komputera.");
    }    
    
}
