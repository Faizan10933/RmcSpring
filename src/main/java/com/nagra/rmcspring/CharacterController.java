package com.nagra.rmcspring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getCharacter(@RequestParam("name") String name) {
        log.info("Request received for name: {}", name);

        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://rickandmortyapi.com/api/character/?name=" + name;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.get("results") == null) {
                log.warn("No record found for name: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<Map<String, Object>> characters = (List<Map<String, Object>>) response.get("results");
            List<Map<String, Object>> simplifiedCharacters = new ArrayList<>();
            for (Map<String, Object> character : characters) {
                Map<String, Object> simplifiedCharacter = new HashMap<>();
                simplifiedCharacter.put("id", character.get("id"));
                simplifiedCharacter.put("name", character.get("name"));
                if (character.containsKey("image")) {
                    simplifiedCharacter.put("image", character.get("image"));
                }
                simplifiedCharacters.add(simplifiedCharacter);
            }
            Map<String, List<Map<String, Object>>> result = new HashMap<>();
            result.put("characters", simplifiedCharacters);

            log.info("Response retrieved successfully");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error occurred while fetching character information", e);
            log.warn("No record found for name: {}", name);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
