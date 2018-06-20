package ucab.ingsw.proyecto.service.Mediassearch;

import org.springframework.http.ResponseEntity;

public interface SearchSocialMedia {
    public ResponseEntity<Object> seeker(String socialMedia);
}
