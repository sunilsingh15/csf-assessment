package vttp2023.batch3.csf.assessment.cnserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
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
	@GetMapping(path = "/posts/{minutes}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TagCount>> getTagsByMinutes(@PathVariable Integer minutes) {
		return ResponseEntity.ok(service.getTags(minutes));
	}

	// TODO: Task 3
	@GetMapping(path = "/posts/{tag}/{duration}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<News>> getPostsByTagAndDuration(@PathVariable String tag, @PathVariable Integer duration) {
		return ResponseEntity.ok(service.getNewsByTag(tag, duration));
	}

}
