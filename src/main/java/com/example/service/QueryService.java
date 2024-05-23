package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final JdbcTemplate jdbcTemplate;

    public int query_1_1(UUID status_id) {
        String sql = "SELECT COUNT(c.contract_id) AS contract_count " +
                "FROM \"insurance\".\"contract\" c " +
                "JOIN \"insurance\".\"client\" cl ON c.client_id = cl.client_id " +
                "WHERE cl.status_id = ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{status_id}, Integer.class);
    }

    public int query_1_2(UUID company_id) {
        String sql = "SELECT COUNT(c.contract_id) AS contract_count " +
                "FROM \"insurance\".\"contract\" c " +
                "JOIN \"insurance\".\"branch\" br ON c.branch_id = br.branch_id " +
                "WHERE br.company_id = ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{company_id}, Integer.class);
    }

    public int query_1_3(Date date) {
        String sql = "SELECT COUNT(*) AS count_of_records " +
                "FROM \"insurance\".\"company\" AS c " +
                "JOIN \"insurance\".\"branch\" AS b ON c.company_id = b.company_id " +
                "JOIN \"insurance\".\"contract\" AS co ON b.branch_id = co.branch_id " +
                "JOIN \"insurance\".\"client\" AS cl ON co.client_id = cl.client_id " +
                "JOIN \"insurance\".\"status\" AS s ON cl.status_id = s.status_id " +
                "WHERE co.date >= ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
    }

    public int query_1_4(Date date) {
        String sql = "SELECT COUNT(*) AS count_of_records " +
                "FROM \"insurance\".\"company\" AS c " +
                "JOIN \"insurance\".\"branch\" AS b ON c.company_id = b.company_id " +
                "JOIN \"insurance\".\"contract\" AS co ON b.branch_id = co.branch_id " +
                "JOIN \"insurance\".\"client\" AS cl ON co.client_id = cl.client_id " +
                "JOIN \"insurance\".\"status\" AS s ON cl.status_id = s.status_id " +
                "WHERE co.date <= ? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
    }

    public int query_2_1() {
        String sql = "SELECT COUNT(*) " +
                "FROM \"insurance\".\"contract\" " +
                "JOIN \"insurance\".\"view\" ON contract.view_id = view.view_id " +
                "JOIN \"insurance\".\"client\" ON contract.client_id = client.client_id " +
                "JOIN \"insurance\".\"status\" ON client.status_id = status.status_id";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_2_2() {
        String sql = "SELECT COUNT(*) AS branch_without_company_count " +
                "FROM \"insurance\".\"branch\" b " +
                "LEFT JOIN \"insurance\".\"company\" c ON b.company_id = c.company_id " +
                "WHERE c.company_id IS NULL";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_2_3() {
        String sql = "SELECT COUNT(*) AS clients_without_status " +
                "FROM \"insurance\".\"client\" c " +
                "LEFT JOIN \"insurance\".\"status\" s ON c.status_id = c.status_id " +
                "WHERE c.status_id IS NULL";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_3() {
        String sql = "SELECT COUNT(b.branch_id) " +
                "FROM \"insurance\".\"branch\" b " +
                "LEFT JOIN \"insurance\".\"company\" c ON b.company_id = c.company_id " +
                "WHERE c.company_id IS NULL";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_4() {
        String sql = "SELECT COUNT(cl.client_id) " +
                "FROM \"insurance\".\"client\" cl " +
                "RIGHT JOIN \"insurance\".\"status\" st ON cl.status_id = st.status_id " +
                "WHERE st.status_id IS NULL";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


    public int query_5(UUID branch_id) {
        String sql = "SELECT COUNT(*) AS contract_count " +
                "FROM \"insurance\".\"contract\" " +
                "WHERE branch_id = ( " +
                "    SELECT branch_id " +
                "    FROM \"insurance\".\"branch\" " +
                "    WHERE branch_id = ? " +
                ")";

        return jdbcTemplate.queryForObject(sql, new Object[]{branch_id}, Integer.class);
    }

    public int query_6() {
        String sql = "SELECT COUNT(*) FROM \"insurance\".\"contract\" ";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_7(UUID company_id) {
        String sql = "SELECT COUNT(ct.contract_id) AS contract_count " +
                "FROM \"insurance\".\"company\" AS c " +
                "JOIN \"insurance\".\"branch\" AS b ON c.company_id = b.company_id " +
                "JOIN \"insurance\".\"contract\" AS ct ON b.branch_id = ct.branch_id " +
                "WHERE c.company_id = ? " +
                "GROUP BY c.company_id, c.company_name";

        List<Integer> result = jdbcTemplate.queryForList(sql, new Object[]{company_id}, Integer.class);

        // Проверяем, есть ли результаты запроса
        if (!result.isEmpty()) {
            // Возвращаем первый элемент списка, так как он содержит результат COUNT
            return result.get(0);
        } else {
            // Если результаты запроса пусты, возвращаем 0 или обрабатываем по вашему усмотрению
            return 0;
        }
    }


    public List<Map<String, Object>> query_8(UUID company_id) {
        String sql = "SELECT c.company_name, COALESCE(SUM(CAST(ct.summ AS NUMERIC)), 0) AS total_contracts_sum " +
                "FROM \"insurance\".\"branch\" b " +
                "LEFT JOIN \"insurance\".\"company\" c ON b.company_id = c.company_id " +
                "LEFT JOIN \"insurance\".\"contract\" ct ON b.branch_id = ct.branch_id " +
                "WHERE c.company_id = ? " +
                "GROUP BY c.company_name";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, new Object[]{company_id});

        return result;
    }




    public int query_9() {
        String sql = "SELECT COUNT(c.contract_id) AS total_contract_count " +
                "FROM \"insurance\".\"view\" v " +
                "LEFT JOIN \"insurance\".\"contract\" c ON v.view_id = c.view_id";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_10(UUID status_id) {
        String sql = "SELECT COUNT(*) AS ClientCount " +
                "FROM \"insurance\".\"client\" " +
                "WHERE status_id = ( " +
                "  SELECT status_id " +
                "  FROM \"insurance\".\"status\" " +
                "  WHERE status_id = ? " +
                ")";

        return jdbcTemplate.queryForObject(sql, new Object[]{status_id}, Integer.class);
    }

    public int query_11() {
        String sql = "SELECT COUNT(*) AS total_contracts " +
                "FROM \"insurance\".\"contract\" c " +
                "INNER JOIN \"insurance\".\"client\" cl ON c.client_id = cl.client_id " +
                "INNER JOIN \"insurance\".\"status\" s ON cl.status_id = s.status_id";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Map<String, Integer> query_12() {
        String sql = "SELECT " +
                "  COUNT(*) AS \"Всего контрактов\", " +
                "  COUNT(*) FILTER (WHERE contract_status_id = '4e35c87a-5b33-4957-bbaf-10c052a2f66e') AS \"Активен\", " +
                "  COUNT(*) FILTER (WHERE contract_status_id = 'd5a3d63d-d6ee-4e50-9d8f-830d6b9b5f58') AS \"Завершен\" " +
                "FROM \"insurance\".\"contract\"";

        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            Map<String, Integer> contractCounts = new HashMap<>();
            contractCounts.put("Всего контрактов", resultSet.getInt("Всего контрактов"));
            contractCounts.put("Активен", resultSet.getInt("Активен"));
            contractCounts.put("Завершен", resultSet.getInt("Завершен"));
            return contractCounts;
        });
    }

    public int query_13_1(int sum) {
        String sql = "SELECT COUNT(*) AS total_contracts " +
                "FROM \"insurance\".\"contract\" " +
                "WHERE summ = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{sum}, Integer.class);
    }

    public int query_13_2(String value) {
        String sql = "SELECT COUNT(*) AS total_contracts " +
                "FROM \"insurance\".\"contract\" " +
                "JOIN view ON contract.view_id = view.view_id " +
                "WHERE view.value LIKE ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{value + "%"}, Integer.class);
    }

    public int query_13_3(Date date) {
        String sql = "SELECT COUNT(*) AS total_contracts " +
                "FROM \"insurance\".\"contract\" " +
                "WHERE \"date\" = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{date}, Integer.class);
    }

    public int query_13_4(String value) {
        String sql = "SELECT COUNT(*) AS \"контракты без введённого вида\" " +
                "FROM \"insurance\".\"contract\" c " +
                "WHERE NOT EXISTS ( " +
                "    SELECT * " +
                "    FROM \"insurance\".\"view\" v " +
                "    WHERE v.view_id = c.view_id " +
                "    AND v.value LIKE ? " +
                ")";

        return jdbcTemplate.queryForObject(sql, new Object[]{value + "%"}, Integer.class);
    }

    public int query_14_1(String firstName) {
        String sql = "SELECT COUNT(*) " +
                "FROM \"insurance\".\"contract\" " +
                "WHERE client_id IN ( " +
                "    SELECT client_id " +
                "    FROM \"insurance\".\"client\" " +
                "    WHERE firstname = ? " +
                ")";

        return jdbcTemplate.queryForObject(sql, new Object[]{firstName}, Integer.class);
    }

    public int query_14_2() {
        String sql = "SELECT COUNT(*) " +
                "FROM \"insurance\".\"contract\" " +
                "WHERE contract_status_id IN ( " +
                "    SELECT contract_status_id " +
                "    FROM \"insurance\".\"contract_status\" " +
                "    WHERE contract_status_id NOT IN ( " +
                "        SELECT contract_status_id " +
                "        FROM \"insurance\".\"contract_status\" " +
                "        WHERE \"value\" = 'Завершен' " +
                "    ) " +
                ")";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_14_3() {
        String sql = "SELECT COUNT(CASE WHEN CAST(summ AS INTEGER) >= 5000 THEN 1 END) AS \"больше 5000\" " +
                "FROM \"insurance\".\"contract\" ";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int query_14_4() {
        String sql = "SELECT ROUND(AVG(total_sum)) AS average_sum_of_contracts " +
                "FROM ( " +
                "    SELECT SUM(CAST(summ AS NUMERIC)) AS total_sum " +
                "    FROM \"insurance\".\"contract\" " +
                "    GROUP BY client_id " +
                ") AS subquery";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }


}
