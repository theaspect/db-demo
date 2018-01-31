package com.example.demo.request.slide_7_report_y_percent;

public class TestReport {
    publicList<SankiDto> getSankiReport() throwsException {
        String sql = "SELECT cl.name, c.amount, c.contract_typeFROM contract c" +
                "JOIN client cl ON c.client_id= cl.id" +
                "UNION ALL " +
                "SELECT c.contract_type, c.amount, e.first_name|| ' ' || e.last_name" +
                "FROM contract c" +
                "JOIN employee e ON c.account_id= e.id";
        try (Connection con = getConnection()) {
            List<SankiDto> result = newLinkedList <>(); PreparedStatementps = con.prepareStatement(sql);
            ResultSetrs = ps.executeQuery();
            while (rs.next()) {
                result.add(newSankiDto(rs.getString(1), rs.getLong(2), rs.getString(3)));
            }
            returnresult;
        }
    }
}
