package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Pet_store {
String User_id=null;
    @Test(priority = 1)
    public void testCreateUser() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"username\": \"Anwar\",\n" +
                        "  \"firstName\": \"string\",\n" +
                        "  \"lastName\": \"string\",\n" +
                        "  \"email\": \"string\",\n" +
                        "  \"password\": \"Anwar123\",\n" +
                        "  \"phone\": \"string\",\n" +
                        "  \"userStatus\": 0\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/user");

        response.prettyPrint();
        response.then().statusCode(200);

    }
    @Test(priority = 2)
    public void testCreateUsersWithArray(){
        Response response = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 1,\n" +
                        "    \"username\": \"Anwar\",\n" +
                        "    \"firstName\": \"string\",\n" +
                        "    \"lastName\": \"string\",\n" +
                        "    \"email\": \"string\",\n" +
                        "    \"password\": \"Anwar123\",\n" +
                        "    \"phone\": \"string\",\n" +
                        "    \"userStatus\": 0\n" +
                        "  }\n" +
                        "]'")
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithArray");
        response.prettyPrint();
        response.then().statusCode(200);

    }
    @Test(priority = 3)
    public void testlogoutUser(){
        Response response= given()
                .header("accept","application/json")
                .when()
                .get("https://petstore.swagger.io/v2/user/logout/");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 4)
    public void testloginUser(){
        Response response=given()
                .header("accept","application/json")
                .queryParam("username", "Anwar")
                .queryParam("password", "Anwar123")
                .when()
                .get("https://petstore.swagger.io/v2/user/login");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 5)
    public void testDeleteUser(){
        Response response=given()
                .header("accept","application/json")
                .queryParam("username", "Anwar")
                .when()
                .delete("https://petstore.swagger.io/v2/user/"+User_id);
        response.prettyPrint();
    }
    @Test(priority = 6)
    public void testUpdateUser(){
        Response response=given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"username\": \"Anwar\",\n" +
                        "  \"firstName\": \"string\",\n" +
                        "  \"lastName\": \"string\",\n" +
                        "  \"email\": \"string\",\n" +
                        "  \"password\": \"Anwar1234\",\n" +
                        "  \"phone\": \"string\",\n" +
                        "  \"userStatus\": 0\n" +
                        "}")
                .when()
                .put("https://petstore.swagger.io/v2/user/Anwar");
        response.prettyPrint();
        response.then().statusCode(200);

    }
    @Test(priority = 7)
    public void testGetUserByName(){
        Response response=given()
                .header("accept", "application/json")
                .queryParam("username","Anwar")
                .when()
                .get("https://petstore.swagger.io/v2/user/Anwar");
        response.prettyPrint();
        response.then().statusCode(200);

    }
    @Test(priority = 7)
            public void testcreateUsersWithListInput() {
        Response response = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": 2,\n" +
                        "    \"username\": \"Anwar\",\n" +
                        "    \"firstName\": \"string\",\n" +
                        "    \"lastName\": \"string\",\n" +
                        "    \"email\": \"string\",\n" +
                        "    \"password\": \"Anwar1234\",\n" +
                        "    \"phone\": \"string\",\n" +
                        "    \"userStatus\": 0\n" +
                        "  }\n" +
                        "]")
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithList");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    //.............................Store..............................................

    @Test(priority = 8)
    public void testPlaceOrder(){
        Response response=given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"petId\": 0,\n" +
                        "  \"quantity\": 0,\n" +
                        "  \"shipDate\": \"2024-08-12T09:04:50.834Z\",\n" +
                        "  \"status\": \"placed\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/store/order");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 9)
    public void testgetOrderById(){
        Response response=given()
                .header("accept","application/json")
                .queryParam("orderId ", "1")
                .when()
                .get("https://petstore.swagger.io/v2/store/order/1");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 10)
    public void testGetInventory(){
        Response response=given()
                .header("accept","application/json")
                .when()
                .get("https://petstore.swagger.io/v2/store/inventory");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @AfterClass
    public void testdeleteOrder(){
        Response response=given()
                .header("accept","application/json")
                .when()
                .delete("https://petstore.swagger.io/v2/store/order/1");
        response.prettyPrint();
    }
//----------------------------------pet--------------------------------------------
    @Test(priority = 11)
public void testAddPet(){
        Response response=given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"Roy\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when()
                .post("https://petstore.swagger.io/v2/pet");
        response.prettyPrint();
        response.then().statusCode(200);

        }
        @Test(priority = 12)
    public void testgetPetById(){
        Response response=given()
                .header("accept","application/json")
                .when()
                .get("https://petstore.swagger.io/v2/pet/1");
        response.prettyPrint();
            response.then().statusCode(200);

        }
        @Test(priority = 13)
    public void testFindPetsByStatus(){
            Response response=given()
                    .header("accept","application/json")
                    .when()
                    .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available");
            response.prettyPrint();
            response.then().statusCode(200);
        }
        @Test(priority = 14)
    public void testUpdatePetWithForm(){
        Response response=given()
                .header("accept","application/json")
                .header("Content-Type"," application/x-www-form-urlencoded")
                .body("name=Zack&status=Avaliable")
                .when()
                .post("https://petstore.swagger.io/v2/pet/2");
        response.prettyPrint();
        }
        @AfterClass
    public void testDeletePet(){
        Response response=given()
                .header("accept","application/json")
                .header("Content-Type"," application/x-www-form-urlencoded")
                .when()
                .delete("https://petstore.swagger.io/v2/pet/1");
        response.prettyPrint();

        }
    }

