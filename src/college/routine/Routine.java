package college.routine;

import javafx.fxml.FXML;
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

        if (Routine.sec != null) {
            combo_class.setValue(Routine.sec);
            populateRoomField(Routine.sec);
            System.out.println("Yes the populate method called");
        }

        // Just make it fetch contents from Database and it's all set
    }

    private void populateRoomField(String section)
    {
        String d = field_day.getText();
        System.out.println("Day: " + d);
        ResultSet r;
        try {
            r = Controller.db.readRoomField(section, d);
            if (r.next()) {
                field_first.setText(r.getString("first"));
                field_second.setText(r.getString("second"));
                field_third.setText(r.getString("third"));
                field_fourth.setText(r.getString("fourth"));
                field_fifth.setText(r.getString("fifth"));
                field_sixth.setText(r.getString("sixth"));
                field_seventh.setText(r.getString("seventh"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception raised");
            e.printStackTrace();
        }
    }

    @FXML
    protected void comboBoxListener()
    {
        String sec = combo_class.getValue();
        populateRoomField(sec);
    }

    @FXML
    public void button_next_pressed()
            throws IOException, SQLException
    {
        Routine.sec = combo_class.getValue();
        String day = field_day.getText();

        String first = field_first.getText();
        String second = field_second.getText();
        String third = field_third.getText();
        String fourth = field_fourth.getText();
        String fifth = field_fifth.getText();
        String sixth = field_sixth.getText();
        String seventh = field_seventh.getText();

        boolean condition = first.isEmpty() || second.isEmpty() || third.isEmpty() ||
                fourth.isEmpty() || fifth.isEmpty() || sixth.isEmpty() || seventh.isEmpty();

        if (!condition)
            Controller.db.fillChoices(Routine.sec, day, first, second, third,
                    fourth, fifth, sixth, seventh);

        if (day.equals("Fri")) {
            super.notify("Routine Added",
                    "Routine Successfully added for Class: " + Routine.sec, "confirm");
            System.out.println("Routine for this class has been filled");
        }
        else
            super.sceneSwitcher("routine/routine.fxml", combo_class,
                    "Routine", 600, 400);
    }
}
