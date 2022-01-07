package guiDatabase;

public class SqlPlayers
{
	private String playerID, firstName, lastName, position, teamID, number;

	public static final String createPlayerTable = "CREATE TABLE Player (" + "PlayerID int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY " + "(START WITH 1234, INCREMENT BY 1), " + "FirstName varchar(255), "
			+ "LastName varchar(255), " + "Position varchar(255), " + "TeamID int, " + "Number int)";

	public static String fillPlayerTable = "INSERT INTO Player "
			+ "(FirstName, LastName, Position, TeamID, Number) VALUES " + "('Jaylen', 'Brown', 'Forward', 123, 7), "
			+ "('Tacko', 'Fall', 'Center', 123, 99), " + "('Javonte', 'Green', 'Guard-Forward', 123, 43), "
			+ "('Carsen', 'Edwards', 'Guard', 123, 4), " + "('Gordon', 'Hayward', 'Forward', 123, 20), "
			+ "('Enes', 'Kanter', 'Center', 123, 11), " + "('Kostas', 'Antetokounmpo', 'Forward', 124, 37), "
			+ "('Avery', 'Bradley', 'Guard', 124, 11), " + "('Devontae', 'Cacok', 'Forward', 124, 12), "
			+ "('Kentavious', 'Caldwell-Pope', 'Guard', 124, 1), " + "('Alex', 'Caruso', 'Guard', 124, 4), "
			+ "('Carsen', 'Edwards', 'Guard', 124, 4), " + "('Kostas', 'Antetokounmpo', 'Forward', 124, 37), "
			+ "('Anthony', 'Davis', 'Forward-Center', 124, 3), " + "('Chandler', 'Hutchison', 'Forward', 125, 15), "
			+ "('Ryan', 'Arcidiacono', 'Guard', 125, 51), " + "('Wendell', 'Carter Jr.', 'Center-Forward', 125, 34), "
			+ "('Kris', 'Dunn', 'Guard', 126, 32), " + "('Dragan', 'Bender', 'Forward', 126, 17), "
			+ "('Stephen', 'Curry', 'Guard', 126, 51), " + "('Marquese', 'Chriss', 'Forward', 126, 32), "
			+ "('Ky', 'Bowman', 'Guard', 126, 12), " + "('LaMarcus', 'Aldridge', 'Center-Forward', 127, 12), "
			+ "('Marco', 'Belinelli', 'Guard', 127, 18), " + "('DeMar', 'DeRozan', 'Guard-Forward', 127, 10), "
			+ "('Drew', 'Eubanks', 'Forward-Center', 127, 14)";

	public static final String dropPlayerTable = "DROP TABLE Player";

	public static final String selectAll = "SELECT * FROM Player";

	public SqlPlayers(String playerID, String firstName, String lastName, String position, String teamID, String number)
	{
		super();
		this.playerID = playerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.teamID = teamID;
		this.number = number;
	}

	public String getPlayerID()
	{
		return playerID;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getPosition()
	{
		return position;
	}

	public String getTeamID()
	{
		return teamID;
	}

	public String getNumber()
	{
		return number;
	}
}
