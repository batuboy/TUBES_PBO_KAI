package kai.models.user;

import kai.models.user.num.Position;

public abstract class Employee extends User {
    
    private String employeeId;
    private double salary;
    
    public Employee(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            Position passenger, String employeeId, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, passenger);
        //TODO Auto-generated constructor stub
        this.employeeId = employeeId;
        this.salary = salary;
    }    

    
    public Employee(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            Position passenger) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password, passenger);
        
    }    

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }        
}
