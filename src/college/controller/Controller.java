package college.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.sql.SQLException;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import javafx.geometry.Pos;
import javafx.util.Duration;

import java.util.HashMap;

import org.controlsfx.control.Notifications;

import college.database.Database;

public class Controller {
    private static String NOTIFY_ICONS = "/college/images/";
    protected static Database db;

    protected static boolean student = false;

    public Controller()
    {
        try {
            db = new Database();
        } catch (SQLException e) {

        } catch (IOException e) {

        }
    }

    protected void goto_home(Parent node)
            throws IOException
    {
        Stage stage = (Stage)node.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().
                getResource("/college/homepage/homepage.fxml"));
        stage.hide();
        stage.setScene(new Scene(newRoot, 800, 600));
        stage.setTitle("Home Page");
        stage.show();
    }

    protected Notifications notify(String title, String text)
    {
        return notify(title, text, "normal");
    }

    protected Notifications notify(String title, String text, String type)
    {
        Notifications obj = Notifications.create();

        obj.position(Pos.TOP_RIGHT);
        obj.darkStyle();
        obj.hideAfter(Duration.seconds(5));
        obj.title(title);
        obj.text(text);

        switch(type) {
            case "normal":
                break;
            case "error":
                obj.graphic(new ImageView(NOTIFY_ICONS + "invalid-64px.png"));
                break;
            case "confirm":
                obj.graphic(new ImageView(NOTIFY_ICONS + "confirmation-64px.png"));
                break;
            case "warning":
                obj.graphic(new ImageView(NOTIFY_ICONS + "invalid-64px.png"));
                break;
        }

        return obj;
    }

    protected void sceneSwitcher(String file,Parent node,
                                 String title, double width, double height)
            throws IOException
    {
        Stage stage = (Stage) node.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(
                getClass().getResource("/college/" + file));

        stage.hide();
        stage.setScene(new Scene(newRoot, width, height));
        stage.setTitle(title);
        stage.show();
    }
}
