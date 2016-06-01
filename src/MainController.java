import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SubScene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private MenuItem empReview;

    @FXML
    private SubScene mainSubscene;

    /**
     * loads the addEmployeeView into the stage.<br>
     *
     * @throws IOException
     */
    public void addEmployeeView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("addEmployeeView.fxml")));
        mainSubscene.setHeight(600.00);
        mainSubscene.setWidth(550.00);
        Stage theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setMinHeight(640);
        theStage.setMinWidth(550);
        theStage.centerOnScreen();
    }

    /**
     * loads the createReviewView into the stage. <br>
     *
     * @throws IOException
     */
    public void createReviewView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("reviewView.fxml")));
        mainSubscene.setHeight(580);
        mainSubscene.setWidth(550);
        Stage theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setMinHeight(640);
        theStage.setMinWidth(600);
        theStage.centerOnScreen();
    }

    /**
     * loads the updateEmpView into the stage. <br>
     *
     * @throws IOException
     */
    public void updateEmpView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("updateEmpView.fxml")));
        mainSubscene.setHeight(580);
        mainSubscene.setWidth(550);
        Stage theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setMinHeight(640);
        theStage.setMinWidth(600);
        theStage.centerOnScreen();
    }
    /**
     *  exits the program.
     */
    public void exitProgram()
    {
        System.exit(0);
    }

}


