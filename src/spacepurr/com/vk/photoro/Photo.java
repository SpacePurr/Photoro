package spacepurr.com.vk.photoro;

import javafx.scene.image.Image;

class Photo {
    private Image passportPhoto;
    private Image webCamPhoto;
    private int anglePassportPhoto;

    private static Photo ourInstance = new Photo();

    private Photo() {
    }

    static Photo getInstance() {
        return ourInstance;
    }

    Image getPassportPhoto() {
        return passportPhoto;
    }

    void setPassportPhoto(Image passportPhoto) {
        this.passportPhoto = passportPhoto;
    }

    Image getWebCamPhoto() {
        return webCamPhoto;
    }

    void setWebCamPhoto(Image webCamPhoto) {
        this.webCamPhoto = webCamPhoto;
    }

    int getAnglePassportPhoto() {
        return anglePassportPhoto;
    }

    void setAnglePassportPhoto(int anglePassportPhoto) {
        if (anglePassportPhoto == 360 || anglePassportPhoto == -360)
            this.anglePassportPhoto = 0;
        else
            this.anglePassportPhoto = anglePassportPhoto;
    }
}
