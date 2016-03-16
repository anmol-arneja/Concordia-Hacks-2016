/**
 * Created by alex on 1/23/16.
 */

import com.sun.awt.AWTUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class Overlay{ //implements ActionListener {
    public Overlay(Dimension d){
        frame = new JFrame("Titleless/Borderless");
        mainPanel = new JPanel(new BorderLayout());
        search = new JPanel(new GridBagLayout()); //search panel with the
        tile = new JPanel(new GridLayout(2,3)); //magic int 4 is for columns / rows, subject to change.
        Listener listener = new Listener(this);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
//        frame.setAlwaysOnTop(true);
        frame.setSize(d.getSize());
        frame.setBackground(new Color(0,0,0,0));

        frame.setContentPane(mainPanel);

        mainPanel.add(search, BorderLayout.SOUTH);
        mainPanel.add(tile, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(0,0,0,0));
        mainPanel.setOpaque(true);

        searchBox = new JTextField();
        searchBox.setPreferredSize(new Dimension((int)d.getWidth()/3, (int)d.getHeight()/24)); //Sets size scaled with screen size
        searchBox.setFont(new Font("Verdana", Font.PLAIN, (int)d.getHeight()/28)); //Sets font size slightly smaller than the height of box
        searchBox.addActionListener(listener.getSearchTrigger());

        search.setBackground(new Color(99,0,0,150));
        search.setPreferredSize(new Dimension((int)d.getWidth(), (int)d.getHeight()/12));
        search.add(searchBox);

        tile.setBackground(new Color(0,0,0,150));



    }

    public void setVisible(){
        frame.setVisible(true);
    }

    public void setHidden(){
        frame.setVisible(false);
    }

    //this will most likely need to be passed the search results
    public void dispResults(String[] a){
        tile.removeAll();
        if(a.length < 3){
            tile.setLayout(new GridLayout(1,1));
            imagePanels = new JPanel[1];
        }
        else{
            tile.setLayout(new GridLayout(2,3));
            imagePanels = new JPanel[6];
        }
        for(int i = 0; i < imagePanels.length; i++){
            imagePanels[i] = new JPanel();
            imagePanels[i].setBorder(BorderFactory.createLineBorder(new Color(0,99,0,100), 10));
            imagePanels[i].setBackground(new Color(0,0,0,0));

            try {
                Image image = ImageIO.read(new URL(a[i]));
                JButton button = new JButton();
                button.setIcon(new ImageIcon(image));
                button.setBorder(null);
                imagePanels[i].add(button);
            } catch (Exception e){
                System.out.println(e);
            }


            tile.add(imagePanels[i]);
            tile.setVisible(false);
            tile.setVisible(true);
        }
    }

    public String getSearch(){
        return searchBox.getText();
    }

    public JFrame getFrame(){
        return frame;
    }

    public JTextField getTextField(){
        return searchBox;
    }

    private JFrame frame;
    private JPanel search, tile, mainPanel;
    private JTextField searchBox;
    private JPanel[] imagePanels;
}
