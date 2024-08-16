package org.example;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
public class Json_ {
    @Test(priority = 1)
    public void postData(){
        Response response=given()
                .header("Content-Type", "application/json")
                .header("Accept","*/*")
                .body("{\n" +
                        "        \"id\": \"7\",\n" +
                        "        \"name\": \"Srafaraj\",\n" +
                        "        \"Salary\": \"40000\"\n" +
                        "    }")
                .when()
                .post("http://localhost:3000/employees");
        response.prettyPrint();
        response.then().statusCode(201);
    }
    @Test(priority = 2)
    public void getData(){
        Response response=given()
         .header("Content-Type", "application/json")
                .header("Accept","*/*")
                .when()
                .get("http://localhost:3000/employees");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 3)
    public void UpdateData(){
        Response response=given()
                .header("Content-Type", "application/json")
                .header("Accept","*/*")
                .body("{\n" +
                        "        \"id\": \"7\",\n" +
                        "        \"name\": \"Sarfaraj\",\n" +
                        "        \"Salary\": \"50000\"\n" +
                        "    }")
                .when()
                .put("http://localhost:3000/employees/7");
        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 4)
    public void DeleteData(){
        Response response=given()
                .header("Content-Type", "application/json")
                .header("Accept","*/*")
                .when()
                .delete("http://localhost:3000/employees/7");
        response.prettyPrint();
        response.then().statusCode(200);
    }
}
