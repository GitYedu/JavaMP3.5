import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class MusicPlayerGUI extends JFrame {


    private MusicPlayer musicPlayer;

    private JFileChooser jFileChooser;

    private JLabel songTitle, songArtist;
    private JPanel playbackBtns;

    public MusicPlayerGUI(){

        super("Плеер");

        setSize(600, 400);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        musicPlayer = new MusicPlayer(this);
        jFileChooser = new JFileChooser();


        jFileChooser.setCurrentDirectory(new File("src/songs"));


        jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        addGuiComponents();
    }

    private void addGuiComponents(){

        Tools();

        songTitle = new JLabel("Название");
        songTitle.setBounds(0, 165, getWidth() - 10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 32));
        songTitle.setForeground(Color.darkGray);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);


        songArtist = new JLabel("Автор");
        songArtist.setBounds(0, 205, getWidth() - 10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 32));
        songArtist.setForeground(Color.darkGray);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        addPlaybackBtns();
    }

    private void Tools(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);


        toolBar.setFloatable(false);

        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);


        JMenu songMenu = new JMenu("Музыка");
        menuBar.add(songMenu);

        JMenuItem loadSong = new JMenuItem("Загрузить");
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){

                    Song song = new Song(selectedFile.getPath());

                    musicPlayer.loadSong(song);

                    updateSongTitleAndArtist(song);

                    enablePauseButtonDisablePlayButton();
                }
            }
        });

        songMenu.add(loadSong);

        JMenu playlistMenu = new JMenu("Плейлист");
        menuBar.add(playlistMenu);

        JMenuItem createPlaylist = new JMenuItem("Создать");
        createPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusicPlaylistCreateDialog(MusicPlayerGUI.this).setVisible(true);
            }
        });
        playlistMenu.add(createPlaylist);

        JMenuItem loadPlaylist = new JMenuItem("Загрузить");
        loadPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.updateUI();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("playlist", "txt"));
                jFileChooser.setCurrentDirectory(new File("src/songs"));

                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){
                    musicPlayer.stopSong();
                    musicPlayer.loadPlaylist(selectedFile);
                }
            }
        });
        playlistMenu.add(loadPlaylist);

        JMenuItem ChangePlaylist = new JMenuItem("Изменить");
        ChangePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusicPlaylistChangeDialog(MusicPlayerGUI.this).setVisible(true);
            }
        });
        playlistMenu.add(ChangePlaylist);

        JMenuItem DeletePlaylist = new JMenuItem("Удалить");
        DeletePlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("playlist", "txt"));
                jFileChooser.setCurrentDirectory(new File("src/songs"));

                int result = jFileChooser.showOpenDialog(MusicPlayerGUI.this);
                File selectedFile = jFileChooser.getSelectedFile();

                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){

                    if(selectedFile.delete()) {
                        JOptionPane.showMessageDialog(MusicPlayerGUI.this, "Плейлист удалён");
                    }
                   else{
                        JOptionPane.showMessageDialog(MusicPlayerGUI.this, "Не удалось удалить плейлист");
                    }
                    }
            }
        });
        playlistMenu.add(DeletePlaylist);

        add(toolBar);
    }

    private void addPlaybackBtns(){
        playbackBtns = new JPanel();
        playbackBtns.setBounds(0, 280, getWidth() - 10, 80);
        playbackBtns.setBackground(null);


        JButton prevButton = new JButton(loadImage("src/img/previousB.png"));
        prevButton.setBorderPainted(false);
        prevButton.setBackground(null);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("кнопка предыдущий");
                musicPlayer.prevSong();
            }
        });
        playbackBtns.add(prevButton);

        JButton playButton = new JButton(loadImage("src/img/playB.png"));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("кнопка играть");
                enablePauseButtonDisablePlayButton();

                musicPlayer.playCurrentSong();
            }
        });
        playbackBtns.add(playButton);

        JButton pauseButton = new JButton(loadImage("src/img/pauseB.png"));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("кнопка пауза");
                enablePlayButtonDisablePauseButton();

                musicPlayer.pauseSong();
            }
        });
        playbackBtns.add(pauseButton);


        JButton nextButton = new JButton(loadImage("src/img/nextB.png"));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("кнопка дальше");
                musicPlayer.nextSong();

            }
        });
        playbackBtns.add(nextButton);

        JButton restartButton = new JButton(loadImage("src/img/restartB.png"));
        restartButton.setBorderPainted(false);
        restartButton.setBackground(null);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("кнопка рестарт");
                musicPlayer.restartSong();


            }
        });
        playbackBtns.add(restartButton);



        add(playbackBtns);
    }




    public void updateSongTitleAndArtist(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }


    public void enablePauseButtonDisablePlayButton(){
       JButton playButton = (JButton) playbackBtns.getComponent(1);
       JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        playButton.setVisible(false);
        playButton.setEnabled(false);

        pauseButton.setVisible(true);
        pauseButton.setEnabled(true);
    }

    public void enablePlayButtonDisablePauseButton(){

        JButton playButton = (JButton) playbackBtns.getComponent(1);
        JButton pauseButton = (JButton) playbackBtns.getComponent(2);

        playButton.setVisible(true);
        playButton.setEnabled(true);

        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    ImageIcon loadImage(String imagePath){
        try{
            BufferedImage image = ImageIO.read(new File(imagePath));
            return new ImageIcon(image);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}




