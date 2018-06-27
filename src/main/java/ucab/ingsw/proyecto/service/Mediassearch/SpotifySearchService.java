package ucab.ingsw.proyecto.service.Mediassearch;


import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.response.SpotifyListResponse;
import ucab.ingsw.proyecto.response.SpotifyResponse;
import ucab.ingsw.proyecto.service.SpotifyData.*;


import java.util.ArrayList;
import java.util.List;


@Slf4j
public class SpotifySearchService implements SearchSocialMedia{

    public static String TOKEN=" BQD02rcoMCkcRSzSTF5MYc1liRprc2Pp8cUy_om7YihbqmAdYj_8jwLPjqXCHmctCFx2CKrgZd9DPs-Nf9dm6JrWC0yLWQmrqr7QU5qMXcWG45cMvFlXBEB8t_SWpVR8F_neGKfs68yz2hsYNMQ1j84&token_type=Bearer&expires_in=3600" ;
    public static final String CREDENTIALBASE64 ="ZmY5MDUyM2QyMTIwNGViYjkwMzI1YjgxYTllNzNjZjI6ZGE4YTNkMWRlNzFmNDhiODk1MmIwODY1YjdkOTRhNjU=";
    public static final String PAGE_SIZE="10";

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    private SpotifyListResponse buildResponse(Tracks tracks){
        SpotifyListResponse spotifyListResponse = new SpotifyListResponse();
        List<SpotifyResponse> trackResponse = new ArrayList<>();
        tracks.getItems().forEach( items -> {
            SpotifyResponse spotifyResponse = new SpotifyResponse();
            spotifyResponse.setName(items.getName());
            spotifyResponse.setAlbumImageUrl(items.getAlbum().getImages().get(1).getUrl());
            List<String> artist = new ArrayList<>();
            items.getArtists().forEach(k->{

                artist.add(k.getName());
            });
            spotifyResponse.setArtists(artist);
            spotifyResponse.setUrl(items.getExternal_urls().getSpotify());
            trackResponse.add(spotifyResponse);
        });
        spotifyListResponse.setTracks(trackResponse);
        return spotifyListResponse;

    }

    public String getNewToken(){
        String tokenUrl="https://accounts.spotify.com/api/token";
        RestTemplate tokenTemplate =new RestTemplate();
        HttpHeaders tokenHeaders= new HttpHeaders();
        tokenHeaders.add("Content-Type","application/x-www-form-urlencoded");
        tokenHeaders.add("Authorization","Basic "+CREDENTIALBASE64);
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("grant_type","client_credentials");
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity(params,tokenHeaders);
        ResponseEntity<Token> tokenResponse=tokenTemplate.exchange(tokenUrl,HttpMethod.POST,tokenRequest,Token.class);
        Token token=tokenResponse.getBody();
        TOKEN=token.getAccess_token();
        return TOKEN;
    }

    public ResponseEntity<Object> seeker(String trackToSearch){
        String searchUrl="https://api.spotify.com/v1/search?type=track&q="+trackToSearch+"&limit="+PAGE_SIZE;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ getNewToken());
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Container> response=restTemplate.exchange(searchUrl,HttpMethod.GET,request,Container.class);
        Container conteiner = response.getBody();


        if (conteiner.getTracks().getItems().isEmpty()){

            log.info("No result for  ={}", trackToSearch);
            return ResponseEntity.badRequest().body(buildAlert("No hay resultados"));
        }else{

            log.info("Results for={}", trackToSearch);
            return ResponseEntity.ok(buildResponse(conteiner.getTracks()));


        }
    }




}

