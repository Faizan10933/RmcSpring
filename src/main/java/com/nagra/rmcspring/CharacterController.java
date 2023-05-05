package com.nagra.rmcspring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/v1/characters")
public class CharacterController {

    @GetMapping("/")
    public Map<String, List<Map<String, Object>>> getCharacter(@RequestParam("name") String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://rickandmortyapi.com/api/character/?name=" + name;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
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
        return result;
    }
}
