package com.example.eatgo.interfaces;

import com.example.eatgo.applicaion.ReviewService;
import com.example.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restourants/{restourantId}/reviews")
    public ResponseEntity<?> create(
            @PathVariable("restourantId") Long restourantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Review review = reviewService.addReview(restourantId, resource);
        String url = "/restourants/"+restourantId+"/reviews/"+review.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
