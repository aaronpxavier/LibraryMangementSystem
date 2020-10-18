package com.smoothstack.lms.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AccessCredentials {
    private String dbUser;
    private String dbHost;
    private String dbPass;
    private String dbPort;
    private String apiKey;
    private final static String NEW_LINE = System.getProperty("line.separator");
    public AccessCredentials() {
        try (FileReader fileReader = new FileReader("src/main/java/com/smoothstack/lms/service/secret.json")){
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(fileReader);
            dbUser = (String) jsonObject.get("dbUser");
            dbHost = (String) jsonObject.get("dbHost");
            dbPass = (String) jsonObject.get("dbPass");
            dbPort = (String) jsonObject.get("dbPort");
            apiKey = (String) jsonObject.get("apiKey");
        } catch ( ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.join(
                NEW_LINE,
                "DBCredentials:",
                "DB User: " + dbUser,
                "DB Host: " + dbHost,
                "DB Pass: " + dbPass,
                "DB Port: " + dbPort
        );
    }

    public String getDbUser() { return dbUser; }

    public String getDbPass () { return dbPass; }

    public String getDbHost() { return  dbHost; }

    public String getDbPort() { return dbPort; }

    public String getApiKey() { return apiKey; }
}
