package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO: Task 1

	public String uploadImage(MultipartFile image) {
		String imageId = UUID.randomUUID().toString().substring(0, 8);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(image.getContentType());
		metadata.setContentLength(image.getSize());

		try {
			PutObjectRequest putReq = new PutObjectRequest("csfassessment", imageId, image.getInputStream(), metadata);
			putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putReq);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return "https://csfassessment.sgp1.digitaloceanspaces.com/"+ imageId;
	}

}
