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
    private Stage theStage;

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

    @FXML
    private void initialize() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("mainSubScene.fxml")));
        mainSubscene.setHeight(400);
        mainSubscene.setWidth(600);
    }

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
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(660);
        theStage.setWidth(540);
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
        mainSubscene.setHeight(296);
        mainSubscene.setWidth(600);
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(340);
        theStage.setWidth(600);
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
        mainSubscene.setHeight(560);
        mainSubscene.setWidth(600);
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(580);
        theStage.setWidth(600);
        theStage.centerOnScreen();
    }

    /**
     * loads the addCertView into the stage. <br>
     *
     * @throws IOException
     */
    public void addCertView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("addCertView.fxml")));
        mainSubscene.setHeight(400);
        mainSubscene.setWidth(600);
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(470);
        theStage.setWidth(600);
        theStage.centerOnScreen();
    }

    /**
     * loads the scheduleView into the stage. <br>
     *
     * @throws IOException
     */
    public void scheduleView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("scheduleView.fxml")));
        mainSubscene.setHeight(300);
        mainSubscene.setWidth(600);
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(350);
        theStage.setWidth(600);
        theStage.centerOnScreen();
    }

    /**
     * loads the scheduleView into the stage. <br>
     *
     * @throws IOException
     */
    public void empReportView() throws IOException
    {
        mainSubscene.setRoot(FXMLLoader.load(getClass().getResource("scheduleView.fxml")));
        mainSubscene.setHeight(300);
        mainSubscene.setWidth(600);
        theStage = (Stage)mainSubscene.getScene().getWindow();
        theStage.setHeight(350);
        theStage.setWidth(600);
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


