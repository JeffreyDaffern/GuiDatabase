package guiDatabase;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

/**
 * Creates a user interface that accesses a database.
 * 
 * @author jeffd
 *
 */
public class MainGui extends JFrame
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane, addAndRemovePanel, tableTeamsPanel, tablePlayersPanel;
	private JTextField textFieldPlayers, textFieldFirstName, textFieldTeamID, textFieldNumber, textFieldPosition;
	private JTextField textFieldLastName, textFieldLastNameRemove, textFieldFirstNameRemove, textFieldTeams;
	private JLabel lblFirstName, lblLastName, lblPosition, lblTeamID, lblNumber, lblFirstNameRemove, lblLastNameRemove;
	private JTable tableTeams, tablePlayers, tableMulti;
	private JComboBox<String> comboBoxTeams, comboBoxPlayers, comboBoxAscending;
	private DefaultTableModel playersModel, teamsModel, multiModel;
	private ResultSet results;
	private String queryTeams, queryPlayers, queryMulti, tempPlayers;
	private ArrayList<SqlPlayers> playersList;
	private ArrayList<SqlTeams> teamsList;
	private ArrayList<SqlMulti> multiList;
	private boolean teamsTable, playersTable, addPlayer, editPlayer, multi;
	private SqlTeams team;
	private SqlPlayers player;
	private SqlMulti multiOccurence;
	private JScrollPane playersScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainGui frame = new MainGui();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame and the panels within the frame.
	 * 
	 * Create the frame.
	 */
	public MainGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		createSearchPanel();
		createTablePanel();
		addAndRemovePanel();
		startup();
	}

	/**
	 * Creates panels that hold the Tables and calls methods to setup the tables.
	 */
	public void createTablePanel()
	{
		tableTeamsPanel = new JPanel();
		contentPane.add(tableTeamsPanel, BorderLayout.WEST);

		tablePlayersPanel = new JPanel();
		tablePlayersPanel.setSize(1000, 1000);
		contentPane.add(tablePlayersPanel, BorderLayout.CENTER);
		tableTeamsPanel.setLayout(new GridLayout(0, 1, 0, 0));

		tableTeams = new JTable();
		tableTeams.setFillsViewportHeight(true);
		tableTeamsPanel.add(new JScrollPane(tableTeams));
		tablePlayersPanel.setLayout(new GridLayout(0, 1, 0, 0));

		tablePlayers = new JTable();
		tablePlayers.setFillsViewportHeight(true);
		playersScrollPane = new JScrollPane(tablePlayers);
		playersScrollPane.setBounds(0, 0, 500, 500);
		tablePlayersPanel.add(playersScrollPane);

		tableMulti = new JTable();
		tableMulti.setFillsViewportHeight(true);

		setColumns();
	}

	/**
	 * Changes the model so that multiTable displays rather than playersTable.
	 */
	public void createMultiTable()
	{
		tablePlayers.setModel(multiModel);
		tablePlayers.invalidate();
	}

	/**
	 * Sets boolean values and displays initial table data.
	 */
	public void startup()
	{
		queryPlayers = "SELECT * FROM PLAYER";
		queryTeams = "SELECT * FROM TEAM";
		teamsTable = true;
		playersTable = true;
		editPlayer = false;
		runQuery();
	}

	/**
	 * Creates bottom panel with textFields, buttons, and labels.
	 */
	public void addAndRemovePanel()
	{
		addAndRemovePanel = new JPanel();
		contentPane.add(addAndRemovePanel, BorderLayout.SOUTH);
		addAndRemovePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblFirstName = new JLabel("First Name");
		addAndRemovePanel.add(lblFirstName);

		textFieldFirstName = new JTextField();
		addAndRemovePanel.add(textFieldFirstName);
		textFieldFirstName.setColumns(5);

		lblLastName = new JLabel("LastName");
		addAndRemovePanel.add(lblLastName);

		textFieldLastName = new JTextField();
		addAndRemovePanel.add(textFieldLastName);
		textFieldLastName.setColumns(5);

		lblPosition = new JLabel("Position");
		addAndRemovePanel.add(lblPosition);

		textFieldPosition = new JTextField();
		addAndRemovePanel.add(textFieldPosition);
		textFieldPosition.setColumns(5);

		lblTeamID = new JLabel("TeamID");
		addAndRemovePanel.add(lblTeamID);

		textFieldTeamID = new JTextField();
		addAndRemovePanel.add(textFieldTeamID);
		textFieldTeamID.setColumns(5);

		lblNumber = new JLabel("Number");
		addAndRemovePanel.add(lblNumber);

		textFieldNumber = new JTextField();
		addAndRemovePanel.add(textFieldNumber);
		textFieldNumber.setColumns(5);

		JButton btnAddPlayer = new JButton("Add Player");
		btnAddPlayer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addPlayer = true;
				modifyPlayer();
			}
		});
		addAndRemovePanel.add(btnAddPlayer);

		JButton btnEditPlayer = new JButton("Edit Player");
		btnEditPlayer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addPlayer = false;
				editPlayer = true;
				modifyPlayer();
			}
		});
		addAndRemovePanel.add(btnEditPlayer);

		lblFirstNameRemove = new JLabel("First Name");
		addAndRemovePanel.add(lblFirstNameRemove);

		textFieldFirstNameRemove = new JTextField();
		addAndRemovePanel.add(textFieldFirstNameRemove);
		textFieldFirstNameRemove.setColumns(5);

		lblLastNameRemove = new JLabel("Last Name");
		addAndRemovePanel.add(lblLastNameRemove);

		textFieldLastNameRemove = new JTextField();
		addAndRemovePanel.add(textFieldLastNameRemove);
		textFieldLastNameRemove.setColumns(5);

		JButton btnRemovePlayer = new JButton("Remove Player");
		btnRemovePlayer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addPlayer = false;
				modifyPlayer();
			}
		});
		addAndRemovePanel.add(btnRemovePlayer);
	}

	/**
	 * Creates the search panel which has fields for searching the database.
	 */
	public void createSearchPanel()
	{
		JPanel searchPanel = new JPanel();
		contentPane.add(searchPanel, BorderLayout.NORTH);
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Row or Table to Search:   |  ");
		searchPanel.add(lblNewLabel);

		JLabel lblTeamsCombo = new JLabel("Teams");
		searchPanel.add(lblTeamsCombo);

		String[] comboTeams =
		{ "", "TeamID", "Name", "City", "ChampionshipWins" };
		comboBoxTeams = new JComboBox<>(comboTeams);
		searchPanel.add(comboBoxTeams);

		JLabel lblPlayersCombo = new JLabel("|   Players");
		searchPanel.add(lblPlayersCombo);

		String[] comboPlayers =
		{ "", "PlayerID", "FirstName", "LastName", "Position", "TeamID", "Number" };

		comboBoxPlayers = new JComboBox<>(comboPlayers);
		searchPanel.add(comboBoxPlayers);

		JLabel lblTeamsText = new JLabel("Teams:");
		searchPanel.add(lblTeamsText);

		textFieldTeams = new JTextField();
		searchPanel.add(textFieldTeams);
		textFieldTeams.setColumns(8);

		JLabel lblPlayersText = new JLabel("Players");
		searchPanel.add(lblPlayersText);

		textFieldPlayers = new JTextField();
		searchPanel.add(textFieldPlayers);
		textFieldPlayers.setColumns(8);

		String[] ascending =
		{ "Ascending", "Descending" };
		comboBoxAscending = new JComboBox<>(ascending);
		comboBoxAscending.setSelectedIndex(0);
		searchPanel.add(comboBoxAscending);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tablePlayers.setModel(playersModel);
				tablePlayersPanel.invalidate();

				if (comboBoxTeams.getSelectedIndex() != 0 && comboBoxPlayers.getSelectedIndex() != 0)
				{
					createMultiTable();
					queryMulti = queryMulti(textFieldPlayers.getText());
					runQuery();
				} else if (comboBoxPlayers.getSelectedIndex() != 0)
				{
					queryPlayers = queryPlayers(textFieldPlayers.getText());
					runQuery();
				} else if (comboBoxTeams.getSelectedIndex() != 0)
				{
					queryTeams = queryTeams(textFieldTeams.getText());
					runQuery();
				} else
				{
					if (comboBoxAscending.getSelectedIndex() == 0)
					{
						queryPlayers = "SELECT * FROM PLAYER ORDER BY PLAYERID ASC";
						queryTeams = "SELECT * FROM TEAM ORDER BY TEAMID ASC";
					} else
					{
						
							queryPlayers = "SELECT * FROM PLAYER ORDER BY PLAYERID DESC";
						queryTeams = "SELECT * FROM TEAM ORDER BY TEAMID ASC";
					}
					teamsTable = true;
					playersTable = true;
					runQuery();
				}

				clearSearchPanel();
			}
		});

		searchPanel.add(btnSearch);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				clearSearchPanel();
				startup();
			}

		});
		searchPanel.add(btnClear);
	}

	/**
	 * Constructs a query to be executed on the players table.
	 * 
	 * @param text textField data
	 * @return
	 */
	public String queryPlayers(String text)
	{
		playersTable = true;
		return "SELECT * FROM Player WHERE " + playersInteger(text) + determineOrder();
	}

	/**
	 * Constructs a query to be executed on the players table.
	 * 
	 * @param textField data
	 * @return
	 */
	public String queryMulti(String text)
	{
		multi = true;
		return "SELECT PLAYERID, P.FIRSTNAME, P.LASTNAME, P.POSITION, P.NUMBER, T.TEAMID, T.NAME, T.CITY, "
				+ "T.CHAMPIONSHIPWINS FROM Player P INNER JOIN TEAM T ON P.TEAMID = T.TEAMID WHERE "
				+ playersInteger(text) + determineOrder();
	}

	/**
	 * Constructs part of a query based on whether the fields are integers or
	 * Strings by adding or removing single quotes.
	 * 
	 * @param textField data
	 * @return
	 */
	public String playersInteger(String text)
	{
		String tempPlayers;
		if (comboBoxPlayers.getSelectedIndex() != 1 && comboBoxPlayers.getSelectedIndex() != 5
				&& comboBoxPlayers.getSelectedIndex() != 6)
		{
			if (multi)
			{
				tempPlayers = comboBoxPlayers.getSelectedItem().toString() + " = '" + text + "' AND T."
						+ teamsInteger(textFieldTeams.getText());
			} else
			{
				tempPlayers = comboBoxPlayers.getSelectedItem().toString() + " = '" + text + "'";
			}
		} else
		{
			if (multi)
			{
				tempPlayers = comboBoxPlayers.getSelectedItem().toString() + " = " + text + " AND T."
						+ teamsInteger(textFieldTeams.getText());
			} else
			{
				tempPlayers = comboBoxPlayers.getSelectedItem().toString() + " = " + text;
			}
		}
		return tempPlayers;
	}

	/**
	 * Constructs part of a query based on whether the fields are integers or
	 * Strings by adding or removing single quotes.
	 * 
	 * @param textField data
	 * @return
	 */
	public String teamsInteger(String text)
	{
		String tempTeams;
		if (comboBoxTeams.getSelectedIndex() != 1 && comboBoxTeams.getSelectedIndex() != 4)
		{
			tempTeams = comboBoxTeams.getSelectedItem().toString() + " = '" + text + "'";
		} else
		{
			tempTeams = comboBoxTeams.getSelectedItem().toString() + " = " + text;
		}
		return tempTeams;
	}

	/**
	 * Sets the query to ascending or descending order.
	 * 
	 * @return
	 */
	public String determineOrder()
	{
		if (comboBoxAscending.getSelectedIndex() == 0)
		{
			tempPlayers = " ORDER BY PlayerID ASC";
		} else
		{
			tempPlayers = " ORDER BY PlayerID DESC";
		}

		return tempPlayers;
	}

	/**
	 * Constructs a query for the teams table.
	 * 
	 * @param textField data
	 * @return
	 */
	public String queryTeams(String text)
	{
		teamsTable = true;
		return "SELECT * FROM Team WHERE " + teamsInteger(text);
	}

	/**
	 * Constructs a query that will add a player to the database.
	 * 
	 * @return
	 */
	public String addPlayer()
	{
		addPlayer = true;
		return "INSERT INTO Player (FirstName, LastName, Position, TeamID, Number) VALUES " + "('"
				+ textFieldFirstName.getText() + "', '" + textFieldLastName.getText() + "', '"
				+ textFieldPosition.getText() + "', " + textFieldTeamID.getText() + ", " + textFieldNumber.getText()
				+ ")";
	}

	/**
	 * Constructs a query that will edit a player within the database.
	 * 
	 * @return
	 */
	public String editPlayer()
	{
		return "UPDATE PLAYER SET FirstName = '" + textFieldFirstName.getText() + "', LastName = '"
				+ textFieldLastName.getText() + "', Position = '" + textFieldPosition.getText() + "', TeamID = "
				+ Integer.parseInt(textFieldTeamID.getText()) + ", Number = "
				+ Integer.parseInt(textFieldNumber.getText()) + " WHERE FirstName = '" + textFieldFirstName.getText()
				+ "' AND LastName = '" + textFieldLastName.getText() + "'";
	}

	/**
	 * Constructs a query that will add a player to the database.
	 * 
	 * @return
	 */
	public String removePlayer()
	{
		playersTable = true;
		return "DELETE FROM PLAYER WHERE FIRSTNAME = '" + textFieldFirstNameRemove.getText() + "' AND LASTNAME = '"
				+ textFieldLastNameRemove.getText() + "'";
	}

	/**
	 * Runs queries through the database that add, delete, or modify table rows.
	 */
	public void modifyPlayer()
	{
		try (Connection connection = DriverManager.getConnection("jdbc:derby:DemoDatabase;create=true");
				Statement statement = connection.createStatement())
		{
			if (addPlayer)
			{
				queryPlayers = addPlayer();
				addPlayer = false;
			} else if (editPlayer)
			{
				queryPlayers = editPlayer();
				editPlayer = false;
			} else
			{
				queryPlayers = removePlayer();
			}

			statement.executeUpdate(queryPlayers);
			results = statement.executeQuery("SELECT * FROM Player");

			playersList = new ArrayList<>();
			SqlPlayers player;

			while (results.next())
			{
				player = new SqlPlayers(results.getString("PlayerID"), results.getString("FirstName"),
						results.getString("LastName"), results.getString("Position"), results.getString("TeamID"),
						results.getString("Number"));
				playersList.add(player);
			}

			fillTables();
			clearAddPanel();
			startup();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Connects to the database and runs the query fields.
	 */
	public void runQuery()
	{
		try (Connection connection = DriverManager.getConnection("jdbc:derby:DemoDatabase;create=true");
				Statement statement = connection.createStatement())
		{
			if (multi)
			{
				results = statement.executeQuery(queryMulti);
				multiList = new ArrayList<>();

				while (results.next())
				{
					multiOccurence = new SqlMulti(results.getString("PlayerID"), results.getString("FirstName"),
							results.getString("LastName"), results.getString("Position"), results.getString("Number"),
							results.getString("TeamID"), results.getString("Name"), results.getString("City"),
							results.getString("ChampionshipWins"));
					multiList.add(multiOccurence);
				}

			} else
			{
				if (teamsTable)

				{
					results = statement.executeQuery(queryTeams);
					teamsList = new ArrayList<>();

					while (results.next())
					{
						team = new SqlTeams(results.getString("TeamID"), results.getString("Name"),
								results.getString("City"), results.getString("ChampionshipWins"));
						teamsList.add(team);
					}
				}
				if (playersTable)
				{

					results = statement.executeQuery(queryPlayers);
					playersList = new ArrayList<>();

					while (results.next())
					{
						player = new SqlPlayers(results.getString("PlayerID"), results.getString("FirstName"),
								results.getString("LastName"), results.getString("Position"),
								results.getString("TeamID"), results.getString("Number"));
						playersList.add(player);
					}
				}
			}
			fillTables();

		} catch (

		SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Fills the tables based on data received from the database.
	 */
	public void fillTables()
	{
		playersModel.setRowCount(0);
		teamsModel.setRowCount(0);
		multiModel.setRowCount(0);

		if (multi)
		{
			Object[] MultiRow = new Object[9];
			for (int i = 0; i < multiList.size(); i++)
			{
				MultiRow[0] = multiList.get(i).getPlayerID();
				MultiRow[1] = multiList.get(i).getFirstName();
				MultiRow[2] = multiList.get(i).getLastName();
				MultiRow[3] = multiList.get(i).getPosition();
				MultiRow[4] = multiList.get(i).getNumber();
				MultiRow[5] = multiList.get(i).getTeamID();
				MultiRow[6] = multiList.get(i).getName();
				MultiRow[7] = multiList.get(i).getCity();
				MultiRow[8] = multiList.get(i).getChampionshipWins();

				multiModel.addRow(MultiRow);
			}
			multi = false;
		}
		if (teamsTable)
		{
			Object[] teamRow = new Object[4];
			for (int i = 0; i < teamsList.size(); i++)
			{
				teamRow[0] = teamsList.get(i).getTeamID();
				teamRow[1] = teamsList.get(i).getName();
				teamRow[2] = teamsList.get(i).getCity();
				teamRow[3] = teamsList.get(i).getChampionshipWins();
				teamsModel.addRow(teamRow);
			}
			teamsTable = false;
		}
		if (playersTable)
		{
			Object[] PlayersRow = new Object[6];
			for (int i = 0; i < playersList.size(); i++)
			{
				PlayersRow[0] = playersList.get(i).getPlayerID();
				PlayersRow[1] = playersList.get(i).getFirstName();
				PlayersRow[2] = playersList.get(i).getLastName();
				PlayersRow[3] = playersList.get(i).getPosition();
				PlayersRow[4] = playersList.get(i).getTeamID();
				PlayersRow[5] = playersList.get(i).getNumber();

				playersModel.addRow(PlayersRow);
			}
			playersTable = false;
		}

	}

	/**
	 * Sets the column names for each table and assigns them to the table models.
	 */
	public void setColumns()
	{
		String[] teamsColumnNames =
		{ "TeamID", "Name", "City", "ChampionshipWins" };
		String[] playersColumnNames =
		{ "PlayerID", "FirstName", "LastName", "Position", "TeamID", "Number" };
		String[] MultiColumnNames =
		{ "PlayerID", "FirstName", "LastName", "Position", "Number", "TeamID", "Name", "City", "ChampionshipWins" };

		playersModel = (DefaultTableModel) tablePlayers.getModel();
		teamsModel = (DefaultTableModel) tableTeams.getModel();
		multiModel = (DefaultTableModel) tableMulti.getModel();

		for (int i = 0; i < playersColumnNames.length; i++)
		{
			playersModel.addColumn(playersColumnNames[i]);
		}
		for (int i = 0; i < teamsColumnNames.length; i++)
		{
			teamsModel.addColumn(teamsColumnNames[i]);
		}
		for (int i = 0; i < MultiColumnNames.length; i++)
		{
			multiModel.addColumn(MultiColumnNames[i]);
		}
	}

	/**
	 * Resets the textFields and comboBoxes in the search panel to their original
	 * states.
	 */
	public void clearSearchPanel()
	{
		comboBoxPlayers.setSelectedIndex(0);
		comboBoxTeams.setSelectedIndex(0);
		textFieldTeams.setText(null);
		textFieldPlayers.setText(null);
		comboBoxAscending.setSelectedIndex(0);
	}

	/**
	 * Resets the textFields and comboBoxes in the add and delete panel to their
	 * original states.
	 */
	public void clearAddPanel()
	{
		textFieldFirstName.setText(null);
		textFieldFirstNameRemove.setText(null);
		textFieldLastName.setText(null);
		textFieldLastNameRemove.setText(null);
		textFieldNumber.setText(null);
		textFieldPosition.setText(null);
		textFieldTeamID.setText(null);
	}
}
