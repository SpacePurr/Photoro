package spacepurr.com.vk.photoro;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

class SceneLoader {

    void makeFadeIn(Node partOfInterface) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(partOfInterface);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    void makeFadeOut(Parent root, String fxml) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(e -> loadScene(root, fxml));
        fadeTransition.play();
    }

    private void loadScene(Parent root, String fxml) {
        try {
            Parent newRoot = (StackPane) FXMLLoader.load(getClass().getResource(fxml));
            Scene newScene = new Scene(newRoot);
            Stage newStage = (Stage) root.getScene().getWindow();
            newStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void blink(Node partOfInterface) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), partOfInterface);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }
}
