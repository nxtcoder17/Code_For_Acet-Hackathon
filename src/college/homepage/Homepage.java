package college.homepage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import college.controller.Controller;

public class Homepage
    extends Controller
{
    @FXML private Button button_search;
    @FXML private Button button_roomsdb;
    @FXML private Button button_routine;

    public void initialize()
    {
        if (Controller.student) {
            button_roomsdb.setDisable(true);
            button_routine.setDisable(true);
            Controller.student = false;
        }
    }

    @FXML
    protected void button_search_pressed()
            throws IOException
    {
        super.sceneSwitcher("search/search.fxml", button_search,
                "Search Free ClassRoom", 800, 400);
    }

    @FXML
    protected void button_roomsdb_pressed()
            throws IOException
    {
        super.sceneSwitcher("roomsdb/roomsdb.fxml", button_roomsdb,
                "Room Database", 800, 400);
    }

    @FXML
    protected void button_routine_pressed()
            throws IOException
    {
        super.sceneSwitcher("routine/routine.fxml", button_routine,
                "ClassRoutines", 1200, 1000);
    }

    @FXML
    protected void logout_method()
            throws IOException
    {
        super.sceneSwitcher("login/login.fxml", button_search,
                "Search Free ClassRoom", 800, 400);
    }

    @FXML
    protected void exit_method()
    {
        System.exit(0);
    }
}
