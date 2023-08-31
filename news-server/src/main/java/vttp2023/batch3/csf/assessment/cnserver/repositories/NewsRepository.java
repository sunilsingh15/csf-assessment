package vttp2023.batch3.csf.assessment.cnserver.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate template;

	// TODO: Task 1
	// Write the native Mongo query in the comment above the method

	public String savePost(News post) {

		Document postToSave = new Document();
		postToSave.append("postDate", post.getPostDate());
		postToSave.append("title", post.getTitle());
		postToSave.append("description", post.getDescription());
		postToSave.append("image", post.getImage());

		if (!post.getTags().get(0).equals("")) {
			postToSave.append("tags", post.getTags());
		}

		template.save(postToSave, "news");
		return postToSave.getObjectId("_id").toString();
	}

	// TODO: Task 2
	// Write the native Mongo query in the comment above the method

	// TODO: Task 3
	// Write the native Mongo query in the comment above the method

}
