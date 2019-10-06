package com.example.eatgo.applicaion;

import com.example.eatgo.domain.Review;
import com.example.eatgo.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Long restourantId, Review review) {
        review.setRestourantId(restourantId);
        return reviewRepository.save(review);
    }
}
