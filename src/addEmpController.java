import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;


/**
 * Created by B on 5/10/16.
 */
public class AddEmpController
{
    /** creates a connection to the database */
    private MYSQL_Connection dbConnect;

    private ObservableList<String> jobPositions = FXCollections.observableArrayList("cashier", "host/hostess",
                                                                            "busser", "waiter/waitress",
                                                                            "bartender", "chef", "line cook",
                                                                            "instructor I", "instructor II",
                                                                            "instructor III", "patrol I",
                                                                            "patrol II", "patrol III",
                                                                            "chairlift operator", "maintenance",
                                                                            "equipment repair", "rental staff",
                                                                            "shuttle driver", "parking lot attendent",
                                                                            "snowmaking staff", "concierge");

    private ObservableList<String> wageSalary = FXCollections.observableArrayList("wage", "salary");

    private ObservableList<String> status = FXCollections.observableArrayList("full-time", "part-time", "temporary");

    private ObservableList<String> genderList = FXCollections.observableArrayList("M", "F");

    private ObservableList<String> stateAbbr = FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA","CO","CT","DE",
                                                                         "FL","GA","HI","ID","IL","IN","IA","KS","KY",
                                                                         "LA","ME","MD","MA","MI","MN","MS","MO","MT",
                                                                         "NE","NV","NH","NJ","NM","NY","NC","ND","OH",
                                                                         "OK","OR","PA","RI","SC","SD","TN","TX","UT",
                                                                         "VT","VA","WA","WV","WI","WY");

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addrField;

    @FXML
    private TextField zipField;

    @FXML
    private TextField nationField;

    @FXML
    private TextField payField;

    @FXML
    private ComboBox positionBox;

    @FXML
    private ComboBox payBox;

    @FXML
    private ComboBox genderBox;

    @FXML
    private ComboBox stateBox;

    @FXML
    private ComboBox empStatusBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private DatePicker hireDatePicker;

    @FXML
    private Button submitBtn;

    @FXML
    private void initialize()
    {
        // initiliaze job position combo box
        positionBox.setValue(" ");
        positionBox.setItems(jobPositions);

        // initialize pay combo box
        payBox.setValue(" ");
        payBox.setItems(wageSalary);

        // initialize gender combo box
        genderBox.setValue(" ");
        genderBox.setItems(genderList);

        // initialize state abbr. combo box
        stateBox.setPromptText(" ");
        stateBox.setItems(stateAbbr);

        //initialize emp status combo box
        empStatusBox.setPromptText(" ");
        empStatusBox.setItems(status);
    }

    /**
     * Adds employee information into the EMPLOYEE, ADDRESS, SALARY , and NON_SALARY<br>
     * tables in the database when the submit button is clicked on the form.<br>
     *
     * @param e - the ActionEvent is not used
     * @throws SQLException
     * @throws ParseException
     */
    public void addEmployeeInfo(final ActionEvent e) throws SQLException, ParseException
    {
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();

        String firstName = firstNameField.getText();
        firstNameField.clear();

        String lastName = lastNameField.getText();
        lastNameField.clear();

        LocalDate dob = dobPicker.getValue();
        dobPicker.getEditor().clear();
        Date dobDate = Date.valueOf(dob);

        String gender = (String) genderBox.getSelectionModel().getSelectedItem();
        genderBox.setValue(" ");

        String phone = phoneField.getText();
        phoneField.clear();

        String email = emailField.getText();
        emailField.clear();

        String address = addrField.getText();
        addrField.clear();

        String state = (String)stateBox.getSelectionModel().getSelectedItem();
        stateBox.setValue(" ");

        String zip = zipField.getText();
        zipField.clear();
        insertAddressTable(address, state, zip, connection);

        String nationality = nationField.getText();
        nationField.clear();

        String empPosition = (String) positionBox.getSelectionModel().getSelectedItem();
        positionBox.setValue(" ");
        int positionID = queryPosID(empPosition,connection);


        String empStatus = (String) empStatusBox.getSelectionModel().getSelectedItem();
        empStatusBox.setValue(" ");

        LocalDate hire = hireDatePicker.getValue();
        hireDatePicker.getEditor().clear();

        Date hireDate = Date.valueOf(hire);

        String insertTable = "INSERT INTO semba_brandon_db.EMPLOYEE"
                             + "(posID,firstName,lastName,dob,email,street,phoneNum,"
                             + "gender,nationality,empStatus,hireDate) VALUES"
                             + "(?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = connection.prepareStatement(insertTable);

        pStmt.setInt(1,positionID);
        pStmt.setString(2,firstName);
        pStmt.setString(3,lastName);
        pStmt.setDate(4,dobDate);
        pStmt.setString(5,email);
        pStmt.setString(6,address);
        pStmt.setString(7,phone);
        pStmt.setString(8,gender);
        pStmt.setString(9,nationality);
        pStmt.setString(10,empStatus);
        pStmt.setDate(11,hireDate);
        pStmt.executeUpdate();

        // adds pay information to either the SALARY of NON_SALARY tables in database
        int employID = queryEmpID(firstName, lastName, connection);
        Double empPay = Double.parseDouble(payField.getText());
        payField.clear();


        String compenType = (String) payBox.getSelectionModel().getSelectedItem();
        payBox.setValue(" ");
        if(compenType.equals("wage"))
        {
            insertPayInfo(compenType, empPay, employID, connection);
        }else{
            insertPayInfo(compenType, empPay, employID, connection);
        }
        pStmt.close();
        connection.close();
    }

    /**
     * Private helper that inserts the address info into the ADDRESS<br>
     * prior to inserting the employee info into the EMPLOYEE table.<br>
     * This ensures that the foreign key constraint is not violated.<br>
     *
     * @param theStreet - the street address of the employee
     * @param theState - the state associate with employee address
     * @param theZip -  the zipcode associated with employee address
     * @param theConnection - the connection to the database.
     * @throws SQLException
     */
    private void insertAddressTable(final String theStreet, final String theState,
                               final String theZip, Connection theConnection) throws SQLException
    {
        String insertTable = "INSERT INTO semba_brandon_db.ADDRESS"
                             + "(street,zipcode,state) VALUES"
                             + "(?,?,?)";

        PreparedStatement pStmt = theConnection.prepareStatement(insertTable);
        pStmt.setString(1,theStreet);
        pStmt.setString(2,theZip);
        pStmt.setString(3,theState);
        pStmt.executeUpdate();
        pStmt.close();
    }

    /**
     * queries the database for the posID associated witht he position name.<br>
     * The position ID is then used for insertion in the EMPLOYEE table.
     *
     * @param thePosition - the name of the job position
     * @return posID - the position ID associated with the position name
     */
    private int queryPosID(final String thePosition, final Connection theConnection) throws SQLException
    {
        Statement stmt = theConnection.createStatement();

        String query = "select posID from semba_brandon_db.POSITION where posName = '"
                       + thePosition + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int posID = rs.getInt("posID");
        stmt.close();
        return posID;
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

    /**
     * inserts compensation information into either the SALARY or NON_SALARY tables in the database.<br>
     *
     * @param theString - the type of compensation
     * @param thePay - the amount of compensation
     * @param theEmpID - the employee ID associated with the pay info
     * @param theConnection - the connection to the database
     * @throws SQLException
     */
    private void insertPayInfo(final String theString,
                               final Double thePay, final int theEmpID,
                               final Connection theConnection) throws SQLException
    {
        String empID, tblName;
        if(theString.equals("wage"))
        {
            empID = "empNonSalaryID";
            tblName = "NON_SALARY";
        }else{
            empID = "empSalaryID";
            tblName = "SALARY";
        }

        String insertTable = "INSERT INTO semba_brandon_db." + tblName
                             + "(" + empID + ", " + theString + ") VALUES"
                             + "(?,?)";

        PreparedStatement pStmt = theConnection.prepareStatement(insertTable);
        pStmt.setInt(1,theEmpID);
        pStmt.setDouble(2,thePay);
        pStmt.executeUpdate();
        pStmt.close();
    }
}
