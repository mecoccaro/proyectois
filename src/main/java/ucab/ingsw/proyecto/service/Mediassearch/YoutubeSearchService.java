package ucab.ingsw.proyecto.service.Mediassearch;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import lombok.extern.slf4j.Slf4j;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.http.ResponseEntity;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.response.YoutubeResponse;
import ucab.ingsw.proyecto.response.YoutubeListResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j

public class YoutubeSearchService implements SearchSocialMedia {

    private static final String KEY = "AIzaSyCvUGU13UGsXQs1xSUIpDBjlNKYcSDy9Ho";
    private static final String TYPE = "Video";

    private YoutubeListResponse buildResponse(SearchListResponse searchResponse){

        YoutubeListResponse youtubeListResponse = new YoutubeListResponse();
        List<YoutubeResponse> youtubeResponseList = new ArrayList<>();
        List<SearchResult> searchResultList = searchResponse.getItems();
        //YoutubeResponse youtubeResponse = new YoutubeResponse();

        searchResultList.forEach(i-> {
            YoutubeResponse youtubeResponse = new YoutubeResponse();
            youtubeResponse.setType(TYPE);
            youtubeResponse.setTitle(i.getSnippet().getTitle());
            youtubeResponse.setUrl(buildUrl(i.getId().getVideoId()));
            youtubeResponseList.add(youtubeResponse);
        });
        youtubeListResponse.setItems(youtubeResponseList);
        return youtubeListResponse;
    }

    private String buildUrl(String Id){
        return "https://www.youtube.com/watch?v="+Id;
    }

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    private YouTube buildYoutube(){
        return  new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                (request) -> {}).setApplicationName("Proyectois").build();
    }

    public ResponseEntity<Object> seeker(String videoToSearch) {
        try{
            YouTube youTube = buildYoutube();
            YouTube.Search.List search = youTube.search().list("id,snippet");
            search.setKey(KEY);
            search.setQ(videoToSearch);
            search.setType(TYPE);
            SearchListResponse searchResponse = search.execute();
            YoutubeListResponse youtubeListResponse = buildResponse(searchResponse);
            if(youtubeListResponse.getItems().isEmpty()){

                log.info("No result for ={}", videoToSearch);

                return ResponseEntity.badRequest().body(buildAlert("No hay resultados para la busqueda"));
            }
            else{
                log.info("Returning results for ={}", videoToSearch);
                return ResponseEntity.ok(youtubeListResponse);
            }
        } catch(GoogleJsonResponseException e){
            log.info("Error", videoToSearch);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Throwable t){
            return ResponseEntity.badRequest().body(t.getMessage());
        }
    }


}
