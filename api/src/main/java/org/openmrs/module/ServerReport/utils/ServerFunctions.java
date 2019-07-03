package org.openmrs.module.ServerReport.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerListing;
import org.openmrs.module.ServerReport.api.ServerReportService;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class ServerFunctions {

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public static String splitCamelCase(String s) {
        StringBuilder result = new StringBuilder();
//        for (String w : s.split("((^[a-z]+)|([0-9]+)|([A-Z][a-z]+)|([A-Z]+(?=([A-Z][a-z])|($)|([0-9]))))")) {
        for (String w : s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            result.append(w).append(" ");
        }
        return result.toString().trim();
    }

    public static String getJsonLocationHierarchy() throws IOException {
        return getHierarchyAsJson();
    }

    private static Map<String, Object> toJsonHelper(Location loc) {
        Map<String, Object> ret = new LinkedHashMap<String, Object>();
        Map<String, Object> state = new LinkedHashMap<String, Object>();
        state.put("opened", "true");
//        StringBuilder sb = new StringBuilder(getName(loc));
        ret.put("id", loc.getLocationId().toString());
        ret.put("text", loc.getName());
        ret.put("state", state);
        if (loc.getChildLocations().size() > 0) {
            ret.put("icon", "fas fa-first-aid");
        } else {
            ret.put("icon", "fas fa-hospital");
        }
        if (loc.getChildLocations() != null && loc.getChildLocations().size() > 0) {
            List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
            for (Location child : loc.getChildLocations()) {
                children.add(toJsonHelper(child));
            }
            ret.put("children", children);
        }
        return ret;
    }

    public static String getHierarchyAsJson() throws IOException {
        // TODO fetch all locations at once to avoid n+1 lazy-loads
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Location> locations = new ArrayList<Location>();
        User currentUser = Context.getAuthenticatedUser();
        if (currentUser.isSuperUser()) {
            locations = Context.getLocationService().getAllLocations();
            for (Location loc : locations) {
                if (loc.getParentLocation() == null) {
                    list.add(toJsonHelper(loc));
                }
            }
        } else  {
            List<Location> currentUserLocations = Context.getService(ServerReportService.class).getUserLocations(currentUser);
            List<Integer> locationIds = new ArrayList<Integer>();
            for (Location l : currentUserLocations) {
                locationIds.add(l.getLocationId());
                locations.addAll(getDescendantLocations(l, false));
            }

            for (Location loc : locations) {
                if (locationIds.contains(loc.getLocationId())) {
                    list.add(toJsonHelper(loc));
                }
            }
        }
        // If this gets slow with lots of locations then switch out ObjectMapper for the
        // stream-based version. (But the TODO above is more likely to be a performance hit.)
        StringWriter w = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(w, list);
        return w.toString();
    }

    private static Set<Location> getDescendantLocations(Location location, boolean includeRetired) {
        Set<Location> result = new HashSet<Location>();
        result.add(location);
        for (Location childLocation : location.getChildLocations()) {
            if (!childLocation.isRetired() || includeRetired) {
                if (!result.contains(childLocation)) {
                    result.add(childLocation);
                    result.addAll(getDescendantLocations(childLocation, includeRetired));
                }
            }
        }
        return result;
    }


    public static List<Map<String, Object>> getQueryMap(ServerListing serverListing, Date startDate, Date endDate, Integer locationId) {
        Connection connection = null;
        Statement stmt = null;
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try {

            Properties props = new Properties();
            props.setProperty("driver.class", "com.mysql.jdbc.Driver");
            props.setProperty("driver.url", Context.getRuntimeProperties().getProperty("connection.url"));
            props.setProperty("user", Context.getRuntimeProperties().getProperty("connection.username"));
            props.setProperty("password", Context.getRuntimeProperties().getProperty("connection.password"));

            connection = DriverManager.getConnection(props.getProperty("driver.url"), props);

            String sql = serverListing.getSqlQuery();
            sql = sql.replace(":locationId", locationId.toString());

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

            if (sql.contains(":startDate")) {
                sql = sql.replace(":startDate", "'" + dateFormatter.format(startDate) + "'");
            }
            if (sql.contains(":endDate")) {
                sql = sql.replace(":endDate", "'" + dateFormatter.format(endDate) + "'");
            }
            //System.out.println(sql);
            stmt = connection.createStatement(TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.executeQuery(sql);
            ResultSet resultSet = stmt.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();
//
//            System.out.println("Number of columns : " + colCount);
//            Map<String, String> columns = new HashMap<String, String>();

            for (int i = 1; i <= colCount; i++) {
                System.out.println( i + " - Column Name : " + metaData.getColumnName(i) + " | Column Type : " + metaData.getColumnClassName(i));
                // columns.put(metaData.getColumnName(i), metaData.getColumnClassName(i));
            }

            while (resultSet.next()) {
                Map<String, Object> rs = new LinkedHashMap<String, Object>();

                for (int i = 1; i <= colCount; i++) {

                    System.out.println( i + " - Column Name : " + metaData.getColumnName(i) + " | Column Type : " + metaData.getColumnClassName(i));

                    if (metaData.getColumnClassName(i).equals("java.lang.String")) {
                        rs.put(metaData.getColumnName(i), resultSet.getString(i));
                    } else if (metaData.getColumnClassName(i).equals("java.lang.Long")) {
                        rs.put(metaData.getColumnName(i), resultSet.getLong(i));
                    } else if (metaData.getColumnClassName(i).equals("java.lang.Double")) {
                        rs.put(metaData.getColumnName(i), resultSet.getDouble(i));
                    } else if (metaData.getColumnClassName(i).equals("java.sql.Timestamp")) {
                        if (resultSet.getTimestamp(i) != null)
                            rs.put(metaData.getColumnName(i), new Date(resultSet.getTimestamp(i).getTime()));
                        else
                            rs.put(metaData.getColumnName(i), null);
                    } else if (metaData.getColumnClassName(i).equals("java.sql.Date")) {
                        if (resultSet.getDate(i) != null)
                            rs.put(metaData.getColumnName(i), new Date(resultSet.getDate(i).getTime()));
                        else
                            rs.put(metaData.getColumnName(i), null);
                    } else if (metaData.getColumnClassName(i).equals("java.lang.Boolean")) {
                        rs.put(metaData.getColumnName(i), resultSet.getBoolean(i));
                    } else if (metaData.getColumnClassName(i).equals("java.lang.Integer")) {
                        rs.put(metaData.getColumnName(i), resultSet.getInt(i));
                    }
                }

                result.add(rs);

            }

            return result;

        } catch (Exception e) {
            System.err.println("Erreur exécution : " + e.getLocalizedMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
