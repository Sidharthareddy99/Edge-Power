package com.example.edge_power;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Process dataCollectionProcess;
    private Process mlModelProcess;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),1230,680);

            try {

                stage.getIcons().add(
                        new Image(
                                HelloApplication.class.getResourceAsStream( "icon.jpg" )));

            }catch (Exception e){
                System.out.println(e);
            }
            stage.setTitle("Edge Power");
            stage.setScene(scene);

            dataCollectionProcess = startPythonScript("src/main/Servers/DataCollectio.py");
            mlModelProcess = startPythonScript("src/main/Servers/ML_Model.py");
            stage.show();

          
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                dataCollectionProcess.destroy();
                mlModelProcess.destroy();
            }));
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private Process startPythonScript(String scriptPath) {
    try {
        
        String pythonPath = "python"; 
        String[] command = {pythonPath, scriptPath};

        
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); 

        
        return processBuilder.start();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

}
