package com.example.frienzy.Models

 public class User {
    var image:String?=null
    var name:String?=null
    var email:String?=null
    var password:String?=null

    constructor(image: String?, name: String?, email: String?, password: String?) {
        this.image = image
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(name: String?, email: String?, password: String?) {

        this.name = name
        this.email = email
        this.password = password
    }


     //login
     constructor(email: String?, password: String?) {
         this.email = email
         this.password = password
     }
     constructor()


 }