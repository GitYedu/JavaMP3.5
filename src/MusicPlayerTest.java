import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MusicPlayerTest {

    // создание, удаление, добавление в, удаление из, сохранение, загрузка.


    @Test
    void loadPlaylist() {

        MusicPlayerGUI musicPlayerGUI = new MusicPlayerGUI();
        MusicPlayer musicPlayer = new MusicPlayer(musicPlayerGUI);

        musicPlayer.loadPlaylist(new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestLoad.txt"));

        assertEquals(2, musicPlayer.getSizePlaylist());
    }

    @Test
    void delPlayList() throws IOException {


        File pltest = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs", "playlistUnitTestDel.txt");
        pltest.createNewFile();

        File selectedfile = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestDel.txt");

        assertEquals(true, selectedfile.delete());

    }

    @Test
    void createPlayList() throws IOException {


        File pltest = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs", "playlistUnitTestCreate.txt");
        pltest.createNewFile();


        File selectedfile = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestCreate.txt");

        assertEquals(true, selectedfile.exists());

        selectedfile.delete();

    }

    @Test
    void addPlayList() throws IOException{


        File selectedPlayList = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestAdd.txt");

        ArrayList<String> songPaths;
        songPaths = new ArrayList<>();

        FileReader fileReader = new FileReader(selectedPlayList);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while(( line = bufferedReader.readLine()) != null)
            songPaths.add(line);

        songPaths.add("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\Tropic Fuse - French Fuse.mp3");

        assertEquals(2, songPaths.size());
    }


    @Test
    void decPlayList() throws IOException{


        File selectedPlayList = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestDec.txt");

        ArrayList<String> songPaths;
        songPaths = new ArrayList<>();

        FileReader fileReader = new FileReader(selectedPlayList);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while(( line = bufferedReader.readLine()) != null)
            songPaths.add(line);

        songPaths.remove(0);

        assertEquals(1,songPaths.size());

    }

    @Test
    void savePlaylist() throws IOException {

        MusicPlayerGUI musicPlayerGUI = new MusicPlayerGUI();
        MusicPlayer musicPlayer = new MusicPlayer(musicPlayerGUI);

        File selectedPlayList = new File("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\playlistUnitTestSave.txt");

        ArrayList<String> songPaths;
        songPaths = new ArrayList<>();

        FileReader fileReader = new FileReader(selectedPlayList);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while(( line = bufferedReader.readLine()) != null)
            songPaths.add(line);

        songPaths.add("C:\\Users\\Dom\\Documents\\javaprogs\\javaMP3.5\\src\\songs\\Tropic Fuse - French Fuse.mp3");

        Path plpath = Paths.get(selectedPlayList.getPath());
        try (BufferedWriter writer = Files.newBufferedWriter(plpath)) {
            writer.write("");
            FileWriter fileWriter = new FileWriter(selectedPlayList);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String songPath : songPaths) {
                bufferedWriter.write(songPath + "\n");
            }
            bufferedWriter.close();
        }catch(Exception e) {
            e.printStackTrace();
        }

        musicPlayer.loadPlaylist(selectedPlayList.getAbsoluteFile());

        assertEquals(2, musicPlayer.getSizePlaylist());

    }
}
