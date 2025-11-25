package kai.repository;

import kai.models.train.*;
import kai.models.train.num.*;
import kai.models.train.num.TrainClass;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import kai.database.DbConnect;

public class RailCarRepository{
    private DbConnect dbConnect; 

    public RailCarRepository(){
        dbConnect = new DbConnect();
    }
    
    public void addRailCar(Railcar railcar){
        String sqlRailcar = "INSERT INTO railcar(railcar_id, name, train_class, capacity, status) VALUES (?, ?, ?, ?, ?)";
        String sqlSeat = "INSERT INTO railcar_seats(railcar_id, seat_number, status) VALUES (?,?,?)";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);
            psRailcar.setString(1, railcar.getRailcarId());
            psRailcar.setString(2, railcar.getName());
            psRailcar.setString(3, railcar.getTrainClass().name());
            psRailcar.setInt(4, railcar.getCapacity());
            psRailcar.setString(5, railcar.getStatus().name());
            psRailcar.executeUpdate();
            
            PreparedStatement psSeat = conn.prepareStatement(sqlSeat);
            for (String seat : railcar.getSearNumbers()) { 
                psSeat.setString(1, railcar.getRailcardId());
                psSeat.setString(2, seat);
                psSeat.setString(3, "AVAILABLE"); 
                psSeat.addBatch();
            }
            psSeat.executeBatch();
        } catch (Exception e) {
        }
    }

    public void addWagon(Railcar wagon){

        String sql = "INSERT INTO wagon (trainId, ) VALUES (?, ?, ?, ?, ?)";
    }

    public void addCarriage(Railcar carriage){
        // Similar to addRailCar but specific to carriages if needed
    }

    public Railcar getRailcarById(String railcarId){
        String sqlRailcar = "SELECT * FROM railcar WHERE railcar_id = ?";
        String sqlSeats = "SELECT seat_number FROM railcar_seats WHERE rilcar_id = ?";
        
        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);
            psRailcar.setString(1, railcarId);
            ResultSet rsRailcar = psRailcar.executeQuery();

            if (rsRailcar.next()){
                String name = rsRailcar.getString("name");
                TrainClass trainClass = Railcar.TrainClass.valueOf(rsRailcar.getString("train_class"));
                int capacity = rsRailcar.getInt("capacity");
                Status status = status.valueOf(rsRailcar.getString("status"));
                
            }            
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }
}


    
 