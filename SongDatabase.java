import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A class that represents a database of songs.
 * 
 * @author Colleen Wurden
 *
 */
public class SongDatabase extends Application implements EventHandler<ActionEvent>
{
    private Stage myStage;
    private ObservableList<String> songsOL  = FXCollections.observableArrayList();
    private ArrayList<Song> songsALSong  = new ArrayList<Song>();
    private ComboBox<String> cbSongNames  = new ComboBox<String>();

    private TextField itemCodeTextBox = new TextField();
    private TextField descriptionTextBox = new TextField();
    private TextField artistTextBox = new TextField();
    private TextField albumTextBox = new TextField();
    private TextField priceTextBox = new TextField();
    private TextField songNameTextBox = new TextField("type Song info");

    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnExit;
    private Button btnAccept;
    private Button btnCancel;

    private String songName;
    private String songID;
    private String songDescription;
    private String songArtist;
    private String songAlbum;
    private String songPrice;

    private String currentSongNameCB;
    private Song oldSong;

    private boolean deleteClicked = false;
    private boolean editClicked = false;
    private boolean addClicked = false;
    private Song newSong;

    public static void main(String[] args)
    {
        if (args.length < 1) // number of records is optional
        { // if not, deal with it
            System.out.println("usage:  SongDatabase <database file path>");
        }
        launch(args);
    }

    @Override
    public void start(Stage myStage) // throws Exception
    {
        songsALSong = loadDBFromFile();
        cbSongNames = loadComboBox(songsALSong);

        setTheStage(myStage);
        setButtonsAtStart();// enable and disable buttons at start of program
        cbSongNames.setOnAction(e ->
        {
            clickComboBox();
        });

        btnAdd.setOnAction(e -> clickAddButton());
        btnAccept.setOnAction(e -> clickAcceptBtn());
        btnCancel.setOnAction(e -> clickCancelBtn());
        btnEdit.setOnAction(e -> clickEditBtn());
        btnDelete.setOnAction(e -> clickDeleteBtn());
        btnExit.setOnAction(e -> clickExitBtn());

    }// end of start(Stage myStage)

    public ArrayList<Song> loadDBFromFile()// gets song list from textfile
    {
        int response = -1;
        Scanner input = null;
        try
        {
            input = new Scanner(new File("database.txt"));
            input.useDelimiter("\n");
            songsALSong.clear();
            while (input.hasNextLine())
            {
                String line = input.nextLine();
                String[] tokens = line.split(",", -1);
                songsALSong.add(new Song(tokens[0].trim(), tokens[1].trim(),
                        tokens[2].trim(), tokens[3].trim(), tokens[4].trim(),
                        tokens[5].trim()));
            }
        } catch (FileNotFoundException e1)
        {
            System.out.println("Input file not found." + " Error:  " +
        e1.getMessage());
            System.out.println("Would you like to create and empty" +
            " database? ");
            System.out.println("Enter 1 for yes and enter 2 for no.");
            response = Integer.parseInt(input.nextLine());
            if (response == 1)
            {
                songsOL = FXCollections.observableArrayList();
            } else if (response == 2)
            {
                System.out.println("Program exited");
                System.exit(-1);
            }
        }
        return songsALSong;
    }

    public ComboBox<String> loadComboBox(ArrayList<Song> songsALSong)
    {
        songsOL.clear();

        for (int i = 0; i < songsALSong.size(); i++)

        {
            Song song = songsALSong.get(i);
            String nextSongName = song.getSongName();
            songsOL.add(nextSongName);
        }
        cbSongNames.setItems(songsOL);
        return cbSongNames;

    }

    public void clickComboBox()
    {
        String currentSong = cbSongNames.getValue();
        // Song song = null;
        for (int i = 0; i < songsALSong.size(); i++)
        {
            Song song = songsALSong.get(i);

            if (song.getSongName().equals(currentSong))
            {
                itemCodeTextBox.setDisable(false);
                descriptionTextBox.setDisable(false);
                artistTextBox.setDisable(false);
                albumTextBox.setDisable(false);
                priceTextBox.setDisable(false);

                String itemCodeText = loadSongID(song);
                String descriptionText = loadSongDescription(song);
                String artistText = LoadSongArtist(song);
                String albumText = LoadSongAlbum(song);
                String priceText = LoadSongPrice(song);

                itemCodeTextBox.setText(itemCodeText);
                descriptionTextBox.setText(descriptionText);
                artistTextBox.setText(artistText);
                albumTextBox.setText(albumText);
                priceTextBox.setText(priceText);

                btnAdd.setDisable(true);
                btnAccept.setDisable(true);
                btnCancel.setDisable(false);
                btnEdit.setDisable(false);
                btnDelete.setDisable(false);
                btnExit.setDisable(false);

                itemCodeTextBox.setEditable(false);
                descriptionTextBox.setEditable(false);
                artistTextBox.setEditable(false);
                albumTextBox.setEditable(false);
                priceTextBox.setEditable(false);
            }
        }
    }

    public String loadSongID(Song song)
    {
        String itemCodeTextBox = song.getSongID();
        return itemCodeTextBox;
    }

    public String loadSongDescription(Song song)
    {
        String descriptionTextBox = song.getSongDescription();
        return descriptionTextBox;
    }

    public String LoadSongArtist(Song song)
    {
        String artistTextBox = song.getSongArtist();
        return artistTextBox;
    }

    public String LoadSongAlbum(Song song)
    {
        String albumTextBox = song.getSongAlbum();
        return albumTextBox;
    }

    public String LoadSongPrice(Song song)
    {
        String priceTextBox = song.getSongPrice();
        return priceTextBox;
    }

    public void setButtonsAtStart()
    {
        // button and textfields at start of GUI

        cbSongNames.setDisable(false);

        btnAdd.setDisable(false);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnExit.setDisable(false);
        btnAccept.setDisable(true);
        btnCancel.setDisable(true);

        songNameTextBox.setText(null);
        itemCodeTextBox.setText(null);
        descriptionTextBox.setText(null);
        artistTextBox.setText(null);
        albumTextBox.setText(null);
        priceTextBox.setText(null);

        itemCodeTextBox.setDisable(true);
        descriptionTextBox.setDisable(true);
        artistTextBox.setDisable(true);
        albumTextBox.setDisable(true);
        priceTextBox.setDisable(true);

        songNameTextBox.setDisable(true);
    }

    public Song findSongFromCB() // use in edit and delete
    {
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnExit.setDisable(true);
        btnAccept.setDisable(false);
        btnCancel.setDisable(false);
        songNameTextBox.setDisable(false);

        itemCodeTextBox.setEditable(true);
        descriptionTextBox.setEditable(true);
        artistTextBox.setEditable(true);
        albumTextBox.setEditable(true);
        priceTextBox.setEditable(true);
        songNameTextBox.setEditable(true);
        cbSongNames.setDisable(true);

        currentSongNameCB = cbSongNames.getValue();
        songNameTextBox.setEditable(true);
        songNameTextBox.setText(currentSongNameCB);

        for (int i = 0; i < songsALSong.size(); i++)
        {
            Song song = songsALSong.get(i);
            if (song.getSongName().equals(currentSongNameCB))
            {
                oldSong = song;
            }
        }
        return oldSong;

    }

    public void clickAddButton()
    {
        // disable buttons not needed
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnExit.setDisable(true);
        btnAccept.setDisable(false);
        btnCancel.setDisable(false);
        songNameTextBox.setDisable(false);

        // remove default text from text boxes
        songNameTextBox.setText(null);
        itemCodeTextBox.setText(null);
        descriptionTextBox.setText(null);
        artistTextBox.setText(null);
        albumTextBox.setText(null);
        priceTextBox.setText(null);

        cbSongNames.setDisable(true);

        itemCodeTextBox.setDisable(false);
        descriptionTextBox.setDisable(false);
        artistTextBox.setDisable(false);
        albumTextBox.setDisable(false);
        priceTextBox.setDisable(false);

        itemCodeTextBox.setEditable(true);
        descriptionTextBox.setEditable(true);
        artistTextBox.setEditable(true);
        albumTextBox.setEditable(true);
        priceTextBox.setEditable(true);
        songNameTextBox.setEditable(true);
        cbSongNames.setDisable(true);

        addClicked = true;
    }

    public void clickEditBtn()
    {
        editClicked = true;

        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnExit.setDisable(true);
        btnAccept.setDisable(false);
        btnCancel.setDisable(false);
        songNameTextBox.setDisable(false);

        songNameTextBox.setText(cbSongNames.getValue());

        itemCodeTextBox.setEditable(true);
        descriptionTextBox.setEditable(true);
        artistTextBox.setEditable(true);
        albumTextBox.setEditable(true);
        priceTextBox.setEditable(true);
        songNameTextBox.setEditable(true);
        cbSongNames.setDisable(true);
    }

    public void clickDeleteBtn()
    {
        deleteClicked = true;

        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        btnExit.setDisable(true);
        btnAccept.setDisable(false);
        btnCancel.setDisable(false);
        songNameTextBox.setDisable(true);
    }

    public Song validateAddedSong()
    {
        songName = songNameTextBox.getText();
        songID = itemCodeTextBox.getText();
        songDescription = descriptionTextBox.getText();
        songArtist = artistTextBox.getText();
        songAlbum = albumTextBox.getText();
        songPrice = priceTextBox.getText();

        if (songName == null || songID == null || songDescription == null || 
                songArtist == null || songAlbum == null || songPrice == null)
        {
            clickAddButton();
            System.out.println("You forgot to enter 1 or more fields of data "
                    + "for this song.  "
                    + "Re-enter the song data or click cancel.");

            btnAccept.setDisable(false);
            btnCancel.setDisable(false);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
            btnExit.setDisable(true);
        } else
        {
            newSong = new Song(songName, songID, songDescription, songArtist, 
                    songAlbum, songPrice);
        }
        return newSong;
    }

    public void checkForDuplicate(Song newSong)
    { // songName, songArtist, songAlbum
        if (newSong != null)
        {
            for (Song db : songsALSong)
            {
                if ((db.getSongName().equals(newSong.getSongName()))
                        & ((db.getSongArtist()).equals(newSong.getSongArtist()))
                        & ((db.getSongAlbum()).equals(newSong.getSongAlbum())))
                {
                    System.out.println("A song with the same name, artist and "
                            + "album is already in the song database.  This "
                            + "song was not added.");
                    newSong = null;
                    clickCancelBtn();
                }
            }
        }
    }

    public void clickAcceptBtn()// get new song from GUI entry. Accept button.
    {
        songName = songNameTextBox.getText();
        songID = itemCodeTextBox.getText();
        songDescription = descriptionTextBox.getText();
        songArtist = artistTextBox.getText();
        songAlbum = albumTextBox.getText();
        songPrice = priceTextBox.getText();

        Song oldSong;

        if (addClicked == true)
        {
            newSong = validateAddedSong();// validate new entry then add
            checkForDuplicate(newSong);
            if (newSong != null)
            {
                songsALSong.add(newSong);
            }
        }

        else if (deleteClicked == true)
        { // find the old song in the arraylist and delete it
            oldSong = findSongFromCB();
            songsALSong.remove(oldSong);
        } 
        else if (editClicked == true)
        { // validate the changed song, delete old version, add new version
            validateAddedSong();
            if (newSong != null)
            {
                oldSong = findSongFromCB();
                songsALSong.remove(oldSong);
                Song newSong = new Song(songName, songID, songDescription, 
                        songArtist, songAlbum, songPrice);
                songsALSong.add(newSong);
                cbSongNames.setValue(null);
            }
        }
        setButtonsAtStart();
        writeDBToFile();
        loadDBFromFile();
        loadComboBox(songsALSong);

        editClicked = false;
        deleteClicked = false;
        addClicked = false;
    }

    public void refreshWindow()
    {
        writeDBToFile();
        loadDBFromFile();
        loadComboBox(songsALSong);
    }

    public void clickCancelBtn() // clears new song input from add screen
    {
        // remove default text from text boxes
        songNameTextBox.setText("");
        itemCodeTextBox.setText("");
        descriptionTextBox.setText("");
        artistTextBox.setText("");
        albumTextBox.setText("");
        priceTextBox.setText("");

        deleteClicked = false;
        editClicked = false;
        addClicked = false;

        setButtonsAtStart();
    }

    public void clickExitBtn()
    {
        writeDBToFile();
        Platform.exit();
    }

    public void writeDBToFile()// writes current version of db to textfile
    {
        try
        {
            PrintStream output = new PrintStream(new PrintStream(new File
                    ("database.txt")));
            // writer = new PrintWriter ("database.txt");
            for (int j = 0; j < songsALSong.size(); j++)
            {
                output.println(songsALSong.get(j).toString());
            }

        } catch (FileNotFoundException e)
        {

            System.out.println("The database file was not found." + 
                    e.getMessage());
        }
    }

    public void setTheStage(Stage myStage)
    {
        myStage.setTitle("Song Database");// stage title
        AnchorPane anchorPane = new AnchorPane();
        Scene myScene = new Scene(anchorPane, 500, 350); // create a scene
        myStage.setScene(myScene); // set the scene on the stage

        cbSongNames.setPromptText("Choose a song.");
        Label selectSong = new Label("Select Song:  ");
        AnchorPane.setTopAnchor(selectSong, 15.0);
        AnchorPane.setLeftAnchor(selectSong, 25.0);
        AnchorPane.setRightAnchor(selectSong, 25.0);

        Label itemCode = new Label("Item Code:  ");
        AnchorPane.setTopAnchor(itemCode, 50.0);
        AnchorPane.setLeftAnchor(itemCode, 25.0);
        AnchorPane.setRightAnchor(itemCode, 25.0);

        itemCodeTextBox = new TextField("itemCode");
        AnchorPane.setTopAnchor(itemCodeTextBox, 45.0);
        AnchorPane.setLeftAnchor(itemCodeTextBox, 145.0);
        AnchorPane.setRightAnchor(itemCodeTextBox, 100.0);

        Label description = new Label("Description:  ");
        AnchorPane.setTopAnchor(description, 90.0);
        AnchorPane.setLeftAnchor(description, 25.0);
        AnchorPane.setRightAnchor(description, 25.0);

        descriptionTextBox = new TextField("description");
        AnchorPane.setTopAnchor(descriptionTextBox, 85.0);
        AnchorPane.setLeftAnchor(descriptionTextBox, 145.0);
        AnchorPane.setRightAnchor(descriptionTextBox, 100.0);

        Label artist = new Label("Artist:  ");
        AnchorPane.setTopAnchor(artist, 130.0);
        AnchorPane.setLeftAnchor(artist, 25.0);
        AnchorPane.setRightAnchor(artist, 25.0);

        artistTextBox = new TextField("artist");
        AnchorPane.setTopAnchor(artistTextBox, 125.0);
        AnchorPane.setLeftAnchor(artistTextBox, 145.0);
        AnchorPane.setRightAnchor(artistTextBox, 100.0);

        Label album = new Label("Album:  ");
        AnchorPane.setTopAnchor(album, 170.0);
        AnchorPane.setLeftAnchor(album, 25.0);
        AnchorPane.setRightAnchor(album, 25.0);

        albumTextBox = new TextField("album");
        AnchorPane.setTopAnchor(albumTextBox, 165.0);
        AnchorPane.setLeftAnchor(albumTextBox, 145.0);
        AnchorPane.setRightAnchor(albumTextBox, 100.0);

        Label price = new Label("Price:  ");
        AnchorPane.setTopAnchor(price, 210.0);
        AnchorPane.setLeftAnchor(price, 25.0);
        AnchorPane.setRightAnchor(price, 25.0);

        priceTextBox = new TextField("price");
        AnchorPane.setTopAnchor(priceTextBox, 205.0);
        AnchorPane.setLeftAnchor(priceTextBox, 145.0);
        AnchorPane.setRightAnchor(priceTextBox, 100.0);

        Label addSong = new Label("Song Name:  ");
        AnchorPane.setTopAnchor(addSong, 250.0);
        AnchorPane.setLeftAnchor(addSong, 25.0);
        AnchorPane.setRightAnchor(addSong, 25.0);

        songNameTextBox = new TextField("type Song info");
        AnchorPane.setTopAnchor(songNameTextBox, 245.0);
        AnchorPane.setLeftAnchor(songNameTextBox, 145.0);
        AnchorPane.setRightAnchor(songNameTextBox, 100.0);

        anchorPane.getChildren().addAll(selectSong, itemCode, itemCodeTextBox, 
                description, descriptionTextBox, artist,
                artistTextBox, album, albumTextBox, price, priceTextBox, 
                addSong, songNameTextBox);

        btnAdd = new Button();
        btnAdd.setText("Add");
        btnAdd.isDisabled();

        AnchorPane.setTopAnchor(btnAdd, 300.0);
        AnchorPane.setLeftAnchor(btnAdd, 30.0);

        btnEdit = new Button();
        btnEdit.setText("Edit");
        btnAdd.isDisabled();
        btnAdd.setOnAction(this);
        AnchorPane.setTopAnchor(btnEdit, 300.0);
        AnchorPane.setLeftAnchor(btnEdit, 100.0);

        btnDelete = new Button();
        btnDelete.setText("Delete");
        AnchorPane.setTopAnchor(btnDelete, 300.0);
        AnchorPane.setLeftAnchor(btnDelete, 170.0);

        btnExit = new Button();
        btnExit.setText("Exit");
        AnchorPane.setTopAnchor(btnExit, 300.0);
        AnchorPane.setLeftAnchor(btnExit, 250.0);
        // btnExit.setOnAction(exitButton());

        btnAccept = new Button();
        btnAccept.setText("Accept");
        AnchorPane.setTopAnchor(btnAccept, 300.0);
        AnchorPane.setLeftAnchor(btnAccept, 320.0);

        btnCancel = new Button();
        btnCancel.setText("Cancel");
        AnchorPane.setTopAnchor(btnCancel, 300.0);
        AnchorPane.setLeftAnchor(btnCancel, 390.0);

        AnchorPane.setTopAnchor(cbSongNames, 10.0);
        AnchorPane.setLeftAnchor(cbSongNames, 145.0);
        AnchorPane.setRightAnchor(cbSongNames, 100.0);
        anchorPane.getChildren().add(cbSongNames);
        anchorPane.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnExit,
                btnAccept, btnCancel);

        // setTheStage(myStage);
        myStage.setResizable(false); // disable stage resizing
        myStage.show(); // show the scene and stage

    }

    @Override
    public void handle(ActionEvent event)
    {
        // TODO Auto-generated method stub

    }

}