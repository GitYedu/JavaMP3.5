import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;



public class MusicPlaylistChangeDialog extends JDialog {
    private MusicPlayerGUI musicPlayerGUI;
    private ArrayList<String> songPaths;
    private File selectedPlayList;
    public MusicPlaylistChangeDialog(MusicPlayerGUI musicPlayerGUI){
        this.musicPlayerGUI = musicPlayerGUI;
        songPaths = new ArrayList<>();

        setTitle("Изменить плейлист");
        setSize(400, 400);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(musicPlayerGUI);


        ChangeDialogComponents();
    }

    private void ChangeDialogComponents(){

        JPanel songContainer = new JPanel();
        songContainer.setLayout(new BoxLayout(songContainer, BoxLayout.Y_AXIS));
        songContainer.setBounds((int)(getWidth() * 0.025), 10, (int)(getWidth() * 0.90), (int) (getHeight() * 0.75));
        add(songContainer);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите плейлист");
        fileChooser.setFileFilter(new FileNameExtensionFilter("playlist", "txt"));
        fileChooser.setCurrentDirectory(new File("src/songs"));

        int result = fileChooser.showOpenDialog(MusicPlaylistChangeDialog.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPlayList = fileChooser.getSelectedFile();
            try {
                FileReader fileReader = new FileReader(selectedPlayList);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while(( line = bufferedReader.readLine()) != null){
                    JLabel filePathLabel = new JLabel(line);
                    filePathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                    filePathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    songPaths.add(filePathLabel.getText());
                    songContainer.add(filePathLabel);
                    songContainer.revalidate();
                }
                bufferedReader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }





        JButton saveButton = new JButton(musicPlayerGUI.loadImage("src/img/save3.png"));
        saveButton.setBounds(10, (int) (getHeight() * 0.80), 35, 35);
        saveButton.setBorderPainted(false);
        saveButton.setBackground(null);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Path plpath = Paths.get(selectedPlayList.getPath());
                    try (BufferedWriter writer = Files.newBufferedWriter(plpath)) {
                        writer.write("");
                        FileWriter fileWriter = new FileWriter(selectedPlayList);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        for(String songPath : songPaths){
                            bufferedWriter.write(songPath + "\n");
                        }
                        bufferedWriter.close();
                        JOptionPane.showMessageDialog(MusicPlaylistChangeDialog.this, "Изменения сохранены");

                        MusicPlaylistChangeDialog.this.dispose();
                    }
                }catch (IOException d) {
                    d.printStackTrace();
                }
            }
        });
        add(saveButton);


        JButton addSongButton = new JButton("Добавить");
        addSongButton.setBounds(60, (int) (getHeight() * 0.80), 100, 25);
        addSongButton.setFont(new Font("Dialog", Font.BOLD, 14));
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));
                jFileChooser.setCurrentDirectory(new File("src/songs"));
                int result = jFileChooser.showOpenDialog(MusicPlaylistChangeDialog.this);

                File selectedFile = jFileChooser.getSelectedFile();
                if(result == JFileChooser.APPROVE_OPTION && selectedFile != null){
                    JLabel filePathLabel = new JLabel(selectedFile.getPath());
                    filePathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
                    filePathLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    songPaths.add(filePathLabel.getText());

                    songContainer.add(filePathLabel);

                    songContainer.revalidate();
                }
            }
        });
        add(addSongButton);

        JPanel inputPanel = new JPanel();
        inputPanel.setSize(50,20);
        inputPanel.setBounds(340, (int) (getHeight() * 0.80) , 30, 25);
        JTextField numF = new JTextField();
        numF.setPreferredSize(new Dimension(30, 25));
        inputPanel.add(numF);

        add(inputPanel);

        JButton DelSongButton = new JButton("Удалить");
        DelSongButton.setBounds(230, (int) (getHeight() * 0.80), 100, 25);
        DelSongButton.setFont(new Font("Dialog", Font.BOLD, 14));
        DelSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String t = numF.getText();
                int i = Integer.parseInt(t);
                i--;
                if(i<=songPaths.size()-1 && i >=0) {
                    songPaths.remove(i);
                    songContainer.remove(i);
                    songContainer.revalidate();
                    songContainer.repaint();

                }
                else
                    System.out.println("индекс не верный");
            }
        });
        add(DelSongButton);

    }


}
