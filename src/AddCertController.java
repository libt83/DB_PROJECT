import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by B on 5/31/16.
 */
public class AddCertController
{
    private MYSQL_Connection dbConnect;

    private DB_Queries dbQueries;

    private ObservableList<String> certList = FXCollections.observableArrayList("work visa","food handler","alcohol serving",
                                                                                "first/aid/CPR","AIARE level I","AIARE level II",
                                                                                "AIARE level III");

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker expDate;

    @FXML
    private ComboBox certCombo;

    @FXML
    private Button AddCertBtn;

    @FXML
    private void initialize()
    {
        // initialize certification combo box
        certCombo.getEditor().clear();
        certCombo.setItems(certList);
    }

    public void addCertToDB() throws SQLException
    {
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();
        dbQueries = new DB_Queries(connection);

        String first,last;
        first = firstName.getText();
        firstName.clear();

        last = lastName.getText();
        lastName.clear();
        boolean bool = dbQueries.doesEmpExist(first, last);

        if(bool)
        {
            int empID = dbQueries.queryEmpID(first,last);
            String certName = (String) certCombo.getSelectionModel().getSelectedItem();
            int certID = queryCertID(certName,connection);
            LocalDate exp  = expDate.getValue();
            expDate.getEditor().clear();
            Date certExpDate = Date.valueOf(exp);

            insertCert(certID,empID,certExpDate,connection);
        }else{
            dbQueries.generateWarning();
        }
    }

    /**
     * Querys the CERT_TYPE table using the certName to retrieve the associated certID.
     *
     * @param certName - the name of the certification
     * @param connection - the connection to the databse
     * @return - the certID associated with the certName
     * @throws SQLException
     */
    private int queryCertID(final String certName, final Connection connection) throws SQLException
    {
        Statement stmt = connection.createStatement();
        System.out.println(certName);
        String query = "select certID from semba_brandon_db.CERT_TYPE where certName = '"
                       + certName + "'";

        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int certID = rs.getInt(1);
        stmt.close();
        return certID;
    }

    /**
     * Inserts the cert info, along with empID into the EMP_CERT_GLUE.
     *
     * @param certID - certification ID
     * @param empID - employee ID
     * @param expDate - certification expiration date
     * @param connection - the database connection
     * @throws SQLException
     */
    private void insertCert(final int certID, final int empID, final Date expDate, final Connection connection) throws SQLException
    {
        String insertTable = "INSERT INTO semba_brandon_db.CERT"
                             + "(certID,certExp) VALUES"
                             + "(?,?)";

        PreparedStatement pStmt = connection.prepareStatement(insertTable);
//        pStmt.setInt(1,empID);
        pStmt.setInt(1,certID);
        pStmt.setDate(2,expDate);
        pStmt.executeUpdate();
        pStmt.close();
    }

}
