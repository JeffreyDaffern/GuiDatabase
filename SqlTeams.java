package guiDatabase;

public class SqlTeams
{
	private String teamID, name, city, championshipWins;

	public static final String createTeamTable = "CREATE TABLE Team (" + "TeamID int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY " + "(START WITH 123, INCREMENT BY 1), " + "Name varchar(255), "
			+ "City varchar(255), " + "ChampionshipWins int)";

	public static String fillTeamTable = "INSERT INTO Team " + "(Name, City, ChampionshipWins) VALUES "
			+ "('Celtics', 'Boston', 17), " + "('Lakers', 'Los Angeles', 16), " + "('Bulls', 'Chicago', 6), "
			+ "('Golden State Warriors', 'San Francisco', 6), " + "('Spurs', 'San Antonio', 5)";

	public static final String dropTeamTable = "DROP TABLE Team";

	public static final String selectAll = "SELECT * FROM Team";

	public SqlTeams(String teamID, String name, String city, String championshipWins)
	{
		super();
		this.teamID = teamID;
		this.name = name;
		this.city = city;
		this.championshipWins = championshipWins;
	}

	public String getTeamID()
	{
		return teamID;
	}

	public String getName()
	{
		return name;
	}

	public String getCity()
	{
		return city;
	}

	public String getChampionshipWins()
	{
		return championshipWins;
	}

}
