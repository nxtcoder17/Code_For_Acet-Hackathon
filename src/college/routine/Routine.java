package college.routine;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import college.controller.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedList;

import college.database.Database;
public class Routine
    extends Controller
{
    @FXML ComboBox<String> combo_class;
    @FXML TextField field_day;

    @FXML TextField field_first;
    @FXML TextField field_second;
    @FXML TextField field_third;
    @FXML TextField field_fourth;
    @FXML TextField field_fifth;
    @FXML TextField field_sixth;
    @FXML TextField field_seventh;

    @FXML ComboBox<String> combo_first;
    @FXML ComboBox<String> combo_second;
    @FXML ComboBox<String> combo_third;
    @FXML ComboBox<String> combo_fourth;
    @FXML ComboBox<String> combo_fifth;
    @FXML ComboBox<String> combo_sixth;
    @FXML ComboBox<String> combo_seventh;

    @FXML Button button_next;

    private static String sec = null;
    private static String day = null;
    private static LinkedList<String> days = new LinkedList<>();

    public void initialize()
    {
        combo_class.getItems().addAll("CSE_1", "CSE_2", "CSE_3");

        if (days.isEmpty()) {
            days.add("Mon");
            days.add("Tue");
            days.add("Wed");
            days.add("Thu");
            days.add("Fri");
            field_day.setText(days.poll());
        } else
            field_day.setText(days.poll());

        // Just make it fetch contents from Database and it's all set

        if (Routine.sec != null) {
            combo_class.setValue(Routine.sec);
            populateRoomField(Routine.sec);
            System.out.println("Yes the populate method called");
        }

        populateComboBox();
    }

    private void populateRoomField(String section)
    {
        String d = field_day.getText();
        System.out.println("Day: " + d);
        ResultSet r;
        try {
            r = Controller.db.readRoomField(section, d);
            if (r.next()) {
                combo_first.setValue(r.getString("first"));
                combo_second.setValue(r.getString("second"));
                combo_third.setValue(r.getString("third"));
                combo_fourth.setValue(r.getString("fourth"));
                combo_fifth.setValue(r.getString("fifth"));
                combo_sixth.setValue(r.getString("sixth"));
                combo_seventh.setValue(r.getString("seventh"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception raised");
            e.printStackTrace();
        }
    }

    private void populateComboBox()
    {
        try {
            ResultSet r = Controller.db.getRooms();
            while (r.next()) {
                combo_first.getItems().add(r.getString("room"));
                combo_second.getItems().add(r.getString("room"));
                combo_third.getItems().add(r.getString("room"));
                combo_fourth.getItems().add(r.getString("room"));
                combo_fifth.getItems().add(r.getString("room"));
                combo_sixth.getItems().add(r.getString("room"));
                combo_seventh.getItems().add(r.getString("room"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception raised");
            e.printStackTrace();
        }
    }

    @FXML
    protected void comboBoxListener()
    {
        String s = combo_class.getValue();
        if (s != null)
            populateRoomField(s);
    }

    @FXML
    public void button_next_pressed()
            throws IOException, SQLException
    {
        Routine.sec = combo_class.getValue();
        String day = field_day.getText();

        String first = combo_first.getValue();
        String second = combo_second.getValue();
        String third = combo_third.getValue();
        String fourth = combo_fourth.getValue();
        String fifth = combo_fifth.getValue();
        String sixth = combo_sixth.getValue();
        String seventh = combo_seventh.getValue();

//        boolean condition = first.isEmpty() || second.isEmpty() || third.isEmpty() ||
//                fourth.isEmpty() || fifth.isEmpty() || sixth.isEmpty() || seventh.isEmpty();
//
//        if (!condition)
        Controller.db.fillChoices(Routine.sec, day, first, second, third,
                fourth, fifth, sixth, seventh);

        if (day.equals("Fri")) {
//            super.notify("Routine Added",
//                    "Routine Successfully added for Class: " + Routine.sec, "confirm");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Alert");
            alert.setContentText( "Routine for class " + Routine.sec +
                    " has been successfully updated");
            alert.showAndWait();
//            System.out.println("Routine for this class has been filled");
            button_cancel_pressed();
        }
        else
            super.sceneSwitcher("routine/routine.fxml", combo_class,
                    "Routine", 1200, 1000);
    }

    @FXML
    protected void button_cancel_pressed()
            throws IOException
    {
        sec = null;
        days = new LinkedList<>();
        super.goto_home(combo_class);
//        super.sceneSwitcher("homepage/homepage.fxml", combo_class,
//                "HomePage", 800, 600);
    }
}
