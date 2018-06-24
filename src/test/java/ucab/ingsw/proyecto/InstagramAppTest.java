package ucab.ingsw.proyecto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;
import ucab.ingsw.proyecto.service.Mediassearch.InstagramSearchService;

import static org.junit.Assert.assertEquals;

public class InstagramAppTest {

    private String search;
    private String invalidSearch;
    private  InstagramSearchService searchService;

    @Before
    public void inicializacion(){
        search = "Bogota";
        invalidSearch = "Bogta";
        searchService = new InstagramSearchService();
    }

    @After
    public void tearDown(){
        search = null;
        invalidSearch = null;
        searchService = null;
    }

    @Test
    public void instagramSearch(){
        try{
            ResponseEntity<Object> responseEntity = searchService.seeker(search);
            assertEquals(responseEntity.toString(), 200, responseEntity.getStatusCode().value());
        }catch (Throwable exception){
            Assert.fail(exception.getMessage());
        }
    }

    @Test
    public void instagramSearchFailed(){
        try{
            ResponseEntity<Object> responseEntity = searchService.seeker(invalidSearch);
            assertEquals(responseEntity.toString(), 400,responseEntity.getStatusCode().value());
        }catch (Throwable exception){
            Assert.fail(exception.getMessage());
        }
    }
}
