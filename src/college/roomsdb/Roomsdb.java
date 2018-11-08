package college.roomsdb;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.io.IOException;

import java.util.HashMap;

import college.controller.Controller;

public class Roomsdb
    extends Controller
{

    @FXML private TextField field_room;
    private static String r = null;

    @FXML
    protected void button_submit_pressed()
        throws SQLException, IOException
    {
        r = field_room.getText();
        Controller.db.addRoom(r);
        super.notify("Successfully Added",
                "Room successfully added in DB","confirm").show();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Alert");
        alert.setContentText("Room: %s has been successfully added".format(r));
        alert.showAndWait();

        Controller.db.initialize_tables("Mon", r);
        Controller.db.initialize_tables("Tue", r);
        Controller.db.initialize_tables("Wed", r);
        Controller.db.initialize_tables("Thu", r);
        Controller.db.initialize_tables("Fri", r);

        super.sceneSwitcher("roomsdb/roomsdb.fxml", field_room,
                "Add New Rooms", 800, 400);
    }

    @FXML
    protected void button_cancel_pressed()
        throws IOException
    {
        super.goto_home(field_room);
//        super.sceneSwitcher("homepage/homepage.fxml", field_room,
//                "HomePage", 800, 400);
    }
}
