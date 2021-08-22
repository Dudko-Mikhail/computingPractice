package by.epam.computingPractice.googleSheets.DAO;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpreadsheetsDAO {
    private static final String APPLICATION_NAME = "Google Sheets API Java";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private Sheets sheetsService;

    public SpreadsheetsDAO() throws IOException {
        this.sheetsService = getSheetsService();
    }

    public void readSpreadsheet(String spreadsheetID, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetID, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                System.out.println(row);
            }
        }
    }

    public List<List<Object>> getSpreadsheetData(String spreadsheetID, String range) throws IOException {
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetID, range)
                .execute();
        return response.getValues();
    }

    public void writeData(String spreadsheetID, String range, List<List<Object>> data) throws IOException {
        ValueRange content = new ValueRange().setValues(data);
        sheetsService.spreadsheets().values()
                .append(spreadsheetID, range, content)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    public void updateRows(String spreadsheetID, String range, List<List<Object>> data) throws IOException {
        ValueRange content = new ValueRange().setValues(data);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetID, range, content)
                .setValueInputOption("RAW")
                .execute();
    }

    public void deleteRows(String spreadsheetID, int sheetID, int startIndex, int endIndex) throws IOException {
        DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                .setRange(
                        new DimensionRange()
                            .setSheetId(sheetID)
                            .setDimension("ROWS")
                            .setStartIndex(startIndex)
                            .setEndIndex(endIndex)
                );
        List<Request> requests = new ArrayList<>();
        requests.add(new Request().setDeleteDimension(deleteRequest));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(spreadsheetID, body).execute();
    }

    private static Credential getCredentials() throws IOException {
        // Load client secrets.
        InputStream in = SpreadsheetsDAO.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
        return credential;
    }

    private static Sheets getSheetsService() throws IOException {
        Credential credential = getCredentials();
        return new Sheets.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }
}
