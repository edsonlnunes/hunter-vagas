package com.api.hunter.vagas.builders.dtos;

import com.api.hunter.vagas.dtos.CreateUser;
import com.api.hunter.vagas.enums.Profile;

import java.util.UUID;

public class CreateUserBuilder {
    private UUID id;
    private String name = "any";
    private String email = "any@email.com";
    private String password = "any_password";
    private String company = null;

    private Profile profile = Profile.ADMIN;

    private  CreateUserBuilder (){};
    public static CreateUserBuilder init(){
        return  new CreateUserBuilder();
    }

    public  CreateUserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public CreateUserBuilder withProfile(Profile profile){
        this.profile = profile;
        return  this;
    }

    public CreateUserBuilder withCompany(String company){
        this.company = company;
        return  this;
    }

    public CreateUser builder(){
        return  new CreateUser(
                name,
                email,
                password,
                profile,
                company
        );
    }

}
