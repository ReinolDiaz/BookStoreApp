package edu.fiu.cen4010.g5.BookStoreApp.service;

import edu.fiu.cen4010.g5.BookStoreApp.model.Rating;
import edu.fiu.cen4010.g5.BookStoreApp.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addRating(Rating rating) {
        ratingRepository.insert(rating);
    }

    public void updateRating(Rating rating) {
        Rating savedRating = ratingRepository.findByUserIdAndBookId(rating.getUserId(), rating.getBookId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot Find Rating for Book ID %s by User ID %s", rating.getBookId(), rating.getUserId())
                ));

        savedRating.setUserId(rating.getUserId());
        savedRating.setBookId(rating.getBookId());
        savedRating.setDate(rating.getDate());
        savedRating.setValue(rating.getValue());
        savedRating.setComment(rating.getComment());

        ratingRepository.save(savedRating);
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByUser(String userId) {
        return ratingRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find Ratings by User %s", userId)
        ));
    }

    public List<Rating> getRatingsByBook(String bookId) {
        return ratingRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find Ratings for Book %s", bookId)
        ));
    }

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }

}
