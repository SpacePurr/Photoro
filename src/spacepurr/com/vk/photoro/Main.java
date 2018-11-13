package spacepurr.com.vk.photoro;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Field;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        initOpenCv();
        Parent root = FXMLLoader.load(getClass().getResource(ConstantFXML.START_SCENE));

        primaryStage.setTitle("Photoro");
        primaryStage.setResizable(false);
        //primaryStage.setOpacity(0.95);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void initOpenCv() {

        setLibraryPath();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.out.println("OpenCV loaded. Version: " + Core.VERSION);

    }

    private static void setLibraryPath() {

        try {

            System.setProperty("java.library.path", "lib/x64");

            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
}