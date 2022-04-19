package utilities;

import com.google.gson.JsonObject;

import java.util.Map;

public class Users {
    private static Map<String, JsonObject> usersMap;

    private static JsonDataReader jsonReader;

    public static Map<String, JsonObject> getUsers(){
        return usersMap;
    }

    public static void setUsers(Map<String, JsonObject> users){
        usersMap = users;
    }

    public static JsonObject getUser(final String userId){
        return usersMap.get(userId);
    }

    public static String getUserName(final String userID){
    return getUser(userID).get("userName").getAsString();
    }

    public static String getPassword(final String userID){
        return getUser(userID).get("password").getAsString();
    }

}
