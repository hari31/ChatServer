import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;


public class ServerWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int WIDTH = 640;
	int HEIGHT = 480;
	
	private static JPanel panel = new JPanel();
	
	private static JTextPane consoleText = new JTextPane();
	private static JScrollPane console = new JScrollPane(consoleText);
	
	
	public ServerWindow(){
		this.setSize(WIDTH,HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("Server Console");
		this.setLocationRelativeTo(null);
		
		console.setPreferredSize(new Dimension(WIDTH-8,HEIGHT-64));
		panel.add(console);
		
		
		this.add(panel);
		
	}	
	
	
	public void writeToConsole(String text){
		
		append(text+"\n");
		
		
		
	}
	
	
	public void append(String s) {
		   try {
		      Document doc = consoleText.getDocument();
		      doc.insertString(doc.getLength(), s, null);
		   } catch(BadLocationException exc) {
		      exc.printStackTrace();
		   }
		}
	

	public void appendImage(String s) {
		try {
			BufferedImage c;
			c = ImageIO.read(getClass().getResourceAsStream(s));
			Document doc = consoleText.getDocument();
			Style style = consoleText.addStyle("I'm a Style", null);
			StyleConstants.setIcon(style,new ImageIcon(c));
			doc.insertString(doc.getLength(), "Ignore", style);
			
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
