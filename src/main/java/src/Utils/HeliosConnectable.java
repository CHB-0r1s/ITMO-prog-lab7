package src.Utils;

import src.CredForPG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface HeliosConnectable {
    static Connection createConToDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs",
                CredForPG.getNameForPG(), CredForPG.getPswdForPG());
    }
}
