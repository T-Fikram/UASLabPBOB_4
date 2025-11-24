import GUI.LoginGUI;
import service.RestaurantSystem;

public class AppLauncher {
    public static void main(String[] args) {
        RestaurantSystem system = new RestaurantSystem();
        new LoginGUI(system);
    }
}
