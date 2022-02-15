package b3.CentroHospitalar.services;

import b3.CentroHospitalar.models.users.User;
import b3.CentroHospitalar.models.users.UserImage;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageService {

    void saveImage(UserImage image);

    void delete(UserImage image);

    public UserImage findByOwner(User owner);

    public boolean updateImageFor(User user, MultipartFile newFile);
}


