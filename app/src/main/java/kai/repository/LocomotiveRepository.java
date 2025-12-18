package kai.repository;

import kai.database.DbConnect;
import kai.models.train.*;
import kai.models.train.num.Status;
import kai.models.train.num.LocomotiveType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class LocomotiveRepository {
    private Connection conn;

    public LocomotiveRepository() {
        this.conn = DbConnect.getConnection();
    }

    // INSERT

    // public void addLocomotive(Locomotive locomotive) {
    // String sql = "INSERT INTO train (train_id, name, status, train_type) VALUES
    // (?, ?, ?, ?)";

    // try (PreparedStatement ps = conn.prepareStatement(sql)) {
    // ps.setString(1, locomotive.getLocomotiveId());
    // ps.setString(2, locomotive.getName());
    // ps.setString(3, locomotive.getStatus());
    // ps.setString(4, locomotive.getLocomotiveType());
    // ps.executeUpdate();

    // if (locomotive instanceof DieselTrain) {
    // DieselTrain dt = (DieselTrain) locomotive;
    // insertDiesel(dt);
    // } else if (locomotive instanceof ElectricTrain) {
    // ElectricTrain et = (ElectricTrain) locomotive;
    // insertElectric(et);
    // } else if (locomotive instanceof SteamTrain) {
    // SteamTrain st = (SteamTrain) locomotive;
    // insertSteam(st);
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // }

    public List<Locomotive> getLocomotivesAvailable() {
        List<Locomotive> list = new ArrayList<>();

        String sql = "SELECT locomotiveId, name, status FROM locomotive WHERE status = 'AVAILABLE'";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Status status = Status.valueOf(rs.getString("status").toUpperCase());
                Locomotive loco = new Locomotive(
                        rs.getString("locomotiveId"),
                        rs.getString("name"),
                        status);
                list.add(loco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET ALL LOCOMOTIVES =====
    public List<Locomotive> findAllLocomotive() {
        List<Locomotive> list = new ArrayList<>();

        String sql = "SELECT locomotiveId, name, status FROM locomotive";

        try (
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Status status = Status.valueOf(rs.getString("status").toUpperCase());

                Locomotive loco = new Locomotive(
                        rs.getString("locomotiveId"),
                        rs.getString("name"),
                        status);
                list.add(loco);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatusLocomotive(String id, Status status) {
        String sql = "UPDATE locomotive SET status = ? WHERE locomotiveId = ?";

        try (
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertDiesel(DieselTrain d) {
        String sql = "INSERT INTO diesel_train (train_id, fuel_capacity, fuel_consumption_per_km) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getLocomotiveId());
            ps.setDouble(2, d.getFuelCapacity());
            ps.setDouble(3, d.getFuelConsumptionPerKm());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertElectric(ElectricTrain e) {
        String sql = "INSERT INTO electric_train (train_id, voltage, airConditioned) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getLocomotiveId());
            ps.setDouble(2, e.getVoltage());
            ps.setBoolean(3, e.isAirConditioned());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertSteam(SteamTrain s) {
        String sql = "INSERT steam_train (train_id, boil_pressure, water_capacity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getLocomotiveId());
            ps.setDouble(2, s.getBoilerPressure());
            ps.setDouble(3, s.getWaterCapacity());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public Locomotive getLocomotiveById(String id) {
        String sql = "SELECT * FROM train WHERE train_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocomotiveType type = LocomotiveType.valueOf(rs.getString("train_type"));
                Status status = Status.valueOf(rs.getString("status"));
                String name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Locomotive> getAllLocomotives() {
        List<Locomotive> list = new ArrayList<>();

        String sql = "SELECT * FROM train";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String id = rs.getString("train_id");
                String name = rs.getString("name");
                Status status = Status.valueOf(rs.getString("status"));
                LocomotiveType type = LocomotiveType.valueOf(rs.getString("train_type"));

                list.add(new Locomotive(id, name, status, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // UPDATE

    public void updateLocomotive(Locomotive train) {
        String sql = "UPDATE train SET name = ?, status =?, train_type = ? WHERE train_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, train.getName());
            // ps.setString(2, train.getStatus());
            ps.setString(3, train.getLocomotiveType());
            ps.setString(4, train.getLocomotiveId());
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

    private void updateDiesel(DieselTrain d) {
        String sql = "UPDATE diesel_train SET fuel_capacity = ?, fuel_consumption_per_km = ? WHERE train_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setDouble(1, d.getFuelCapacity());
            ps.setDouble(2, d.getFuelConsumptionPerKm());
            ps.setString(3, d.getLocomotiveId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateElectric(ElectricTrain et) {
        String sql = "UPDATE electric_train SET voltage = ? WHERE train_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setDouble(1, et.getVoltage());
            ps.setString(2, et.getLocomotiveId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateSteam(SteamTrain s) {
        String sql = "UPDATE steam_train SET water_capacity = ?, boilerPressure = ?, WHERE train_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setDouble(1, s.getWaterCapacity());
            ps.setDouble(2, s.getBoilerPressure());
            ps.setString(3, s.getLocomotiveId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletelocomotive(String id) {
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

    private void deleteFromSubTables(String id) {
        try {
            conn.prepareStatement("DELETE FROM diesel_train WHERE train_id = '" + id + "'").executeUpdate();
            conn.prepareStatement("DELETE FROM electric_train WHERE train_id = '" + id + "'").executeUpdate();
            conn.prepareStatement("DELETE FROM steam_train WHERE train_id = '" + id + "'").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}