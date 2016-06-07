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
        DB_Support db_support = new DB_Support(connection);
        String first, last;
        first = firstName.getText();
        last = lastName.getText();
        firstName.clear();
        lastName.clear();
        reviewNoteArea.setPromptText("Enter employee review notes here (500 max characters)");

        boolean bool = db_support.doesEmpExist(first,last);
        if(bool)
        {
            int empID = db_support.queryEmpID(first,last);

            int ratingNum = (int) ratingCombo.getSelectionModel().getSelectedItem();
            ratingCombo.setValue("");

            LocalDate revDate = reviewDatePicker.getValue();
            reviewDatePicker.getEditor().clear();
            Date reviewDate = Date.valueOf(revDate);

            String revNotes = reviewNoteArea.getText();
            reviewNoteArea.clear();

            insertReviewTable(empID,ratingNum,reviewDate,revNotes,connection);
            connection.close();
        }else{
            db_support.generateWarning();
        }
    }

    private void insertReviewTable(final int theID, final int theRatingNum,
                                    final Date theReviewDate,
                                   final String theReviewNotes,
                                   Connection theConnection) throws SQLException
    {
        String insertTable = "INSERT INTO sembab.REVIEW"
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
