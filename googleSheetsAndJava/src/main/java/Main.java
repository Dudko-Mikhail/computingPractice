import by.epam.computingPractice.googleSheets.DAO.SpreadsheetsDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     * https://docs.google.com/spreadsheets/d/1Y_SJokDRBT-vULwqqKbn9yhHHG9fsvnY3Ewn_4hxSIQ/edit#gid=0
     */

    public static void main(String... args) throws IOException {
        String spreadsheetID = "1Y_SJokDRBT-vULwqqKbn9yhHHG9fsvnY3Ewn_4hxSIQ";
        int sheetID = 0;
        String range = "A1:E10";
        SpreadsheetsDAO spreadsheetsDAO = new SpreadsheetsDAO();
        List<List<Object>> data = Arrays.asList(
                Arrays.asList(
                        "first", "row", "was", "added", "from", "code"
                ),
                Arrays.asList(
                        "second", "row", "was", "added", "from", "code"
                )
        );
        List<List<Object>> update = Arrays.asList(
                Arrays.asList(
                        "this", "row", "was", "updated", "from", "code"
                )
        );
        spreadsheetsDAO.writeData(spreadsheetID, "A1", data);
        spreadsheetsDAO.readSpreadsheet(spreadsheetID, range);

        spreadsheetsDAO.updateRows(spreadsheetID,"A2", update);
        spreadsheetsDAO.readSpreadsheet(spreadsheetID, range);

        spreadsheetsDAO.deleteRows(spreadsheetID, sheetID, 2,4);
        spreadsheetsDAO.readSpreadsheet(spreadsheetID, range);

    }
}