package b3.CentroHospitalar.controllers.Advices;

import b3.CentroHospitalar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @Autowired
    UserService userService;

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        System.out.println(request.getServletPath());
        ModelAndView mav = new ModelAndView(request.getServletPath().replaceAll("/", ""));
        mav.addObject("loggedInUser", userService.getLoggedUser());
        mav.addObject("maxFileSizeErrorMessage", "Max file size is 1MB");
        return mav;
    }
}