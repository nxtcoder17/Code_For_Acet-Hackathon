package college.login;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

import college.controller.Controller;

public class Login
    extends Controller
{
    @FXML private TextField field_user;
    @FXML private PasswordField field_passwd;

    @FXML private Button button_login;
    @FXML private Button button_signup;

    @FXML protected void button_login_pressed()
            throws SQLException, IOException
    {
        String user = field_user.getText();
        String passwd = field_passwd.getText();
        if (user.isEmpty() || passwd.isEmpty())
            super.notify("Username & Password Required", "You must provide a valid set of Username & password", "error").show();
        else {
            if (user.equals("admin") && passwd.equals("admin"))
                    super.goto_home(button_login);
            else
                super.notify("Access Denied",
                        "You must provide a valid set of Username & password", "warning").show();
        }
    }

    // When user presses Enter Key over the Password Field
    @FXML protected void handle_EnterKey_press()
            throws IOException, SQLException
    {
        button_login_pressed();
    }

    @FXML protected void students_label_pressed()
            throws IOException
    {
        Controller.student = true;
        super.goto_home(field_user);
    }
}
