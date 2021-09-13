package edu.fiu.cen4010.g5.BookStoreApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("user")
public class User {

    @Id
    private String id;
    @Field("email")
    @Indexed(unique = true)
    private String email;
    @Field("firstname")
    private String firstName;
    @Field("lastname")
    private String lastName;

}
