package backendimc.controller;

import backendimc.entity.ImcDetails;
import backendimc.service.ImcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/imc")
public class ImcController {

    private ImcService imcService;
    public HttpResponse<String> ImcController(ImcService imcService) throws IOException, InterruptedException, URISyntaxException {
        this.imcService = imcService;
        String url = "http://localhost:4200";
//        try {
//            URI uri = new URI( String.format(
//                    "http://finance.yahoo.com/q/h?s=%s",
//                    URLEncoder.encode( url , "UTF8" ) ) );
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("Access-Control-Allow-Origin" , "*")
                .setHeader("Access-Control-Allow-Credentials", "true")
                .setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept")
                .uri(new URI( String.format(
                        "http://localhost:4200",
                        URLEncoder.encode( url , "UTF8" ) ) )).build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "http://localhost:15003/imc")
    @PostMapping
    public ResponseEntity<ImcDetails> postImc(@Valid @RequestBody ImcDetails imcDetails) {
        return ResponseEntity.ok(imcService.saveImc(imcDetails));
    }

    @CrossOrigin(origins = "http://localhost:15003/imc")
    @GetMapping
    public ResponseEntity<List<ImcDetails>> getImcs() {
        return ResponseEntity.ok(imcService.listImcs());
    }
}
