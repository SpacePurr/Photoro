package spacepurr.com.vk.photoro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;

import java.util.ResourceBundle;

public class WebcamSceneController implements Initializable {

    @FXML
    public StackPane rootStackPane;
    @FXML
    public ImageView webCamView;
    @FXML
    public Button button;
    //public Button buttonPhoto;
    private boolean buttonPhotoVisible = false;

    private SceneLoader sceneLoader = new SceneLoader();
    private WebCamCapture webCamCapture = new WebCamCapture();
    private Photo photo = Photo.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (photo.getWebCamPhoto() != null) {
            webCamView.setImage(photo.getWebCamPhoto());
            button.setText("");
        }

        rootStackPane.setOpacity(0);
        sceneLoader.blink(button);
        sceneLoader.makeFadeIn(rootStackPane);

    }

    public void toPassportPhoto(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.PASSPORT_PHOTO);
    }

    public void toStatsScreen(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.STATS_SCREEN);
    }

    public void startWebCam(ActionEvent actionEvent) {
        webCamCapture.startWebCam(rootStackPane, webCamView, button);
    }
}
