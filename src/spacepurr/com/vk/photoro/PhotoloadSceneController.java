package spacepurr.com.vk.photoro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class PhotoloadSceneController implements Initializable {

    @FXML
    public ImageView imageView;
    @FXML
    public StackPane rootStackPane;
    @FXML
    public Button button;
    @FXML
    public ImageView blur;
    @FXML
    public Button arrowLeft;
    @FXML
    public Button arrowRight;

    private Photo photo = Photo.getInstance();
    private SceneLoader sceneLoader = new SceneLoader();
    private File fileCopy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try (FileInputStream left = new FileInputStream("left.png");
             FileInputStream right = new FileInputStream("right.png")) {
            arrowLeft.setGraphic(new ImageView(new Image(left)));
            arrowRight.setGraphic(new ImageView(new Image(right)));

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (photo.getPassportPhoto() != null) {
            imageView.setImage(photo.getPassportPhoto());
            blur.setImage(photo.getPassportPhoto());
            button.setText("");
        }

        rootStackPane.setOpacity(0);

        sceneLoader.blink(button);
        sceneLoader.makeFadeIn(rootStackPane);
    }

    public void toWebCamScreen(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.WEB_CAM);
    }

    public void toStartScene(ActionEvent actionEvent) {
        sceneLoader.makeFadeOut(rootStackPane, ConstantFXML.START_SCENE);
    }

    public void openImage(ActionEvent actionEvent) {
        try {
            photo.setAnglePassportPhoto(0);
            Stage stage = (Stage) rootStackPane.getScene().getWindow();
            File fileSource = new FileChooser().showOpenDialog(stage);
            fileCopy = new File("passportPhoto.jpg");
            if (fileCopy.exists()) {
                Files.delete(fileCopy.toPath());
            }
            Files.copy(fileSource.toPath(), fileCopy.toPath());
            button.setText("");
            Image image = new Image(fileCopy.toURI().toString());

            imageView.setRotate(photo.getAnglePassportPhoto());
            imageView.setImage(image);
            blur.setImage(image);


            photo.setPassportPhoto(image);
        } catch (Exception ignored) {

        }
    }


    public void turnLeft(ActionEvent actionEvent) {
        if (imageView != null) {
            try {
                photo.setAnglePassportPhoto(photo.getAnglePassportPhoto() - 90);
                imageView.setRotate(photo.getAnglePassportPhoto());
                saveImage(leftRotate90(ImageIO.read(fileCopy)));
                photo.setPassportPhoto(new Image(fileCopy.toURI().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void turnRight(ActionEvent actionEvent) {
        if (imageView != null) {
            try {
                photo.setAnglePassportPhoto(photo.getAnglePassportPhoto() + 90);
                imageView.setRotate(photo.getAnglePassportPhoto());
                saveImage(rightRotate90(ImageIO.read(fileCopy)));
                photo.setPassportPhoto(new Image(fileCopy.toURI().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveImage(BufferedImage image) {
        try {
            ImageIO.write(image, "jpg",fileCopy);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static BufferedImage rightRotate90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    private static BufferedImage leftRotate90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate(-(height - width) / 2, -(height - width) / 2);
        graphics2D.rotate(-Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

}
