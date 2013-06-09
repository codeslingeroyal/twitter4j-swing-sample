package com.github.twitterswingsample.view.frames;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.github.twitterswingsample.model.Credentials;
import com.github.twitterswingsample.view.listener.ProgramCloser;
import com.github.twitterswingsample.view.listener.UserSelectionFrameCreator;
import com.github.twitterswingsample.view.panels.ConsolePanel;
import com.github.twitterswingsample.view.panels.ClientUserPanel;
import com.github.twitterswingsample.view.panels.ShortInfoPanel;

/**
 * The main window of the client
 * 
 * @author multiprogger
 */
public class MainFrame extends JFrame{
	
	public MainFrame() {
		setBounds(50, 0, 600, 700);
		setTitle("Twitter4J Swing Sample");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(2, 2));
		
		try {
			setIconImage(ImageIO.read(getClass().getResource("images/icon.png")));
		} catch (IOException e) {}
		
		Font menuFont = new Font("Arial", Font.BOLD, 13);
		JMenuBar jmb = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open User Selection"),
				exit = new JMenuItem("Close");
		open.setFont(menuFont);
		open.addActionListener(new UserSelectionFrameCreator());
		file.add(open);
		file.addSeparator();
		exit.setFont(menuFont);
		exit.addActionListener(new ProgramCloser());
		file.add(exit);
		file.setFont(menuFont);
		jmb.add(file);
		setJMenuBar(jmb);
		
		JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		try {
			pane.setTopComponent(new ClientUserPanel(new Credentials(0).getTwitter()));
		} catch (FileNotFoundException e) {
			ConsolePanel.getInstance().printMessage(new String[]{
				"File 'login.xml' not found",
				"See the project homepage for further information"
			});
		} catch (IOException e) {
			ConsolePanel.getInstance().printMessage(new String[]{
				"Unknown error",
				"Please report this bug!"
			});
		} catch (SAXException e) {
			ConsolePanel.getInstance().printMessage(new String[]{
				"damaged xml structure in file 'login.xml'",
				"Repair it!"
			});
		} catch (ParserConfigurationException e) {
			ConsolePanel.getInstance().printMessage(new String[]{
				"Internal Error",
				"Please report this bug!"
			});
		} catch (Exception e) {
			ConsolePanel.getInstance().printMessage(new String[]{
				"Unspecified Error",
				"Perhaps 'login.xml' not filled",
				"Otherwise, please report this bug!",
				e.toString()
			});
		}

		pane.setBottomComponent(ConsolePanel.getInstance());
		pane.setDividerLocation(1.);
		pane.setResizeWeight(1.);
		pane.setOneTouchExpandable(true);
		add(pane, BorderLayout.CENTER);
		
		add(ShortInfoPanel.getInstance(), BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) throws Exception {
		new MainFrame().setVisible(true);
	}
}