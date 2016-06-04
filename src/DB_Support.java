import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Provides support to the controllers for querying the database and in generating warnings.
 *
 * Created by B on 6/1/16.
 */
public class DB_Support
{
    private Connection myConnection;

    /**
     *
     * @param theConnection
     */
    public DB_Support(final Connection theConnection)
    {
        myConnection = theConnection;
    }

    /**
     * Query database to see if the employee exists.
     *
     * @param name1 - the fist name of the employee
     * @param name2 - the last name of the employee
     * @return true if employee is in db; false otherwise
     */
    public boolean doesEmpExist(final String name1, final String name2) throws SQLException
    {
        Statement stmt  = myConnection.createStatement();
        String query = "select exists (select firstName,"
                       + "lastName from semba_brandon_db.EMPLOYEE"
                       + " where firstname = '" + name1
                       + "' and lastName = '" + name2 + "')";


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
     * Query the database to see if the employee ID exists in the database.
     *
     * @param theEmpID - the employee ID
     * @return true if the employee ID exists and false if it doe not
     * @throws SQLException
     */
    public boolean doesEmpIDExist(final int theEmpID) throws SQLException
    {
        Statement stmt  = myConnection.createStatement();
        String query = "select exists (select empID from semba_brandon_db.EMPLOYEE "
                       + "where empID = " + theEmpID + ")";


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
     * Query the database to determine if an employee has a salary.
     *
     * @param theEmpID - the employee ID
     * @return true if they have a salary; false otherwise
     * @throws SQLException
     */
    public boolean doesSalaryExist(final int theEmpID) throws SQLException
    {
        Statement stmt  = myConnection.createStatement();
        String query = "select exists (select empSalaryID from semba_brandon_db.SALARY "
                       + "where empSalaryID = " + theEmpID + ")";


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
     * Query the database to determine if the employee has a non-salary.
     *
     * @param theEmpID - the employee ID
     * @return true if the employee has a non-salary; false otherwise
     * @throws SQLException
     */
    public boolean doesNonSalaryExist(final int theEmpID) throws SQLException
    {
        Statement stmt  = myConnection.createStatement();
        String query = "select exists (select empNonSalaryID from semba_brandon_db.NON_SALARY "
                       + "where empNonSalaryID = " + theEmpID + ")";


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

//    /**
//     * Query the database to determine if the employee has a cert.
//     * @param theEmpID - the employee ID
//     * @return
//     * @throws SQLException
//     */
//    public boolean doesCertExist(final int theEmpID) throws SQLException
//    {
//        Statement stmt  = myConnection.createStatement();
//        String query = "select exists (select empNonSalaryID from semba_brandon_db.NON_SALARY "
//                       + "where empNonSalaryID = " + theEmpID + ")";
//
//
//        ResultSet rs = stmt.executeQuery(query);
//        rs.next();
//        int result = rs.getInt(1);
//        stmt.close();
//
//        if(result == 0)
//        {
//            return false;
//        }else{
//            return true;
//        }
//    }

    /**
     * Query the database for a specific employee ID.
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
     * Query the database for a specific employee address.
     *
     * @param theEmpID - the employee ID
     * @return the employee's address
     * @throws SQLException
     */
    public String queryEmpAddress(final int theEmpID) throws SQLException
    {
        Statement stmt = myConnection.createStatement();

        String query = "select street from semba_brandon_db.EMPLOYEE where empID =" + theEmpID;

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        String empStreet = rs.getString(1);
        stmt.close();
        return empStreet;
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
     * Generates a new custom warning.
     *
     * @param title - the new warning title
     * @param header - the new warning header
     */
    public void customWarning(final String title, final String header)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR - " + title);
        alert.setHeaderText(header);
        alert.setContentText("Please try again");
        alert.showAndWait();
    }

    /**
     * Closes the database connection.
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException
    {
        myConnection.close();
    }
}
