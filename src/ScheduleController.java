import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by B on 5/31/16.
 */
public class ScheduleController
{
    private MYSQL_Connection dbConnect;

    private DB_Queries dbQueries;

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
     * Generates a weekly schedule for a specified employee and inserts that information<br>
     * into the SCHEDULE database.
     */
    public void insertSchedule() throws SQLException
    {
        String first,last,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
        dbConnect = new MYSQL_Connection();
        Connection connection = dbConnect.getConnection();
        dbQueries = new DB_Queries(connection);

        first = firstName.getText();
        firstName.clear();

        last = lastName.getText();
        lastName.clear();
        boolean bool = dbQueries.doesEmpExist(first, last);

        if(bool)
        {
            LocalDate start = startDate.getValue();
            startDate.getEditor().clear();
            Date startDate = Date.valueOf(start);

            LocalDate end = endDate.getValue();
            endDate.getEditor().clear();
            Date endDate = Date.valueOf(end);

            int intMon,intTue,intWed,intThur,intFri,intSat,intSun;
            monday = mon.getText();
            mon.clear();
            if(monday.equals(""))
            {
                intMon = Integer.parseInt("0");
            }else{
                intMon = Integer.parseInt(monday);
            }

            tuesday = tue.getText();
            tue.clear();
            if(tuesday.equals(""))
            {
                intTue = Integer.parseInt("0");
            }else{
                intTue = Integer.parseInt(monday);
            }

            wednesday = wed.getText();
            wed.clear();
            if(wednesday.equals(""))
            {
                intWed = Integer.parseInt("0");
            }else{
                intWed = Integer.parseInt(monday);
            }

            thursday = thur.getText();
            thur.clear();
            if(thursday.equals(""))
            {
                intThur = Integer.parseInt("0");
            }else{
                intThur = Integer.parseInt(monday);
            }

            friday = fri.getText();
            fri.clear();
            if(friday.equals(""))
            {
                intFri = Integer.parseInt("0");
            }else{
                intFri = Integer.parseInt(monday);
            }

            saturday = sat.getText();
            sat.clear();
            if(saturday.equals(""))
            {
                intSat = Integer.parseInt("0");
            }else{
                intSat = Integer.parseInt(monday);
            }

            sunday = sun.getText();
            sun.clear();
            if(sunday.equals(""))
            {
                intSun = Integer.parseInt("0");
            }else{
                intSun = Integer.parseInt(monday);
            }

            int empID = dbQueries.queryEmpID(first, last);
            insertSchedule(empID,startDate,endDate,intMon,intTue,intWed,
                           intThur,intFri,intSat,intSun,connection);
            connection.close();
        }else{
            dbQueries.generateWarning();
        }
    }

    /**
     * Inserts a schedule into the SCHEDULE table of the database.
     *
     * @param empID - the employee ID
     * @param start - the start date
     * @param end - the end date
     * @param mon - monday
     * @param tue - tuesday
     * @param wed - wednesday
     * @param thur - thursday
     * @param fri - friday
     * @param sat - saturday
     * @param sun - sunday
     * @param connection - the connection to the database
     * @throws SQLException
     */
    private void insertSchedule(final int empID, final Date start, final Date end, final int mon,
                                final int tue, final int wed, final int thur, final int fri,
                                final int sat, final int sun, final Connection connection) throws SQLException
    {
        String insertTable = "INSERT INTO semba_brandon_db.SCHEDULE"
                             + "(empSchedID,startDate,endDate,mon,tue,wed,thur,"
                             + "fri,sat,sun) VALUES"
                             + "(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = connection.prepareStatement(insertTable);
        pStmt.setInt(1,empID);
        pStmt.setDate(2,start);
        pStmt.setDate(3,end);
        pStmt.setInt(4,mon);
        pStmt.setInt(5,tue);
        pStmt.setInt(6,wed);
        pStmt.setInt(7,thur);
        pStmt.setInt(8,fri);
        pStmt.setInt(9,sat);
        pStmt.setInt(10,sun);
        pStmt.executeUpdate();
        pStmt.close();
    }
}
