import kai.models.train.Route;
import kai.database.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RouteRepository {

    private static final Connection conn;

    public RouteRepository() {
        conn = DbConnect.getConnection();
    }

    public void addRoute(Route route) {
        String sqlRoute = "INSERT INTO Routes (origin_id, destination_id) VALUES (?, ?)";
        String sqlStops = "INSERT INTO Route_Stops (station_id, route_id, stop_order) VALUES (?, ?, ?)";

        try {
            
            int origin = route.getStops().get(0);
            int destination = route.getStops().get(route.getStops().size());
            
            PreparedStatement psRoute = conn.prepareStatement(sqlRoute, PreparedStatement.RETURN_GENERATED_KEYS );
            psRoute.setInt(1, route.getOrigin());             

        } catch(

    Exception e)
    {
        e.printStackTrace();
    }


    public void 
}
}

/// jadi gini, anggaplah station itu rumah mala stationID itu adalah alamat rumah
/// lalu ada route yang mana merupakan rute perjalanan dari satu rumah ke satu rumah lainnya. routeID itu buat nunjukin ini itu perjalan yang mana
/// stops adalah rumah--rumah yang kita lewati untuk sampai ke tujuan 
///
/// 
/// 
/// misalnya 
/// 
///
/// 
/// 
/// 
/// 
/// 
/// 
/// 