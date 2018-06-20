package ucab.ingsw.proyecto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import ucab.ingsw.proyecto.response.AlertsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.service.Mediassearch.SearchSocialMedia;
import ucab.ingsw.proyecto.service.Mediassearch.InstagramSearchService;

@CrossOrigin
@Slf4j
@Service("searchMediasService")
public class SearchMediasService {

    private SearchSocialMedia socialMedia;

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    public void setSearchSocialMedias(SearchSocialMedia socialMedia){
        this.socialMedia = socialMedia;
    }

    public ResponseEntity<Object> search(String socialMedias){
        if(socialMedias.matches("[a-zA-Z0-9]*"))
            return  socialMedia.seeker(socialMedias);
        else
            log.info("Invalid characters", socialMedias);
            return ResponseEntity.badRequest().body(buildAlert("Caracteres invalidos"));
    }
}
