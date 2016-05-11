import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by B on 5/10/16.
 */
public class MainController
{
    @FXML
    private Pane mainPane;

    @FXML
    private MenuBar MainMenuBar;

    @FXML
    private MenuItem quitProgram;

    @FXML
    private MenuItem addEmployee;

    @FXML
    private MenuItem updateEmployee;

    @FXML
    private MenuItem deleteEmployee;

    @FXML
    private SubScene mainSubscene;

    public void addEmployeeView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("addEmployeeView.fxml")));
        Stage theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.sizeToScene();
        theStage.centerOnScreen();

    }
}


