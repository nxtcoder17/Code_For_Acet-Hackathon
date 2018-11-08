package college.search;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Date;

import java.io.IOException;
import java.sql.SQLException;

import college.controller.Controller;

public class Search
    extends Controller
{

    @FXML private ComboBox<String> combo_period;
    @FXML private ComboBox<String> combo_days;

    @FXML private Label result_label;
    @FXML private Label result_header;

    private static HashMap<String, String> periodMap = new HashMap<>();
    static {
        periodMap.put("1st Period (09:00 - 09:55)", "first");
        periodMap.put("2nd Period (09:55 - 10:50)", "second");
        periodMap.put("3rd Period (10:50 - 11:45)", "third");
        periodMap.put("4th Period (11:45 - 12:40)", "fourth");
        periodMap.put("5th Period (01:35 - 02:30)", "fifth");
        periodMap.put("6th Period (02:30 - 03:25)", "sixth");
        periodMap.put("7th Period (03:25 - 04:15)", "seventh");
    }

    public void initialize()
    {
        combo_period.getItems().addAll(
                "1st Period (09:00 - 09:55)",
                "2nd Period (09:55 - 10:50)",
                "3rd Period (10:50 - 11:45)",
                "4th Period (11:45 - 12:40)",
                "5th Period (01:35 - 02:30)",
                "6th Period (02:30 - 03:25)",
                "7th Period (03:25 - 04:15)" );

        combo_days.getItems().addAll("Mon", "Tue", "Wed", "Thu", "Fri");

        Calendar x = Calendar.getInstance();
        Date d = x.getTime();
        String s = d.toString();
        combo_days.setValue(s.substring(0, 3));
    }

    @FXML
    protected void button_submit_pressed()
            throws SQLException
    {
        String s = combo_period.getValue();
        String d = combo_days.getValue();

        ResultSet r = Controller.db.getEmptyRooms(d, periodMap.get(s));
        if (r.next()) {
            StringBuilder rooms = new StringBuilder();
            rooms.append(r.getString("sno"));
            int count = 1;
            while(r.next()) {
                if (count == 5) {
                    count = 0;
                    rooms.append("\n" + r.getString("sno"));
                }
                else
                    rooms.append(" , " + r.getString("sno"));
                count += 1;
            }
            result_header.setText("Free Rooms are");
            result_label.setText(rooms.toString());
            result_header.setVisible(true);
            result_label.setVisible(true);
            super.notify("Free ClassRooms", rooms.toString(), "confirm").show();
        } else {
            super.notify("No Free Room",
                    "No Class Room is Free on " + d, "warning").show();
        }
    }

    @FXML
    protected void button_cancel_pressed()
            throws IOException
    {
        super.goto_home(combo_days);
//        super.sceneSwitcher("homepage/homepage.fxml", combo_days,
//                "HomePage", 800, 600);
    }
}
