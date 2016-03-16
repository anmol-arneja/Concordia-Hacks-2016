import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.*;

import org.json.*;

/**
 * Created by alex on 1/23/16.
 */
public class Listener {
    public Listener(final Overlay o){
        searchTrigger = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent search) {
                System.out.println("You searched");
                String search_terms = o.getSearch();
                String[] imgarray = {"error"};

                /////////////////////
                /// PYTHON CALLER ///
                /////////////////////
                try {

                    String python_path = "/home/alex/Documents/github/memeshot/src/python_helpers/giphyapi.py";
                    String[] cmd = new String[3];

                    cmd[0] = "python3";
                    cmd[1] = python_path;
                    cmd[2] = search_terms;

                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    pb.redirectErrorStream(true);

//                    Runtime rt = Runtime.getRuntime();
                    Process pr = pb.start();
                    BufferedReader bf = new BufferedReader(
                            new InputStreamReader(pr.getInputStream())
                    );

                    String json_response = "";

                    if (bf != null) {
//                        System.out.println(bf.readLine());
                        json_response = bf.readLine();
                    }

                    System.out.println(json_response);

                    ///////////////////
                    /// JSON PARSER ///
                    ///////////////////
                    JSONObject jsonobject = new JSONObject(json_response);
                    JSONArray jarray = jsonobject.getJSONArray("data");
                    imgarray = new String[jarray.length()];
                    for(int i = 0; i < imgarray.length; i++){
                        imgarray[i] = jarray.getString(i);
                    }
                    ///////////////////
                    /// JSON PARSER ///
                    ///////////////////

                }catch (Exception e){
                    System.out.println(e);
                }
                /////////////////////
                /// PYTHON CALLER ///
                /////////////////////

                o.dispResults(imgarray);
                // o.getTextField().setText("potato");
            }
        };

        imageButton = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent mouseClick){
                o.getTextField().setText("");
                if(mouseClick.getClickCount() == 1){
                    //img to clipboard
                }
                else{
                    //new frame options?!!?
                }
            }
            public void mousePressed(MouseEvent mousePressed){

            }
            public void mouseReleased(MouseEvent mouseRelease){

            }
            public void mouseExited(MouseEvent mouseExit){

            }
            public void mouseEntered(MouseEvent mouseEnter){

            }
        };
    }

    public AbstractAction getSearchTrigger() {
        return searchTrigger;
    }

    private AbstractAction searchTrigger;
    private MouseListener imageButton;
}
