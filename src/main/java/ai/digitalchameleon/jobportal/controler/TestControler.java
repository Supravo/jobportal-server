package ai.digitalchameleon.jobportal.controler;

import ai.digitalchameleon.jobportal.enums.AuthorisedUserType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("test")
public class TestControler {

    @GetMapping("platform_admin")
    @Secured(AuthorisedUserType.PLATFORM_ADMIN)
    public String platformAdmin() {
        return "Allowed";
    }

    @GetMapping("org_admin")
    @Secured(AuthorisedUserType.ORG_ADMIN)
    public String orgAdmin() {
        return "Allowed";
    }

    @GetMapping("recruiter")
    @Secured(AuthorisedUserType.RECRUITER)
    public String recruiter() {
        return "Allowed";
    }

    @GetMapping("job_seeker")
    @Secured(AuthorisedUserType.JOB_SEEKER)
    public String job_seeker() {
        return "Allowed";
    }

}
