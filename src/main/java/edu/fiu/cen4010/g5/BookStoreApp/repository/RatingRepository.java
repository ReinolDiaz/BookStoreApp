package edu.fiu.cen4010.g5.BookStoreApp.repository;

import edu.fiu.cen4010.g5.BookStoreApp.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends MongoRepository<Rating, String> {

    @Query("{'userid': ?0}, 'bookid': ?1}")
    Optional<Rating> findByUserIdAndBookId(String userid, String bookid);

    @Query("{'userid': ?0}")
    Optional<List<Rating>> findByUserId(String userid);

    @Query("{'bookid': ?0}")
    Optional<List<Rating>> findByBookId(String bookid);

}