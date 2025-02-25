package com.revature.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class DatabaseScriptRunner {
    public static void main(String[] args) {
        Path sqlPath = Paths.get("src/main/resources/Bank-setup-reset-script.sql");
        try{
            try(
                    Connection connection = DatabaseConnector.createConnection();
                    Stream<String> lines = Files.lines(sqlPath);
            ){
                // by setting auto commit to false we can execute multiple statements
                // and commit them all together, ensuring all data
                // or no data is updated
                connection.setAutoCommit(false);

                StringBuilder sqlBuilder = new StringBuilder();

                lines.forEach(line -> sqlBuilder.append(line));
                String sql = sqlBuilder.toString();

                String[] statements = sql.split(";\\R");

                for(String statement : statements){
                    Statement stmt =  connection.createStatement();
                    stmt.executeUpdate(statement);
                }
                connection.commit();
            }
        }catch (SQLException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
