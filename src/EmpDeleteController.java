import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes an employee and all related info from the database.
 *
 * Created by B on 6/3/16.
 */
public class EmpDeleteController
{
    private MYSQL_Connection dbConnect;

    private Connection connection;

    private DB_Support db_support;

    @FXML
    private TextField  firstText;

    @FXML
    private TextField lastText;

    public void deleteEmployee() throws SQLException
    {
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();
        db_support = new DB_Support(connection);

        if(firstText.getText().equals("") || lastText.getText().equals(""))
        {
            db_support.customWarning("BLANK TEXT FIELD", "User must input first and last name");
        }else if(!db_support.doesEmpExist(firstText.getText(),lastText.getText()))
        {
            db_support.customWarning("INVALID INPUT", "Employee does not exist in database");
        }
        else
        {
            int empID = db_support.queryEmpID(firstText.getText(),lastText.getText());
            String address = db_support.queryEmpAddress(empID);
            firstText.clear();
            lastText.clear();

            String insertTable = "DELETE FROM semba_brandon_db.CERT WHERE empIDcert = "
                                 + empID;

            PreparedStatement pStmt = connection.prepareStatement(insertTable);
            pStmt.executeUpdate();

            insertTable = "DELETE FROM semba_brandon_db.EMPLOYEE WHERE empID = "
                           + empID;

            pStmt = connection.prepareStatement(insertTable);
            pStmt.executeUpdate();

            insertTable = "DELETE FROM semba_brandon_db.ADDRESS WHERE street ='"
                                 + address + "'";

            pStmt = connection.prepareStatement(insertTable);
            pStmt.executeUpdate();
            pStmt.close();
            connection.close();
        }
    }
}
