package com.api.hunter.vagas.builders.dtos;

import com.api.hunter.vagas.enums.Profile;
import com.api.hunter.vagas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UserBuilder {
    private UUID id;
    private String name = "any";
    private String email = "any@email.com";
    private String password = "any_password";
    private String company = null;

    private Profile profile = Profile.ADMIN;



    private  UserBuilder (){};
    public static UserBuilder init(){
        return  new UserBuilder();
    }

    public  UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserBuilder withProfile(Profile profile){
        this.profile = profile;
        return  this;
    }

    public UserBuilder withCompany(String company){
        this.company = company;
        return  this;
    }

    public UserBuilder withPassword(String password){
         this.password = password;
         return  this;
    }

    public User builder(){
        return  new User(
                null,
                name,
                email,
                password,
                company,
                profile
        );
    }





}
