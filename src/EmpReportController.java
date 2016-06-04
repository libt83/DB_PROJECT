import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by B on 6/3/16.
 */
public class EmpReportController
{
    private MYSQL_Connection dbconnect;

    private ObservableList<ObservableList> data;

    @FXML
    private TableView tableView;

    /**
     * Adds the data from the EMPLOYEE table into the TableView<br>
     * in order to be displayed on the GUI.
     *
     * @throws SQLException
     */
    public void generateTable() throws SQLException
    {
            dbconnect = new MYSQL_Connection();
            Connection connection = dbconnect.getConnection();
            data = FXCollections.observableArrayList();

            String query = "SELECT * from semba_brandon_db.EMPLOYEE natural join semba_brandon_db.POSITION";

            ResultSet rs = connection.createStatement().executeQuery(query);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableView.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }
            tableView.setItems(data);
    }
}
