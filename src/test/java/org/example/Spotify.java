package org.example;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class Spotify {
    String token = "BQDiNvHKZ2jx8zKjnfnwJQ8s8SnnOPFL2Fy3UC7UPs5s9e_JChtFJfY6ZDve0e_ju_gZt44eCRuJKfHbAUgqkc7XwZoZNXh065e2z2SzlRXRFJsIC_xoyfep3n9hX9IPHvBTG27I6nfOf_DtPBmWSXYA8rdrCZVnG2SE1X4rGut4clpVhR2HWGFzIY91C95S58iQbHJee2Zx5u-72Iab1ARkXrpAKiqg-uNgFg79XXevhmqS_qrvAeeBpi-wEHBQy1abE7rFcU7ncVWLLeyUUiPDisyqiQhfW9JXR1ge2C4kCv6jSCTf7wQ8TZTeTD21nq0eGIuhUVlMcoIg-AYD";
String user_id=null;
String Snapshot_id=null;
String Track_id=null;
    @Test(priority = 1)
    public void GetCurrentUseProfile() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me ");
        user_id=response.path("id");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 2)
    public void GetUserTopItems() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/top/artists");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void GetUserProfile() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/users/"+ user_id);
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test(priority =4 )
    public void FollowPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @AfterClass
    public void UnfollowPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .delete("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers");
        response.prettyPrint();
        response.then().statusCode(200);

    }

    @Test(priority = 5)
    public void GetFollowedArtists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/me/following?type=artist");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test(priority = 6)
    public void followArtistsOrUsers() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2CIMQHirSU0MQqyYHq0eOx\",\n" +
                        "        \"57dN52uHvrHOxijzpIgu3E\",\n" +
                        "        \"1vCWHaC5f2uS3yhpwWbIA6\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type=artist");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 204);

    }

    @AfterClass
    public void unfollowArtistsOrUsers() {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete("https://api.spotify.com/v1/me/following?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6");

        response.prettyPrint();


        assertEquals(response.getStatusCode(), 204);
    }

    @Test(priority = 7)
    public void checkIfUserFollowsArtistsOrUsers() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6");

        response.prettyPrint();


        assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 8)
    public void CheckIfCurrentUserFollowsPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers/contains");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    //----------------------------Tracks--------------------------------------------
    @Test(priority = 10)
    public void GetTrack() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl");
        Track_id=response.path("id");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 11)
    public void GetSeveralTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 12)
    public void GetUserSavedTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/tracks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 13)
    public void SaveTracksforCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @AfterClass
    public void RemoveUserSavedTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 14)
    public void checkUserSavedTracks() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/tracks/contains?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B");

        response.prettyPrint();


        assertEquals(response.getStatusCode(), 200);


    }

    @Test(priority = 15)
    public void GetSeveralTracksAudioFeatures() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audio-features?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");
        response.prettyPrint();


        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 16)
    public void GetTrackAudioFeatures() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .pathParam("id",Track_id)
                .when()
                .get("https://api.spotify.com/v1/audio-features/{id}");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 17)
    public void GetTrackAudioAnalysis() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/11dFghVXANMlKmJXsNCbNl");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 18)
    public void GetRecommendations() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/recommendations?seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical%2Ccountry&seed_tracks=0c6xIDDpzE81m2q797ordA");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    //----------------------------------------Search----------------------------------------------------------------------
    @Test(priority = 19)
    public void SearchForItem() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/search?q=remaster%2520track%3ADoxy%2520artist%3AMiles%2520Davis&type=album");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    //-----------------------------------------Markets---------------------------------------------------------------
    @Test(priority = 20)
    public void GetAvailableMarkets() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/markets");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    //-------------------------------------------------------Genres-------------------------------------------------
    @Test(priority = 21)
    public void GetAvailableGenreSeeds() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);

    }

    //---------------------------------------------Chapters-----------------------------------------------------------------
    @Test(priority = 22)
    public void getAChapter() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/chapters/0D5wENdkdwbqlrHoaJ9g29");

        response.prettyPrint();
    }

    @Test(priority = 23)
    public void GetSeveralChapters() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/chapters?ids=0IsXVP0JmcB2adSE338GkK%2C3ZXb8FKZGU0EHALYX6uCzU%2C0D5wENdkdwbqlrHoaJ9g29");

        response.prettyPrint();
    }
//----------------------------------------Categories-------------------------------------------------------------------

    @Test(priority = 24)
    public void GetSeveralBrowseCategories() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/categories ");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 25)
    public void GetSingleBrowseCategory() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner");
        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);

    }

    //-------------------------------------Artists------------------------------------------------------------------------
    @Test(priority = 26)
    public void GetArtist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg ");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 27)
    public void GetSeveralArtists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx,57dN52uHvrHOxijzpIgu3E,1vCWHaC5f2uS3yhpwWbIA6");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 28)
    public void GetArtistAlbums() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 29)
    public void GetArtistTopTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 30)
    public void GetArtistRelatedArtists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/related-artists");

        response.prettyPrint();

        assertEquals(response.getStatusCode(), 200);
    }

    //------------------------------------Shows------------------------------------------------------
    @Test(priority = 31)
    public void GetShow() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/shows/0jCWG5oU6BvRtlLwusgLv5");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 32)
    public void GetSeveralShows() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/shows?ids=0jCWG5oU6BvRtlLwusgLv5");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 33)
    public void GetShowEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/shows/0jCWG5oU6BvRtlLwusgLv5/episodes");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 34)
    public void GetUserSavedShows() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/shows");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 35)
    public void SaveShowsforCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/shows?ids=5CfCWKI5pZ28U0uOzXkDHe%2C5as3aKmN2k11yfDDDSrvaZ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @AfterClass
    public void RemoveUserSavedShows() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete("https://api.spotify.com/v1/me/shows?ids=6ZpMKmwPn7iGuneYkzU7tW");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 36)
    public void CheckUserSavedShows() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/shows/contains?ids=6ZpMKmwPn7iGuneYkzU7tW");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }

    //-------------------------------Playlist-------------------------------------------------------
    @Test(priority = 37)
    public void GetPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/playlists/7wyB2rMcdSR28li9ZIsnCu");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 38)
    public void ChangePlaylistDetails() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \"Anwar  Playlist\",\n" +
                        "    \"description\": \"welcome\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/7wyB2rMcdSR28li9ZIsnCu");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 39)
    public void GetPlaylistItems() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/playlists/2QFkzW6jIMP6ptmiB13xNC/tracks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 40)
    public void UpdatePlaylistItems() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"range_start\": 1,\n" +
                        "    \"insert_before\": 3,\n" +
                        "    \"range_length\": 2\n" +
                        "}")
                .put("https://api.spotify.com/v1/playlists/2zQATSNOavUgu0Vt2aYOo6/tracks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 41)
    public void AddItemstoPlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:31DFOzgOdnouxpEMkGxqLj\"\n" +
                        "    ],\n" +
                        "    \"position\": 2\n" +
                        "}")
                .post("https://api.spotify.com/v1/playlists/2zQATSNOavUgu0Vt2aYOo6/tracks");
        Snapshot_id=response.path("snapshot_id");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 201);
    }

    @AfterClass
    public void RemovePlaylistItems() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\": \"spotify:track:31DFOzgOdnouxpEMkGxqLj\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"snapshot_id\": \"AAAAEJTAA6dXcTzHOg51zE2r3ZitqCtR\"\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/2zQATSNOavUgu0Vt2aYOo6/tracks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 42)
    public void GetCurrentUserPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/me/playlists ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 43)
    public void GetUserPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/users/smedjan/playlists ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 44)
    public void CreatePlaylist() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"name\": \"As Playlist\",\n" +
                        "    \"description\": \"New playlist \",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/31iwkptmilixkc76obp7hzh6zu7e/playlists");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 45)
    public void GetFeaturedPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/browse/featured-playlists ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 46)
    public void GetCategoryPlaylists() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/browse/categories/dinner/playlists ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }

    //------------------------------------------Player----------------------------------------------
    @Test(priority = 47)
    public void GetPlaybackState() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/me/player ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 204);

    }

    /*@Test
    public void TransferPlayback() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"device_ids\": [\n" +
                        "        \"74ASZWbe4lXaubB36ztrGX\"\n" +
                        "    ]\n" +
                        "}")
                .put("https://api.spotify.com/v1/me/player ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }*/

    @Test(priority = 48)
    public void GetAvailableDevices() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/me/player/devices ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 49)
    public void GetCurrentlyPlayingTrack() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get("https://api.spotify.com/v1/me/player/currently-playing  ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 204);
    }

    /*@Test
    public void StartResumePlayback() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"context_uri\": \"spotify:album:5ht7ItJgpBH7W6vJ5BqpPr\",\n" +
                        "    \"offset\": {\n" +
                        "        \"position\": 5\n" +
                        "    },\n" +
                        "    \"position_ms\": 0\n" +
                        "}")
                .put("https://api.spotify.com/v1/me/player/play ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void PausePlayback() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/player/pause ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void SkipToNext() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .post("https://api.spotify.com/v1/me/player/next   ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void SkipToPrevious() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .post("https://api.spotify.com/v1/me/player/next   ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void SeekToPosition() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .put("https://api.spotify.com/v1/me/player/seek?position_ms=25000   ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void SetRepeatMode() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .put("https://api.spotify.com/v1/me/player/repeat?state=context  ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }*/
    //------------------------------------- EPISODE--------------------------------------------------

    @Test(priority = 50)
    public void GetEpisode() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/episodes/7A3UuJPswhbYJ2JDEq88Gx ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 51)
    public void GetSeveralEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/episodes?ids=77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 52)
    public void GetUserSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/episodes ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 53)
    public void SaveEpisodesforCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"77o6BIVlYM3msb4MMIL1jH\",\n" +
                        "        \"0Q86acNRm6V9GYx55SXKwf\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/episodes");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @AfterClass
    public void RemoveUserSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"77o6BIVlYM3msb4MMIL1jH\",\n" +
                        "        \"0Q86acNRm6V9GYx55SXKwf\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/episodes");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 54)
    public void CheckUserSavedEpisodes() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/episodes/contains?ids=77o6BIVlYM3msb4MMIL1jH,0Q86acNRm6V9GYx55SXKwf");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    //-----------------------------------AudioBook--------------------------------------------------
   /* @Test
    public void GetAudiobook() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/4A5iWnXCpEB6IFlFBgqSKu");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }*/

    @Test(priority = 55)
    public void GetSeveralAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }

    /*@Test
    public void GetAudiobookChapters() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/audiobooks/7iHfbu1YPACw6oZPAFJtqe/chapters");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }*/

    @Test(priority = 56)
    public void GetUserSavedAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/audiobooks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 57)
    public void SaveAudiobooksforCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .put("https://api.spotify.com/v1/me/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @AfterClass
    public void RemoveUserSavedAudiobooks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .delete("https://api.spotify.com/v1/me/audiobooks?ids=18yVqkdbdRvS24c0Ilj2ci,1HGw3J3NxZO1TP1BTtVhpZ,7iHfbu1YPACw6oZPAFJtqe");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);

    }

    //-----------------------------Albums--------------------------------------------------------------
    @Test(priority = 58)
    public void GetAlbum() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 59)
    public void GetSeveralAlbums() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 60)
    public void GetAlbumTracks() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 61)
    public void GetUserSavedAlbums() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/albums");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 62)
    public void SaveAlbumsforCurrentUser() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }
    @AfterClass
    public void RemoveUsersSavedAlbums() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/albums?ids=382ObEPsp2rxGrnsizN5TX,1A2GTWGtFfWp7KSQTwWOyo,2noRn2Aes5aoNVsU6iWThc");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }
    @Test(priority = 63)
    public void CheckUserSavedAlbums() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/me/albums/contains?ids=4jYN9icTE1NDqCSE0YvHVB");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }
    @Test(priority = 64)
    public void GetNewReleases() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .when()
                .get("https://api.spotify.com/v1/browse/new-releases ");
        response.prettyPrint();
        assertEquals(response.getStatusCode(), 200);
    }
}