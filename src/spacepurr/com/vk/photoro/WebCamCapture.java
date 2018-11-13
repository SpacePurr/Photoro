package spacepurr.com.vk.photoro;

import com.sun.jna.platform.WindowUtils;
import org.opencv.core.Core;
import spacepurr.com.vk.photoro.it.polito.elite.teaching.cv.utils.utils.Utils;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class WebCamCapture {
    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private boolean cameraActive = false;
    private Mat frame;
    private Image imageToShow;
    private String webCamPhoto;
    private Photo photo = Photo.getInstance();

    void startWebCam(Parent root, ImageView webCamView, Button button) {
        if (!this.cameraActive) {
            capture = new VideoCapture();
            this.capture.open(0);

            if (this.capture.isOpened()) {
                this.cameraActive = true;

                Runnable frameGrubber = new Runnable() {
                    @Override
                    public void run() {
                        frame = grabFrame();
                        imageToShow = Utils.mat2Image(frame);
                        updateImageView(webCamView, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrubber, 0, 33, TimeUnit.MILLISECONDS);

                button.setText("");

                Stage stage = (Stage) root.getScene().getWindow();
                stage.setOnCloseRequest(e -> setClosed());
            } else
                System.err.println("Impossible to open the camera connection...");
        } else {
            saveImage();
            this.cameraActive = false;
            button.setText("");
            setClosed();
        }
    }

    private void stopAcquisition() {
        if (this.timer != null && !this.timer.isShutdown()) {
            try {
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception is stopping the frame capture, trying to release the camera now..." + e);
            }
        }
        if (this.capture.isOpened()) {
            this.capture.release();
        }
    }

    private Mat grabFrame() {
        Mat frame = new Mat();

        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame);

                if (!frame.empty()) {
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);
                }
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }

    private void updateImageView(ImageView webCamView, Image imageToShow) {
        Utils.onFXThread(webCamView.imageProperty(), imageToShow);
    }

    private void setClosed() {
        this.stopAcquisition();
    }

    void saveImage() {
        webCamPhoto = "webCam.jpg";
        Imgcodecs.imwrite(webCamPhoto, frame);
        photo.setWebCamPhoto(getImageToShow());
    }

    Image getImageToShow(){
        return new Image(new File(webCamPhoto).toURI().toString());
    }
}
