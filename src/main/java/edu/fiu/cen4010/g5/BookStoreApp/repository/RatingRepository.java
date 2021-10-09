package edu.fiu.cen4010.g5.BookStoreApp.repository;

import edu.fiu.cen4010.g5.BookStoreApp.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

//    Still trying to get "AND" functionality of DB query to work properly, but implemented logic
//    in Java code for now in RatingService for Post/Put features
////    @Query("{'userid': ?0}, 'bookid': ?1}")
//    @Query("{'$and' : [{'userid': ?0}, 'bookid': ?1}]}")
//    Optional<List<Rating>> findByUserIdAndBookId(String userid, String bookid);

    @Query("{'userid': ?0}")
    Optional<List<Rating>> findByUserId(String userid);

    @Query("{'bookid': ?0}")
    Optional<List<Rating>> findByBookId(String bookid);

}