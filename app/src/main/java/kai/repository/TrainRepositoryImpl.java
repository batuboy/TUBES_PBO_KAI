package kai.repository;

import kai.database.DbConnect;
import kai.models.train.*;
import kai.models.train.num.Status;
import kai.models.train.num.TrainType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainRepositoryImpl implements ITrainRepository {
    private final Connection conn;

    public TrainRepositoryImpl() {
        this.conn = DbConnect.getConnection();
    }

    // INSERT

    public void addTrain(Locomotive locomotive) {
        String sql = "INSERT INTO train (train_id, name, status, train_type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, locomotive.getLocotomiveId());
            ps.setString(2, locomotive.getName());
            ps.setString(3, locomotive.getStatus());
            ps.setString(4, locomotive.getTrainType());
            ps.executeUpdate();

            if (locomotive instanceof DieselTrain) {
                DieselTrain dt = (DieselTrain) locomotive;
                insertDiesel(dt);
            } else if (locomotive instanceof ElectricTrain) {
                ElectricTrain et = (ElectricTrain) locomotive;
                insertElectric(et);
            } else if (locomotive instanceof SteamTrain) {
                SteamTrain st = (SteamTrain) locomotive;
                insertSteam(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertDiesel(DieselTrain d) throws SQLException {
        String sql = "INSERT INTO diesel_train (train_id, fuel_capacity, fuel_consumption_per_km) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getLocotomiveId());
            ps.setDouble(2, d.getFuelCapacity());
            ps.setDouble(3, d.getFuelConsumptionPerKm());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertElectric(ElectricTrain e) throws SQLException {
        String sql = "INSERT INTO electric_train (train_id, voltage, airConditioned) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getLocotomiveId());
            ps.setDouble(2, e.getVoltage());
            ps.setBoolean(3, e.isAirConditioned());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertSteam(SteamTrain s) throws SQLException {
        String sql = "INSERT steam_train (train_id, boil_pressure, water_capacity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getLocotomiveId());
            ps.setDouble(2, s.getBoilerPressure());
            ps.setDouble(3, s.getWaterCapacity());
            ps.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Locomotive getLocomotiveById(String id){
        String sql = "SELECT * FROM train WHERE train_id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                TrainType tc = TrainType.valueOf(rs.getString("train_type"));
                Status status = Status.valueOf(rs.getString("status"));
                String name = rs.getString("name");

                return switch (type){
                    case DIESEL -> loadDiesel(id, name, status);
                    case ELECTRIC -> loadElectric(id, name, status);
                    case STEAM -> loadSteam(id, name, status);
                };
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private DieselTrain loadDiesel (String id, String name, Status status) throws SQLException{
        String sql = "SELECT * FROM diesel_train WHERE train_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();


        if(rs.next()){

            return new DieselTrain(){
                id, name, status, rs.getDouble("fuel_capacity"), rs.getDouble("fuel_consumption_per_km")
            };
        }

        return null;
    }

    private ElectricTrain loadElectric (String id, String name, Status status) throws SQLException {
        String sql = "SELECT * FROM electric_train WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            return new ElectricTrain(
                id, name, status, rs.getDouble("voltage"), rs.getString("isair_conditioned");
            )
        }
        return null;
    }

    private SteamAll loadSteam (String id, String name, Status status) throws SQLException {
        String sql = "SELECT * FROM electric_train WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            return new SteamTrain(
                id, name, status, rs.getDouble("boil_pressure"), rs.getString("water_capacity");
            )
        }
        return null;
    }

    @Override
    public List<Locomotive> getAllTrains(){
        List<Locomotive> list = new ArrayList<>();

        String sql = "SELECT * FROM train";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String id = rs.getString("train_id");
                String name = rs.getString("name");
                String status = Status.valueOf(rs.getString("status"));
                TrainType type = TrainType.valueOf(rs.getString("train_type"));
                Locomotive loc = switch(type) {
                    case DIESEL -> loadDiesel(id, name, status);
                    case ELECTRIC -> loadElectric(id, name, status);
                    case STEAM -> loadSteam(id, name, status);
                };

                list.add(loc)
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE

    public void updateTrain(Locomotive train) {
        String sql = "UPDATE train SET name = ?, status =?, train_type = ? WHERE train_id = ?";

        try (PreparedStatement ps = conn.preparedStatement(sql)) {
            ps.setString(1, train.getName());
            ps.setString(2, train.getStatus().name());
            ps.setString(3, train.getTrainType().name());
            ps.setString(4, train.getTrainId());
            ps.executeUpdate();

            if (train instanceof DieselTrain dt) {
                updateDiesel(dt);
            } else if (train instanceof ElectricTrain et) {
                updateElectric(et);
            } else if (train instanceof SteamTrain st) {
                updateSteam(st);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDiesel(DieselTrain d) throws SQLException {
        String sql = "UPDATE diesel_train SET fuel_capacity = ?, fuel_consumption_per_km = ? WHERE train_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, d.getFuelCapacity());
        ps.setDouble(2, d.getFuelConsumptionPerKm());
        ps.setString(3, d.getTrainId());
        ps.executeUpdate();
    }

    private void updateElectric(ElectricTrain e) throws SQLException {
        String sql = "UPDATE electric_train SET power_capacity = ?, voltage = ? WHERE train_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, e.getPowerCapacity());
        ps.setDouble(2, e.getVoltage());
        ps.setString(3, e.getTrainId());
        ps.executeUpdate();
    }

    private void updateSteam(SteamTrain s) throws SQLException {
        String sql = "UPDATE steam_train SET water_capacity = ?, coal_capacity = ? WHERE train_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, s.getWaterCapacity());
        ps.setDouble(2, s.getCoalCapacity());
        ps.setString(3, s.getTrainId());
        ps.executeUpdate();
    }

    @Override
    public void deleteTrain(String id) {
        // delete from all subtype tables first
        try {
            deleteFromSubTables(id);

            String sql = "DELETE FROM train WHERE train_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFromSubTables(String id) throws SQLException {
        conn.prepareStatement("DELETE FROM diesel_train WHERE train_id = '" + id + "'").executeUpdate();
        conn.prepareStatement("DELETE FROM electric_train WHERE train_id = '" + id + "'").executeUpdate();
        conn.prepareStatement("DELETE FROM steam_train WHERE train_id = '" + id + "'").executeUpdate();
    }

}