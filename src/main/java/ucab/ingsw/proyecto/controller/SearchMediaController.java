package ucab.ingsw.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.service.Mediassearch.InstagramSearchService;
import ucab.ingsw.proyecto.service.Mediassearch.YoutubeSearchService;
import ucab.ingsw.proyecto.service.SearchMediasService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value="/search", produces = "application/json")
public class SearchMediaController {

    @Autowired
    private SearchMediasService searchMediasService;

    private List<String> socialMedias;

    private void setValidSocialMedias(){

        socialMedias = new ArrayList<>();
        socialMedias.add("instagram");
        socialMedias.add("spotify");
        socialMedias.add("youtube");
    }

    private boolean checkSocialMedia(String socialMedia){
        setValidSocialMedias();
        if(socialMedias.contains(socialMedia.toLowerCase())){
            return true;
        }
        else
            return false;
    }

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }
    @RequestMapping(value = "/{socialMedia}", method = RequestMethod.GET)
    public ResponseEntity search(@PathVariable("socialMedia") String socialMedia, @RequestParam("q") String tagSearch) {
        if(checkSocialMedia(socialMedia)) {
            if (socialMedia.toLowerCase().equals(socialMedias.get(0))) {
                searchMediasService.setSearchSocialMedias(new InstagramSearchService());
            }else {
                searchMediasService.setSearchSocialMedias(new YoutubeSearchService());
            }
        tagSearch = tagSearch.replace(" ", "");
        return searchMediasService.search(tagSearch);
        }
        else{
            return ResponseEntity.badRequest().body(buildAlert("invalid social media"));
        }
    }
}
