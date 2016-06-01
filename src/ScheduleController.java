import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by B on 5/31/16.
 */
public class ScheduleController
{
    private MYSQL_Connection dbConnect;

    private DB_Queries db_queries;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField mon;

    @FXML
    private TextField sun;

    @FXML
    private TextField tue;

    @FXML
    private TextField wed;

    @FXML
    private TextField thur;

    @FXML
    private TextField fri;

    @FXML
    private TextField sat;

    @FXML
    private Button submitBtn;

    /**
     *
     */
    public void insertSchedule() throws SQLException
    {
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();
        db_queries = new DB_Queries(connection);

        String first = firstName.getText();
        firstName.clear();

        String last = lastName.getText();
        lastName.clear();

        boolean bool = db_queries.doesEmpExist(first,last);

        if(bool)
        {

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR - INVALID INPUT");
            alert.setHeaderText("The employee entered does not exist");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }

}
