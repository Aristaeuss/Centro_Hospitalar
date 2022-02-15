package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.models.users.UserImage;
import b3.CentroHospitalar.repositories.UserImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UserImageServiceImpl implements UserImageService {

    @Autowired
    UserImageRepository pr;
    @Autowired
    UserService userService;

    @Override
    public void saveImage(UserImage image) {
        pr.save(image);
    }

    @Override
    public void delete(UserImage image) {
        pr.delete(image);
    }

    @Override
    public UserImage findByOwner(User owner) {
        return pr.findByOwner(owner);
    }

    @Override
    public boolean updateImageFor(User user, MultipartFile newFile) {
        if(newFile.isEmpty()){
            return false;
        }
        UserImage userImage = findByOwner(user);
        if (userImage == null) {
            userImage = new UserImage(user, "");
            saveImage(userImage);
        } else {
            try {
                Files.delete(Paths.get(userImage.getPath()));
            } catch (IOException e) {
                System.out.println("Exception caught in deleting an old image file");
            }
        }
        String fileName = newFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        String rootDir = System.getProperty("user.dir");
        String writePath = rootDir + "/UploadedImages/" + user.getNif() + "_" + userImage.getId() + "." + fileType;
        String springPath = "UploadedImages/" + user.getNif() + "_" + userImage.getId() + "." + fileType;
        userImage.setPath(springPath);
        //try save the image:
        try (FileOutputStream fos = new FileOutputStream(writePath)) {
            fos.write(newFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            //Failed to save, so remove from database.
            delete(userImage);
            user.setUserImage(null);
            userService.updateUser(user);
            return false;
        }
        user.setUserImage(userImage);
        userService.updateUser(user);
        return true;
    }

}
