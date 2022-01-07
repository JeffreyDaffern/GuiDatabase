package guiDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import guiDatabase.SqlPlayers;
import guiDatabase.SqlTeams;

/**
 * Sets initial database data.
 * @author jeffd
 *
 */
public class DatabaseApp {
	public static void main(String[] args) {

		try (Connection connection = DriverManager.getConnection("jdbc:derby:DemoDatabase;create=true");
				Statement statement = connection.createStatement()) {

//		statement.execute(SqlTeams.dropTeamTable);
//		statement.execute(SqlTeams.createTeamTable);
//		statement.execute(SqlTeams.fillTeamTable);

//		statement.execute(SqlPlayers.dropPlayerTable);
//		statement.execute(SqlPlayers.createPlayerTable);
//		statement.execute(SqlPlayers.fillPlayerTable);		

			ResultSet results = statement.executeQuery(SqlPlayers.selectAll);
			displayResults(results);

			System.out.println();
			results = statement.executeQuery(SqlTeams.selectAll);
			displayResults(results);

			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\ndone.");
	}

	/**
	 * displays table data once they have been created.
	 * @param results
	 */
	private static void displayResults(ResultSet results) {
		try {
			ResultSetMetaData metaData = results.getMetaData();
			printHeader(metaData);
			System.out.println();

			int columnCount = metaData.getColumnCount();

			while (results.next()) {
				for (int i = 1; i <= columnCount; i++) {
					int columnWidth = metaData.getColumnLabel(i).length() + 2;
					System.out.printf("%-" + columnWidth + "s", results.getObject(i));

				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints a header for the tables.
	 * @param metaData
	 */
	private static void printHeader(ResultSetMetaData metaData) {
		int columnCount;
		try {
			columnCount = metaData.getColumnCount();

			// print column headers
			for (int i = 1; i <= columnCount; i++) {
				System.out.print(metaData.getColumnName(i) + " ");
			}
			System.out.println();

			// print dashed line
			for (int i = 1; i <= columnCount; i++) {
				for (int j = 0; j < metaData.getColumnLabel(i).length(); j++) {
					System.out.print('-');
				}
				if (i != columnCount)
					System.out.print("--");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
