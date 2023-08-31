package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate template;

	// TODO: Task 1
	// Write the native Mongo query in the comment above the method

	// MONGO QUERY:
	// use assessment
	// db.news.insertOne(
	// {
	// postDate: << date >>,
	// title: << title >>,
	// description: << description >>,
	// image: << image URL >>,
	// tags: << tags, if applicable >>,
	// }
	// )

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

	// MONGO QUERY:
	// use assessment
	// db.news.aggregate([
	// {
	// $match: {
	// postDate: { $gte: new Date().getTime() - (10 * 60 * 1000) },
	// tags: { $exists: true, $ne: [] }
	// }
	// },
	// {
	// $unwind: "$tags"
	// },
	// {
	// $group: {
	// _id: "$tags",
	// count: { $sum: 1 }
	// }
	// },
	// {
	// $sort: { count: -1, _id: 1 }
	// },
	// {
	// $limit: 10
	// },
	// {
	// $project: {
	// _id: 0,
	// tag: "$_id",
	// count: 1
	// }
	// }
	// ])

	public List<Document> getTags(Integer minutes) {
		MatchOperation match = Aggregation.match(Criteria.where("postDate")
				.gte(System.currentTimeMillis() - (minutes * 60 * 1000)).and("tags").exists(true));
		UnwindOperation unwind = Aggregation.unwind("tags");
		GroupOperation group = Aggregation.group("tags").count().as("count");
		SortOperation sort = Aggregation.sort(Sort.by(Sort.Order.desc("count"), Sort.Order.asc("_id")));
		LimitOperation limit = Aggregation.limit(10);
		ProjectionOperation project = Aggregation.project()
				.andExclude("_id")
				.and("_id").as("tag")
				.and("count").as("count");

		Aggregation pipeline = Aggregation.newAggregation(match, unwind, group, sort, limit, project);

		return template.aggregate(pipeline, "news", Document.class).getMappedResults();
	}

	// TODO: Task 3
	// Write the native Mongo query in the comment above the method

}
