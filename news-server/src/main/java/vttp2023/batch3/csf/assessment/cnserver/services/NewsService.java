package vttp2023.batch3.csf.assessment.cnserver.services;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private NewsRepository newsRepo;

	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(String title, String description, MultipartFile image, String tags) {

		// Generate a UUID
		// Upload the image to DigitalOcean bucket
		// return full URL and set as image URL in News object
		String imageUrl = imageRepo.uploadImage(image);

		if (tags == null) {
			tags = "";
		}

		String[] tagsArray = tags.trim().split(" ");

		News newPost = new News();
		newPost.setTitle(title);
		newPost.setDescription(description);
		newPost.setImage(imageUrl);
		newPost.setPostDate(System.currentTimeMillis());
		newPost.setTags(Arrays.asList(tagsArray));

		String postId = newsRepo.savePost(newPost);

		return postId;
	}

	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(Integer minutes) {

		List<Document> tagList = newsRepo.getTags(minutes);

		return tagList.stream()
				.map(t -> (new TagCount(t.getString("tag"), t.getInteger("count")))).toList();
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(String tag, Integer duration) {
		return newsRepo.getDocumentsByTagAndDuration(tag, duration);
	}

}
