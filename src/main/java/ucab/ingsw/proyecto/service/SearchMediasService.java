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

    private AlertsResponse buildAlert(String invalid_chracters, String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    public void setSearchSocialMedias(SearchSocialMedia socialMedia){
        this.socialMedia = socialMedia;
    }

    public ResponseEntity<Object> search(String tagSearch, String socialMedias){
        if(!(tagSearch.matches("[a-zA-Z0-9]*"))&&(socialMedias.toLowerCase().equals("instagram"))) {
            return ResponseEntity.badRequest().body(buildAlert("Invalid chracters", tagSearch));
        }
        else{
            return socialMedia.seeker(tagSearch);
        }

    }

}
