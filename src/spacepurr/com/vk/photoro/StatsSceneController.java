package spacepurr.com.vk.photoro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;

import java.util.ResourceBundle;

public class StatsSceneController implements Initializable {

    @FXML
    public StackPane rootStackPane;
    public Label labelResult;
    public Button button;
    private Photo photo = Photo.getInstance();
    private SceneLoader sceneLoader = new SceneLoader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootStackPane.setOpacity(0);
        sceneLoader.blink(button);
        sceneLoader.makeFadeIn(rootStackPane);
    }


    public void toWebCamScreen(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.WEB_CAM);
    }

    public void faceD(ActionEvent actionEvent) {
        if(photo.getWebCamPhoto() != null && photo.getPassportPhoto() != null) {
            FaceApi.verify(photo.getPassportPhoto(), photo.getWebCamPhoto());
            try {
                labelResult.setText(getResult(FaceApi.result));
                button.setDisable(true);
                button.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getResult(String result) {
        String values[] = result.split(",");
        String[] confidence = values[1].split(":");
        Double procent = Double.parseDouble(confidence[1].replace("}", ""));
        return "Процент идентичности: " + (double) Math.round(procent * 100) + "%";
    }
}
