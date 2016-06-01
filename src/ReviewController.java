import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by B on 5/31/16.
 */
public class ReviewController
{

    private MYSQL_Connection dbConnect;

    private ObservableList<Integer> ratingList = FXCollections.observableArrayList(1,2,3,4,5);

    @FXML
    private TextArea reviewNoteArea;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private ComboBox ratingCombo;

    @FXML
    private DatePicker reviewDatePicker;

    @FXML
    private Button submitBtn;

    @FXML
    private void initialize()
    {
        // initiliaze ratings combo box
        ratingCombo.setValue(" ");
        ratingCombo.setItems(ratingList);
    }


    public void insertReview() throws SQLException
    {
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();
        String first, last;
        first = firstName.getText();
        last = lastName.getText();
        firstName.clear();
        lastName.clear();

        int empID = queryEmpID(first,last,connection);

        int ratingNum = (int) ratingCombo.getSelectionModel().getSelectedItem();
        ratingCombo.getEditor().clear();

        LocalDate revDate = reviewDatePicker.getValue();
        reviewDatePicker.getEditor().clear();
        Date reviewDate = Date.valueOf(revDate);

        String revNotes = reviewNoteArea.getText();
        reviewNoteArea.setPromptText("Enter employee review notes here (500 max characters)");

        insertReviewTable(empID,ratingNum,reviewDate,revNotes,connection);
        connection.close();
    }

    /**
     * Queries the database to get the empID for a given employee's first and last name<br>
     * then returns the empID.<br>
     *
     * @param firstName - the first name of the employee
     * @param lastName - the last name of the employee
     * @param theConnection - the database connection
     * @return - the empID
     * @throws SQLException
     */
    private int queryEmpID(final String firstName, final String lastName, final Connection theConnection) throws SQLException
    {
        Statement stmt = theConnection.createStatement();

        String query = "select empID from semba_brandon_db.EMPLOYEE where firstName = '"
                       + firstName + "' and lastName = '" + lastName + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int employeeID = rs.getInt("empID");
        stmt.close();
        return employeeID;
    }

    private void insertReviewTable(final int theID, final int theRatingNum,
                                    final Date theReviewDate,
                                   final String theReviewNotes,
                                   Connection theConnection) throws SQLException
    {
        String insertTable = "INSERT INTO semba_brandon_db.REVIEW"
                             + "(empID,reviewRating,reviewDate,reviewNote) VALUES"
                             + "(?,?,?,?)";

        PreparedStatement pStmt = theConnection.prepareStatement(insertTable);
        pStmt.setInt(1,theID);
        pStmt.setInt(2,theRatingNum);
        pStmt.setDate(3,theReviewDate);
        pStmt.setString(4,theReviewNotes);
        pStmt.executeUpdate();
        pStmt.close();
    }
}
