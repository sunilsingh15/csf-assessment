package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

@RestController
@RequestMapping(path = "/api")
public class NewsController {

	@Autowired
	private NewsService service;

	// TODO: Task 1
	@PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postNewsArticle(@RequestPart String title, @RequestPart String description,
			@RequestPart MultipartFile image, @RequestPart(required = false) String tags) {

		String newsId = service.postNews(title, description, image, tags);

		JsonObject returnObj = Json.createObjectBuilder()
				.add("newsId", newsId)
				.build();

		return ResponseEntity.ok(returnObj.toString());
	}

	// TODO: Task 2

	// TODO: Task 3

}
