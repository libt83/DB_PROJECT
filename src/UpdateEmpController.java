import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Controller class that handles user input for updating employee information<br>
 * and modifications to the database model
 * Created by B on 5/31/16.
 */
public class UpdateEmpController
{
    private MYSQL_Connection dbConnect;

    private Connection connection;

    private DB_Support db_support;

    private ObservableList<String> state = FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
                                                                                 "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
                                                                                 "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
                                                                                 "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
                                                                                 "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
                                                                                 "VT", "VA", "WA", "WV", "WI", "WY");

    private ObservableList<String> compensation = FXCollections.observableArrayList("wage", "salary");

    private ObservableList<String> status = FXCollections.observableArrayList("full-time", "part-time", "temporary");

    @FXML
    private TextField empIDText;

    @FXML
    private CheckBox firstNameCheck;

    @FXML
    private TextField firstNameText;

    @FXML
    private CheckBox lastNameCheck;

    @FXML
    private TextField lastNameText;

    @FXML
    private CheckBox addressCheck;

    @FXML
    private CheckBox stateCheck;

    @FXML
    private CheckBox zipCheck;

    @FXML
    private CheckBox statusCheck;

    @FXML
    private CheckBox emailCheck;

    @FXML
    private CheckBox phoneCheck;

    @FXML
    private CheckBox compCheck;

    @FXML
    private ComboBox compCombo;

    @FXML
    private TextField compText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField zipText;

    @FXML
    private TextField phoneText;

    @FXML
    private ComboBox stateCombo;

    @FXML
    private ComboBox statusCombo;

    @FXML
    private Button updateBtn;


    @FXML
    private void initialize()
    {
        // initialize payType combo box
//        compCombo.setValue("sdfdsf");
        compCombo.setItems(compensation);

        // initialize state combo box
//        stateCombo.getEditor().clear();
        stateCombo.setItems(state);

        // initialize status combo box
//        statusCombo.getEditor().clear();
        statusCombo.setItems(status);
    }

    /**
     * Updates information related to the employee.<br>
     * This will result in changes being made within the EMPLOYEE, ADDRESS,<br>
     * and/or SALARY/NON_SALARY tables in the database.
     */
    public void updateEmpInfo() throws SQLException
    {
        dbConnect = new MYSQL_Connection();
        connection = dbConnect.getConnection();
        db_support  = new DB_Support(connection);
        int empID;
        boolean empExist;

        if(empIDText.getText().equals(""))
            db_support.customWarning("EMPLOYEE ID", "A value must be given for employee ID");
        else
        {
            empID = Integer.parseInt(empIDText.getText());
            empExist = db_support.doesEmpIDExist(empID);
            empIDText.clear();

            if(empExist == false)
            {
                db_support.generateWarning();
            }
            else
            {
                if(firstNameCheck.isSelected() && lastNameCheck.isSelected())
                {
                    if(firstNameText.getText().equals("") || lastNameText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input first and last name");
                    }
                    else
                    {
                        String first, last;
                        first = firstNameText.getText();
                        last = lastNameText.getText();
                        firstNameText.clear();
                        lastNameText.clear();
                        firstNameCheck.setSelected(false);
                        lastNameCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET firstName = ?, lastName = ?"
                                             + "WHERE empID = " + empID;


                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1,first);
                        pStmt.setString(2,last);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }else if(firstNameCheck.isSelected())
                {
                    if(firstNameText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input a first name");
                    }
                    else
                    {
                        String first;
                        first = firstNameText.getText();
                        firstNameText.clear();
                        lastNameCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET firstName = ?"
                                             + "WHERE empID = " + empID;


                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1,first);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }else if(lastNameCheck.isSelected())
                {
                    if(lastNameText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input a last name");
                    }
                    else
                    {
                        String last;
                        last = lastNameText.getText();
                        lastNameText.clear();
                        lastNameCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET lastName = ?"
                                             + "WHERE empID = " + empID;


                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1,last);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }

                if(emailCheck.isSelected())
                {
                    if(emailText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input an email address");
                    }
                    else
                    {
                        String email;
                        email = emailText.getText();
                        emailText.clear();
                        emailCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET email = ?"
                                             + "WHERE empID = " + empID;


                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1,email);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }
                if(phoneCheck.isSelected())
                {
                    if (phoneText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input a phone number");
                    }
                    else
                    {
                        String phone;
                        phone = phoneText.getText();
                        phoneText.clear();
                        phoneCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET phoneNum = ?"
                                             + "WHERE empID = " + empID;


                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1, phone);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }

                if(statusCheck.isSelected())
                {
                    if (statusCombo.getSelectionModel().isEmpty())
                    {
                        db_support.customWarning("BLANK SELECTION", "User must select a status");
                    }
                    else
                    {
                        String status = (String) statusCombo.getSelectionModel().getSelectedItem();
                        statusCombo.setValue("");
                        statusCheck.setSelected(false);
                        String insertTable = "UPDATE semba_brandon_db.EMPLOYEE SET empStatus = ?"
                                             + "WHERE empID = " + empID;

                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1, status);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }

                if(addressCheck.isSelected())
                {
                    if (addressText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input an address");
                    }
                    else
                    {
                        String street = addressText.getText();
                        addressText.clear();
                        addressCheck.setSelected(false);

                        String insertTable = "UPDATE semba_brandon_db.ADDRESS SET street = ?";

                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1, street);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }
                if(stateCheck.isSelected())
                {
                    if (stateCombo.getSelectionModel().isEmpty())
                    {
                        db_support.customWarning("BLANK SELECTION", "User must select a state");
                    }
                    else
                    {
                        String state = (String)stateCombo.getSelectionModel().getSelectedItem();
                        stateCombo.setValue("");
                        stateCheck.setSelected(false);
                        String street = db_support.queryEmpAddress(empID);
                        System.out.println(street);

                        String insertTable = "UPDATE semba_brandon_db.ADDRESS SET state = ? WHERE street = '"
                                             + street + "'";

                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1, state);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }

                if(zipCheck.isSelected())
                {
                    if (zipText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input a zipcode");
                    }
                    else
                    {
                        String zip = zipText.getText();
                        zipText.clear();
                        zipCheck.setSelected(false);
                        String street = db_support.queryEmpAddress(empID);

                        String insertTable = "UPDATE semba_brandon_db.ADDRESS SET zipcode = ? WHERE street = '"
                                             + street + "'";

                        PreparedStatement pStmt = connection.prepareStatement(insertTable);

                        pStmt.setString(1, zip);
                        pStmt.executeUpdate();
                        pStmt.close();
                    }
                }

                if(compCheck.isSelected())
                {
                    if (compText.getText().equals(""))
                    {
                        db_support.customWarning("BLANK TEXT FIELD", "User must input a compensation amount");
                    }else if(compCombo.getSelectionModel().isEmpty())
                    {
                        db_support.customWarning("BLANK SELECTION", "User must select a compensation type");
                    }
                    else
                    {
                        if(compCombo.getSelectionModel().getSelectedItem().equals("wage"))
                        {
                            if(db_support.doesNonSalaryExist(empID) == false)
                            {
                                double wage = Double.parseDouble(compText.getText());
                                compText.clear();
                                compCombo.setValue("");
                                compCheck.setSelected(false);

                                String insertTable = "DELETE FROM semba_brandon_db.SALARY WHERE empSalaryID = "
                                                     + empID;

                                PreparedStatement pStmt = connection.prepareStatement(insertTable);
                                pStmt.executeUpdate();

                                insertTable = "INSERT INTO semba_brandon_db.NON_SALARY (empNonSalaryID,wage)"
                                              + "VALUES(?,?)";
                                pStmt = connection.prepareStatement(insertTable);
                                pStmt.setInt(1,empID);
                                pStmt.setDouble(2,wage);
                                pStmt.executeUpdate();
                                pStmt.close();
                            }
                            else
                            {
                                double wage = Double.parseDouble(compText.getText());
                                compText.clear();
                                compCombo.setValue("");
                                compCheck.setSelected(false);

                                String insertTable = "UPDATE semba_brandon_db.NON_SALARY SET wage = ? "
                                                     + "where empNonSalaryID = " + empID;
                                PreparedStatement pStmt = connection.prepareStatement(insertTable);
                                pStmt.setDouble(1,wage);
                                pStmt.executeUpdate();
                                pStmt.close();
                            }
                        }

                        if(compCombo.getSelectionModel().getSelectedItem().equals("salary"))
                        {
                            if(db_support.doesSalaryExist(empID) == false)
                            {
                                double salary = Double.parseDouble(compText.getText());
                                compText.clear();
                                compCombo.setValue("");
                                compCheck.setSelected(false);

                                String insertTable = "DELETE FROM semba_brandon_db.NON_SALARY WHERE empNonSalaryID = "
                                                     + empID;

                                PreparedStatement pStmt = connection.prepareStatement(insertTable);
                                pStmt.executeUpdate();

                                insertTable = "INSERT INTO semba_brandon_db.SALARY (empSalaryID,salary)"
                                              + "VALUES(?,?)";
                                pStmt = connection.prepareStatement(insertTable);
                                pStmt.setInt(1,empID);
                                pStmt.setDouble(2,salary);
                                pStmt.executeUpdate();
                                pStmt.close();
                            }
                            else
                            {
                                double salary = Double.parseDouble(compText.getText());
                                compText.clear();
                                compCombo.setValue("");
                                compCheck.setSelected(false);

                                String insertTable = "UPDATE semba_brandon_db.SALARY SET salary = ? "
                                                     + "where empSalaryID = " + empID;
                                PreparedStatement pStmt = connection.prepareStatement(insertTable);
                                pStmt.setDouble(1,salary);
                                pStmt.executeUpdate();
                                pStmt.close();
                            }
                        }
                    }
                }
            }
        }
        connection.close();
    }
}
