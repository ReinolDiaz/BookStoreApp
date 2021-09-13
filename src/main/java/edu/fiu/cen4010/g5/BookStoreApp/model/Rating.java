package edu.fiu.cen4010.g5.BookStoreApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("rating")
public class Rating {

    @Id
    private String id;
    @Field("bookid")
    private String bookId;
    @Field("userid")
    private String userId;
    @Field("comment")
    private String comment;
    @Field("date")
    private String date;



}
