package ucab.ingsw.proyecto.service.Mediassearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.response.InstagramVideoResponse;
import ucab.ingsw.proyecto.service.instagramData.Values;
import ucab.ingsw.proyecto.response.InstagramResponse;
import ucab.ingsw.proyecto.service.instagramData.InstagramList;

import java.util.ArrayList;
import java.util.List;

@Slf4j

public class InstagramSearchService implements SearchSocialMedia {

    private List<InstagramResponse> buildResponse(List<Values> values){
        List<InstagramResponse> instagramResponseList = new ArrayList<>();
        values.forEach(i -> {
            InstagramResponse instagramResponse = new InstagramResponse();
            if(i.getType().equals("image"))
                instagramResponse = new InstagramResponse();
            else {
                instagramResponse = new InstagramVideoResponse();
                ((InstagramVideoResponse) instagramResponse).setVideoUrl(i.getVideos().getStandard_resolution().getUrl());
            }
            instagramResponse.setImageUrl(i.getImages().getStandard_resolution().getUrl());
            instagramResponse.setInstagramLink(i.getLink());
            instagramResponse.setType(i.getType());
            instagramResponseList.add(instagramResponse);
        });
        return instagramResponseList;
    }



    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    private static final String AUTH_TOKEN = "46287188.09cd0e3.0deed527f71b46ffa93f73065f570f48";

    public ResponseEntity<Object> seeker(String tagToSearch) {

        String searchUrl = "https://api.instagram.com/v1/tags/"+tagToSearch+"/media/recent?access_token="+AUTH_TOKEN;
        RestTemplate restTemplate = new RestTemplate();
        InstagramList instagramList = restTemplate.getForObject(searchUrl, InstagramList.class);
        if(instagramList.getData().isEmpty()){
            log.info("No result ={}", tagToSearch);

            return ResponseEntity.badRequest().body(buildAlert("No hay resultados para esta busqueda"));
        }
        else {

            log.info("Results for tag ={}", tagToSearch);
            return ResponseEntity.ok(buildResponse(instagramList.getData()));
        }

    }
}
