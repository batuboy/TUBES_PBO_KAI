// package kai.repository;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import kai.database.DbConnect;
// import kai.models.user.*;
// import kai.models.user.num.Position;

// public class EmployeeRepository {

//     UserRepository userRepository;

//     public EmployeeRepository() {
//         userRepository = new UserRepository();
//     }

//     public boolean addEmployee(Employee e) {
//         boolean userAdded = userRepository.register(e);
//         if (!userAdded) {
//             return false;
//         }

//         String sql = "INSERT INTO employee (employeeId, position, salary) VALUES (?, ?, ?)";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, e.getEmployeeId());
//             ps.setString(2, String.valueOf(e.getPosition()));
//             ps.setDouble(3, e.getSalary());

//             ps.executeUpdate();
//             return true; // success

//         } catch (SQLException ex) {
//             ex.printStackTrace();
//             return false;
//         }
//     }

//     public boolean addMachinist(Machinist m) {
//         boolean employeeAdded = addEmployee(m);
//         if (!employeeAdded) {
//             return false;
//         }

//         String sql = "INSERT INTO machinist (employeeId, license_number) VALUES (?, ?)";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, m.getEmployeeId());
//             ps.setString(2, m.getLicenseNumber());

//             ps.executeUpdate();
//             return true; // success

//         } catch (SQLException ex) {
//             ex.printStackTrace();
//             return false;
//         }
//     }


//     // Get Admin by ID
//     public Admin getAdminById(String id) {
        
//         String sql = "SELECT u.*, e.* FROM employee e JOIN user u ON u.userid = e.userid WHERE employeeId = ? AND role = 'ADMIN'";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, id);
//             ResultSet rs = ps.executeQuery();

//             if (rs.next()) {
//                 // Position position = Position.valueOf(rs.getString("position"));

//                 return new Admin(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
//                         rs.getDouble("salary"));
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // Get Admin by Email
//     public Admin getAdminByEmail(String email) {
        
//         String sql = "SELECT u.*, e.* FROM employee e JOIN user u ON u.userid = e.userid WHERE u.email = ? AND role = 'ADMIN'";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, email);
//             ResultSet rs = ps.executeQuery();

//             if (rs.next()) {
//                 // Position position = Position.valueOf(rs.getString("position"));

//                 return new Admin(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
//                         rs.getDouble("salary"));
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // Get TrainDispatcher by ID
//     public TrainDispatcher getDispatcherById(String id) {
        
//         String sql = "SELECT u.*, e.* FROM employee e JOIN user u ON u.userid = e.userid WHERE employeeId = ? AND role = 'TRAIN_DISPATCHER'";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, id);
//             ResultSet rs = ps.executeQuery();

//             if (rs.next()) {

//                 // Position position = Position.valueOf(rs.getString("position"));

//                 return new TrainDispatcher(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
//                         rs.getDouble("salary"));
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // Get Conductor by ID
//     public Conductor getConductorById(String id) {
//         String sql = "SELECT u.*, e.* FROM employee e JOIN user u ON u.userid = e.userid WHERE employeeId = ? AND role = 'CONDUCTOR'";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, id);
//             ResultSet rs = ps.executeQuery();

//             if (rs.next()) {

//                 // Position position = Position.valueOf(rs.getString("position"));

//                 return new Conductor(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
//                         rs.getDouble("salary"));
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // Get Machinist by ID (has extra attribute)
//     public Machinist getMachinistById(String id) {
//         String sql = "SELECT u.*, e.*, m.* FROM user u JOIN employee e ON u.nik = e.nik JOIN machinist m ON e.employeeId = m.employeeId WHERE e.employeeId = ? AND e.role = 'MACHINIST';";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setString(1, id);
//             ResultSet rs = ps.executeQuery();

//             if (rs.next()) {

//                 // Position position = Position.valueOf(rs.getString("position"));

//                 return new Machinist(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
                        
//                         rs.getDouble("salary"),
//                         rs.getString("licenseNumber"));
//             }
//         } catch (

//         SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     public List<Admin> getAllAdmins() {
//         List<Admin> admins = new ArrayList<>();
//         String sql = "SELECT u.userId, u.nik, u.namaLengkap, u.nomorTelepon, u.email, u.password, " +
//                 "e.employeeId, e.position, e.salary " +
//                 "FROM user u JOIN employee e ON u.userId = e.userId " +
//                 "WHERE e.position = 'ADMIN'";

//         try (Connection conn = DbConnect.getConnection();
//                 PreparedStatement ps = conn.prepareStatement(sql);
//                 ResultSet rs = ps.executeQuery()) {

//             while (rs.next()) {
//                 // Position pos = Position.valueOf(rs.getString("position"));
//                 Admin admin = new Admin(
//                         rs.getString("nik"),
//                         rs.getString("namaLengkap"),
//                         rs.getString("nomorTelepon"),
//                         rs.getString("email"),
//                         rs.getString("password"),
//                         rs.getString("employeeId"),
                        
//                         rs.getDouble("salary"));
//                 admins.add(admin);
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }

//         return admins;
//     }

// }
