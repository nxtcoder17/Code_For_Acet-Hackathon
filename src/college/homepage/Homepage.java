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
                "ClassRoutines", 800, 400);
    }
}
