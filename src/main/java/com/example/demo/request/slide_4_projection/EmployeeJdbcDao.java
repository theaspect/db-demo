package com.example.demo.request.slide_4_projection;

import com.example.demo.domain.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("ALL")
public class EmployeeJdbcDao {

    public void save(Employee employee) throws Exception {
        String sql = "insert into employee(id, first_mame, last_name, department_id) values(?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setLong(4, employee.getDepartment().getId());
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }
    }

    public boolean update(Employee employee) throws Exception {
        String sql = "update employee set first_mame = ?, last_name = ?, department_id = ? where id = ?";
        Connection connection = null;
        boolean flag = false;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getDepartment().getId());
            statement.setLong(4, employee.getId());
            if (statement.executeUpdate(sql) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }
        return flag;
    }

    public String getById(int id) throws Exception {
        String sql = "select * from emp where empno=" + empNo;
        StringBuilder sb = new StringBuilder();
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("t");
                sb.append(rs.getString(2));
                sb.append("t");
                sb.append(rs.getDouble(3));
                sb.append("t");
                sb.append(rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    public String getAll(int id) throws Exception {
        String sql = "select * from emp where empno=" + empNo;
        StringBuilder sb = new StringBuilder();
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("t");
                sb.append(rs.getString(2));
                sb.append("t");
                sb.append(rs.getDouble(3));
                sb.append("t");
                sb.append(rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    public Collection<Employee> getByDepartmentId(int id) throws Exception {
        String sql = "select * from employee where department_id = ?";
        Connection connection = null;
        Collection<Employee> result = new ArrayList<>();
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(sql);
            result = mapToEmployee(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    public Collection<Employee> getCustomEmployeeByDepartmentId(int id) throws Exception {
        String sql = "select id, first_name from employee where department_id = ?";
        Connection connection = null;
        Collection<Employee> result = new ArrayList<>();
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(sql);
            result = mapToCustomEmployee(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    private Collection<Employee> mapToCustomEmployee(ResultSet resultSet) {
        System.out.println();
        return null;
    }

    public boolean delete(int empno) throws Exception {
        String sql = "delete from emp where empno=" + empno;
        Connection con = null;
        boolean flag = false;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
            Statement st = con.createStatement();
            if (st.executeUpdate(sql) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
        return flag;
    }

    private Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
    }

    private Collection<Employee> mapToEmployee(ResultSet resultSet) {
        System.out.printf("");
        return null;
    }
}
