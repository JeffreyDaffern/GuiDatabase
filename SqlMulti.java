package guiDatabase;

public class SqlMulti
{
	private String playerID, firstName, lastName, position, number, teamID, name, city, championshipWins;

	public SqlMulti(String playerID, String firstName, String lastName, String position, String number, String teamID,
			String name, String city, String championshipWins)
	{
		super();
		this.playerID = playerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.number = number;
		this.teamID = teamID;
		this.name = name;
		this.city = city;
		this.championshipWins = championshipWins;
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

	public String getNumber()
	{
		return number;
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
