package kai;

import java.sql.Connection;

import kai.database.DbConnect;
import kai.view.LoginView;
import kai.view.adminView.MenuAdminView;
import kai.view.userView.MenuUserView;

public class App {
    public static void main(String[] args) {
        // new MenuUserView().setVisible(true);
        // new LoginView().setVisible(true);

        // TEST CONNECTION
        // Connection conn = DbConnect.getConnection();

        // if (conn != null) {
        //     System.out.println("PASSED");
        // }else {
        //     System.err.println("FAILED");
        // }
    }
}
