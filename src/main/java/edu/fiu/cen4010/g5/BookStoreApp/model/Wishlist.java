package edu.fiu.cen4010.g5.BookStoreApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("wishlist")
public class Wishlist {

    @Id
    public String id;
    @Field("userid")
    private String userId;

}
