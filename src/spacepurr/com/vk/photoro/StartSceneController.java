package spacepurr.com.vk.photoro;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;

import java.util.ResourceBundle;

public class StartSceneController implements Initializable {

    @FXML
    public StackPane rootStackPane;
    public Button button;
    private SceneLoader sceneLoader = new SceneLoader();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootStackPane.setOpacity(0);
        sceneLoader.blink(button);
        sceneLoader.makeFadeIn(rootStackPane);
    }

    public void toPassportPhoto(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.PASSPORT_PHOTO);

    }
}
