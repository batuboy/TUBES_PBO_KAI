package kai.repository;

import kai.database.DbConnect;
import kai.models.train.*;
import kai.models.train.num.Status;
import kai.models.train.num.TrainType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainRepository {

    private final RailCarRepository railcarRepo;

    public TrainRepository() {
        this.railcarRepo = new RailCarRepository();
    }

    public boolean addTrain(Locomotive loco) {
        String sql = "INSERT INTO train (train_id, name, status, train_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loco.getLocomotiveId());
            ps.setString(2, loco.getName());
            ps.setString(3, loco.getStatus());
            ps.setString(4, loco.getTrainType());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addDieselTrain(DieselTrain dt) {
        boolean addTrain = addTrain(dt);
        if (!addTrain) {
            return false;
        }

        String sql = "INSERT INTO diesel_train(locomotiveId, fuelCapacity, fuelConsumptionPerKM) VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dt.getLocomotiveId());
            ps.setDouble(2, dt.getFuelCapacity());
            ps.setDouble(3, dt.getFuelConsumptionPerKm());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addElectricTrain(ElectricTrain et) {
        boolean addTrain = addTrain(et);
        if (!addTrain) {
            return false;
        }

        String sql = "INSERT INTO electric_train(locomotiveId, voltage, airConditioned) VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, et.getLocomotiveId());
            ps.setInt(2, et.getVoltage());
            ps.setInt(3, et.getAirConditioned() ? 1 : 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addSteamTrain(SteamTrain et) {
        boolean addTrain = addTrain(et);
        if (!addTrain) {
            return false;
        }

        String sql = "INSERT INTO steamtrain(locomotiveId, boilerPressure, waterCapacity) VALUES (?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, et.getLocomotiveId());
            ps.setDouble(2, et.getBoilerPressure());
            ps.setDouble(3, et.getWaterCapacity());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Locomotive getDieselTrainById(String trainId) {
        String sql = "SELECT l.*, dt.* FROM locomotive l JOIN diesel_train dt ON l.locomotiveId = dt.locomotiveId WHERE l.locomotiveId = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trainId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                Status status = Status.valueOf(rs.getString("status"));
                int traction = rs.getInt("traction");
                // List<Railcar> coaches = railcarRepo.getCoachesByTrainId(trainId);
                double fuelCapacity = rs.getDouble("fuelCapacity");
                double fuelConsumptionPerKM = rs.getDouble("fuelConsumptionPerKM");

                return new DieselTrain(trainId, name, traction, status, fuelCapacity, fuelConsumptionPerKM);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Deactivate train → set status to MAINTENANCE
    public void deactivateTrain(String trainId) {
        String sql = "UPDATE train SET status = ? WHERE train_id = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Status.MAINTENANCE.name());
            ps.setString(2, trainId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Attach a coach to a train
    public void attachCoach(String trainId, Railcar coach) {
        String sql = "INSERT INTO train_coach (train_id, coach_id) VALUES (?, ?)";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trainId);
            ps.setString(2, coach.getRailcarId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Detach a coach from a train
    public void detachCoach(String trainId, Railcar coach) {
        String sql = "DELETE FROM train_coach WHERE train_id = ? AND coach_id = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trainId);
            ps.setString(2, coach.getRailcarId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // // List all trains
    // public List<Locomotive> getAllTrains() {
    // List<Locomotive> trains = new ArrayList<>();
    // String sql = "SELECT * FROM train";

    // try (Connection conn = DbConnect.getConnection();
    // PreparedStatement ps = conn.prepareStatement(sql);
    // ResultSet rs = ps.executeQuery()) {

    // while (rs.next()) {
    // String trainId = rs.getString("train_id");
    // String name = rs.getString("name");
    // Status status = Status.valueOf(rs.getString("status"));
    // TrainType type = TrainType.valueOf(rs.getString("train_type"));

    // trains.add(new LocomotiveStub(trainId, name, type, status)); // simplified
    // }

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }

    // return trains;
    // }
}
