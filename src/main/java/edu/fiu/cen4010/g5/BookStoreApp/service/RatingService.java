package edu.fiu.cen4010.g5.BookStoreApp.service;

import edu.fiu.cen4010.g5.BookStoreApp.model.Rating;
import edu.fiu.cen4010.g5.BookStoreApp.model.RatingSortedByValueDes;
import edu.fiu.cen4010.g5.BookStoreApp.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addRating(Rating rating) {

        // TODO: Validate user and book from other features (dependencies)
        // TODO: Validate value 0-5 stars

        Optional<List<Rating>> repositoryResults = ratingRepository.findByUserId(rating.getUserid());

        // if no ratings already exist for this user, go ahead and add new rating (user/book already validated)
        if (repositoryResults.isPresent() == false) {
            ratingRepository.insert(rating);
        }

        // if ratings do exist by this user, check that there is not already a rating for this book
        else {
            List<Rating> queryResultsForUser = repositoryResults.get();
            List<Rating> queryResultsForUserAndBook = new ArrayList<>();

            for (Rating r : queryResultsForUser) {
                if (r.getBookid().equals(rating.getBookid())) {
                    queryResultsForUserAndBook.add(r);
                }
            }

            if (queryResultsForUserAndBook.size() == 0) {
                // no rating by this user for this book, so insert it
                ratingRepository.insert(rating);
            }
            else {
                // rating for this book by this user already exists, use the Put API to update instead of insert
                throw new RuntimeException(String.format("Found Existing Rating for Book ID %s by User ID %s", rating.getBookid(), rating.getUserid()));
            }
        }
    }

    public void updateRating(Rating rating) {

//        TODO: Validate user and book from other features (dependencies)
//        TODO: Validate value 0-5 stars

////      For testing.
//        System.out.println("INSIDE OF UPDATE BEFORE CALL TO REPOSITORY FUNCTION");

        Optional<List<Rating>> repositoryResults = ratingRepository.findByUserId(rating.getUserid());

        if (repositoryResults.isPresent() == false) {
            throw new RuntimeException(String.format("Cannot find Ratings by User %s", rating.getUserid()));
        }
        else {
            List<Rating> queryResultsForUser = repositoryResults.get();
            List<Rating> queryResultsForUserAndBook = new ArrayList<>();

            for (Rating r : queryResultsForUser) {

////              For testing purposes
//                System.out.println(r.getUserid() + " : " + r.getBookid());

                if (r.getBookid().equals(rating.getBookid())) {
                    queryResultsForUserAndBook.add(r);
                }
            }

////          For testing purposes
//            System.out.println(queryResultsForUserAndBook.size());
//            for (Rating r : queryResultsForUserAndBook) {
//                System.out.println(r.getUserid() + " : " + r.getBookid());
//
//            }

            if (queryResultsForUserAndBook.size() == 0) {
                throw new RuntimeException(String.format("Cannot Find Rating for Book ID %s by User ID %s", rating.getBookid(), rating.getUserid()));
            } else if (queryResultsForUserAndBook.size() > 1) {
////              For testing purposes
//                for (Rating r : queryResultsForUserAndBook) {
//                    System.out.println(r.getUserid() + " : " + r.getBookid());
//                }
                throw new RuntimeException(String.format("Found Duplicate Ratings for Book ID %s by User ID %s", rating.getBookid(), rating.getUserid()));
            } else {
                Rating savedRating = queryResultsForUserAndBook.get(0);

                savedRating.setUserid(rating.getUserid());
                savedRating.setBookid(rating.getBookid());
                savedRating.setDate(rating.getDate());
                savedRating.setValue(rating.getValue());
                savedRating.setComment(rating.getComment());

                ratingRepository.save(savedRating);
            }
        }

//        Rating savedRating = ratingRepository.findByUserIdAndBookId(rating.getUserid(), rating.getBookid())
//                .orElseThrow(() -> new RuntimeException(
//                        String.format("Cannot Find Rating for Book ID %s by User ID %s", rating.getBookid(), rating.getUserid())
//                ));

////      For testing
//        System.out.println("REPOSITORY FUNCTION RAN");

    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> getRatingsByUser(String userId) {
        // TODO: validate user

        return ratingRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find Ratings by User %s", userId)
        ));
    }

    public List<Rating> getRatingsByBook(String bookId) {
        // TODO: validate book

        return ratingRepository.findByBookId(bookId).orElseThrow(() -> new RuntimeException(
                String.format("Cannot find Ratings for Book %s", bookId)
        ));
    }

    public List<Rating> getRatingsByBookSortedDes(String bookid) {
        // validation of book and existence of results performed in getRatingsByBook method

        List<Rating> unsortedRatings = getRatingsByBook(bookid);
        unsortedRatings.sort(new RatingSortedByValueDes());

        return unsortedRatings;
    }

    public void deleteRating(String id) {
        ratingRepository.deleteById(id);
    }

    public float getAverageRating(String bookid) {
        // validation of bookid done in getRatingsByBook method

        List<Rating> allRatingsByBook = getRatingsByBook(bookid);
        long sum = 0;

        for (Rating r : allRatingsByBook) {
            sum += r.getValue();
        }

        return sum / (float) allRatingsByBook.size();
    }

}
