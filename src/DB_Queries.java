import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by B on 6/1/16.
 */
public class DB_Queries
{
    private Connection myConnection;

    /**
     *
     * @param theConnection
     */
    public DB_Queries(final Connection theConnection)
    {
        myConnection = theConnection;
    }

    /**
     * Queries database to see if the employee exists.
     *
     * @param firstName - the fist name of the employee
     * @param lastName - the last name of the employee
     * @return true if employee is in db; false otherwise
     */
    public boolean doesEmpExist(final String firstName, final String lastName) throws SQLException
    {
        Statement stmt  = myConnection.createStatement();
        String query = "select exists (select firstName,"
                       + " lastName from semba_brandon_db.EMPLOYEE "
                       + "where firstName = '" + firstName
                       + "' and lastName = '" + lastName + "')";


        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int result = rs.getInt(1);
        stmt.close();

        if(result == 0)
        {
            return false;
        }else{
           return true;
        }

    }

    /**
     * Generates a warning when employee entered does not exist in database.
     */
    public void generateWarning()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR - INVALID INPUT");
        alert.setHeaderText("The employee entered does not exist");
        alert.setContentText("Please try again");
        alert.showAndWait();
    }

    /**
     *
     * @param firstName
     * @param lastName
     * @return
     * @throws SQLException
     */
    public int queryEmpID(final String firstName, final String lastName) throws SQLException
    {
        Statement stmt = myConnection.createStatement();

        String query = "select empID from semba_brandon_db.EMPLOYEE where firstName = '"
                       + firstName + "' and lastName = '" + lastName + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int employeeID = rs.getInt("empID");
        stmt.close();
        return employeeID;
    }

    /**
     * closes the database connection.
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException
    {
        myConnection.close();
    }
}
