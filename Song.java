/**
 * A class to represent a song.
 * @author Colleen Wurden
 *
 */
public class Song
{
    private String songName;
    private String songID;
    private String songDescription;
    private String songArtist;
    private String songAlbum;
    private String songPrice;

    public Song()
    {
        
    }
    
    /**
     * @param songName is the name of the song.
     * @param songID is an unique integer ID  assigned to the song.
     * @param songDescription is the genre the artist usually plays.
     * @param songArtist is the performer of the song.
     * @param songAlbum is the album the song is part of.
     * @param price is the cost to purchase the song.
     */
    public Song(String songName, String songID, String songDescription,
            String songArtist, String songAlbum,
            String price)
    {
       
        this.songName = songName;
        this.songID = songID;
        this.songDescription = songDescription;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songPrice = price;
    }
    
    public Song(String songID, String songDescription, String songArtist, 
            String songAlbum, String price)
    {

        this.songID = songID;
        this.songDescription = songDescription;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songPrice = price;
    }
     /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.songName + ", " + this.songID + ", "
                + this.songDescription + ", " + this.songArtist + ", "
                + this.songAlbum
                + ", " + this.songPrice;
    }
    
    public String toString1()
    {
        return this.songName;
    }
    /**
     * @return the songName
     */
    public String getSongName()
    {
        return this.songName;
    }

    /**
     * @return the songID
     */
    public String getSongID()
    {
        return this.songID;
    }

    /**
     * @return the songDescription
     */
    public String getSongDescription()
    {
        return this.songDescription;
    }

    /**
     * @return the songArtist
     */
    public String getSongArtist()
    {
        return this.songArtist;
    }

    /**
     * @return the songAlbum
     */
    public String getSongAlbum()
    {
        return this.songAlbum;
    }

    /**
     * @return the songPrice
     */
    public String getSongPrice()
    {
        return this.songPrice;
    }

    /**
     * @param songName the songName to set
     */
    public void setSongName(String songName)
    {
        this.songName = songName;
    }

    /**
     * @param songID the songID to set
     */
    public void setSongID(String songID)
    {
        this.songID = songID;
    }

    /**
     * @param songDescription the songDescription to set
     */
    public void setSongDescription(String songDescription)
    {
        this.songDescription = songDescription;
    }

    /**
     * @param songArtist the songArtist to set
     */
    public void setSongArtist(String songArtist)
    {
        this.songArtist = songArtist;
    }

    /**
     * @param songAlbum the songAlbum to set
     */
    public void setSongAlbum(String songAlbum)
    {
        this.songAlbum = songAlbum;
    }

    /**
     * @param songPrice the songPrice to set
     */
    public void setSongPrice(String songPrice)
    {
        this.songPrice = songPrice;
    }

    public boolean contains(String currentSong)
    {
        // TODO Auto-generated method stub
        return true;
    }
}
