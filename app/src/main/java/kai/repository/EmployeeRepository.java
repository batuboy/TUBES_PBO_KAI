package kai.repository;

import kai.models.user.Employee;
import kai.models.user.User;
import kai.models.user.Admin;
import kai.models.user.Conductor;
import kai.models.user.num.Position;
import kai.database.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private DbConnect dbConnect;
    private final UserRepository userRepo;

    public EmployeeRepository() {
        userRepo = new UserRepository();
        dbConnect = new DbConnect();
    }

    // admin, disptacher
    public boolean addEmployee(Employee emp) {

        boolean userCreated = userRepo.register(emp);

        if (!userCreated)
            return false;
        String sql = "INSERT employee INTO (nik, employeeId, position, salary) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getUserId());
            ps.setString(2, emp.getEmployeeId());
            ps.setString(3, emp.getPosition());
            ps.setDouble(4, emp.getSalary());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // conductor table is subtype of employee
    public boolean addEmployee(Conductor conductor) {

        boolean userCreated = userRepo.register(conductor);
        if (!userCreated)
            return false;

        boolean empCreated = addEmployee((Employee) conductor);
        if (!empCreated)
            return false;

        String sql = "INSERT conductor INTO (employeeId, areaAssignedId) VALUES (?, ?)";
        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, conductor.getEmployeeId());
            ps.setString(2, conductor.getStationAssigned().getStationId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Employee getEmployeeById(String empId) {
        String sql = "SELECT * FROM employee WHERE employeeId = ?";

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String positionStr = rs.getString("position");
                Position position = Position.valueOf(positionStr);
                String userId = rs.getString("userId");
                double salary = rs.getDouble("salary");

                User user = userRepo.getUserById(userId);
                if (user == null) return null;

                return new Employee(
                        user.getNik(),
                        user.getNamaLengkap(),
                        user.getNomorTelepon(),
                        user.getEmail(),
                        user.getPassword(),
                        empId,
                        position,
                        salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // not found
    }

    public void updateEmployee(Employee emp) {

    }

    public void deleteEmployee(String empId) {

    }
}
