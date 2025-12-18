package kai.repository;

import java.util.ArrayList;
import kai.database.DbConnect;
import kai.models.train.Station;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;

public class StationRepository {

    private final Connection conn;

    public StationRepository(){
        conn = DbConnect.getConnection();
    }    

    public void addStation(Station station){
        String sql = "INSERT INTO station (station_id, name, city) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, station.getStationId());
            ps.setString(2, station.getName());
            ps.setString(3, station.getCity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Station getStationById(String stationId){
        String sql = "SELECT * FROM station WHERE station_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, stationId);
            ResultSet rs = ps.executeQuery();      

            if(rs.next()){
                return new Station(
                    rs.getString("station_id"),
                    rs.getString("name"),
                    rs.getString("city")
                );
            }     
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Station> getAllStations(){
        String sql = "SELECT * FROM station";
        List<Station> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Station station = new Station
                (rs.getString("station_id"), 
                rs.getString("name"), 
                rs.getString("city"));

                list.add(station);
            }
            
        }
        catch(SQLException e){
                e.printStackTrace();
        }
        return list;
    }

    public void updateStation(Station station){
        String sql = "UPDATE station SET name = ?, city = ? WHERE station_id = ? ";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, station.getName());
            ps.setString(2, station.getCity());
            ps.setString(3, station.getStationId());
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteStation(String stationId){
        String sql = "DELETE FROM station WHERE station_id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, stationId);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}