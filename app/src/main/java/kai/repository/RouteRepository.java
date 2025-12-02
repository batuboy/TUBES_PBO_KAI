import kai.models.train.Route;
import kai.models.train.Station;
import kai.database.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {

    private DbConnect dbConnect; 

    public RouteRepository(){
        dbConnect = new DbConnect();
    }

    public void addRoute(Route route) {
        String sqlRoute = "INSERT INTO Routes (origin_id, destination_id) VALUES (?, ?)";
        String sqlStops = "INSERT INTO Route_Stops (station_id, route_id, stop_order) VALUES (?, ?, ?)";

        try (Connection conn = dbConnect.getConnection()){

            PreparedStatement psRoute = conn.prepareStatement(sqlRoute, PreparedStatement.RETURN_GENERATED_KEYS );
            psRoute.setInt(1, route.getOrigin()); 
            psRoute.setInt(1, route.getDestination());
            psRoute.executeUpdate();            

            ResultSet rs = psRoute.getGeneratedKeys();
            int routeId = 0;
            if(rs.next()) {
                routeId = rs.getInt(1);
            }

            PreparedStatement psStops = conn.prepareStatement(sqlStops);
            int order = 1;

            for (int stationId  : route.getStops()) {
                psStops.setInt(1, routeId);
                psStops.setInt(2, stationId);
                psStops.setInt(3, order);
                psStops.executeUpdate();
                order++;
            }

            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public Route getRouteById(int routeId) {
        String sqlRoute = "SELECT origin_id, destination_id FROM Routes WHERE route_id = ?";
        String sqlStops = "SELECT rs.station_id, s.name, s.city " +
                          "FROM Route_Stops rs " +
                          "JOIN Stations s ON rs.station_id = s.station_id " +
                          "WHERE rs.route_id = ? " +
                          "ORDER BY rs.stop_order";
        
        try (Connection conn = dbConnect.getConnection()) {
            PreparedStatement psRoute = conn.prepareStatement(sqlRoute);
            psRoute.setInt(1, routeId);
            ResultSet rsRoute = psRoute.executeQuery();

            int originId = 0;
            int destinationId = 0;
            if (rsRoute.next()) {
                originId = rsRoute.getInt("origin_id");
                destinationId = rsRoute.getInt("destination_id"); 
            }

            PreparedStatement psStops = conn.prepareStatement(sqlStops);
            psStops.setInt(1, routeId);
            ResultSet rsStops = psRoute.executeQuery();

            List<Station> stops = new ArrayList<>();
            while (rsStops.next()) {
                Station s = new Station (
                    String.valueOf(rsStops.getInt("station_id")),
                    rsStops.getString("name"),
                    rsStops.getString("city")
                );
                stops.add(s);
            }

            return new Route (
                String.valueOf(rsStops.getInt("station_id")),
                originId,
                destinationId,
                convertStationToId(stops)
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> convertStationToId(List<Station> stations) {
        List<Integer> list = new ArrayList<>();

        for (Station s : stations) {
            list.add(Integer.parseInt(s.getStationId()));
        }

        return list;
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