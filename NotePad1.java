package Notepad;

//Required Libraries
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad1 {
	
	//Main Components of the Frame
	JFrame frame;
	JTextArea txtArea;
	JMenuBar menuBar;
	JMenu fileMenu,languageMenu,formatMenu,commandPrompt;
	JScrollPane scroll;
	
	//file menu Items
	JMenuItem newItem , newWindowItem, openItem,saveItem,saveAsItem,exitItem; 
	
	//format menu Items
	JMenuItem wordWrapItem,fontItem,fontSizeItem;
	
	//command prompt Item
	JMenuItem openCommandPrompt;
	
	
	//Variables for File Operation
	String openPath = null;
	String openFileName = null;
	boolean wrap = false;
	
	//Fonts
	Font arial ;
	Font newRoman ;
	Font consolas ;
	
	String fontStyle = "Arial";
	
	
 	
	public Notepad1() {
		
		createFrame();
		createTextArea();
		createScrollBars();
		createMenuBar();
		fileMenuItems();
		createformatItems();
		createCommandPrompt();
		createLanguageItems();
	}
	
	//Method to Create Main Frame 
	public void createFrame() 
	{
		frame = new JFrame("Notepad");
 		
		frame.setSize(1200,900);
		
		//Application Icon
		Image logo = Toolkit.getDefaultToolkit().getImage("C:\\Users\\avish\\OneDrive\\Desktop\\Notepad.jpg");
		
		frame.setIconImage(logo);
			
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
	
	}
	
	//Method to create Text Area
	public void createTextArea() 
	{
		txtArea = new JTextArea();
		
		txtArea.setFont(new Font("Arial", Font.PLAIN, 25));
		
		frame.add(txtArea);
	}
	
	//Method to create or enable the Scrollbar for Text Area 
	public void createScrollBars()
	{
		scroll = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroll);
	}
	
	//Method to Create MenuBar
	public void createMenuBar() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		languageMenu = new JMenu("Language");
		
		menuBar.add(languageMenu);
		
		formatMenu = new JMenu("Format");
		
		menuBar.add(formatMenu);
		
		commandPrompt = new JMenu("Command Prompt");
		menuBar.add(commandPrompt);
		
	}
	
	//Method to Add File Menu Items
	public void fileMenuItems() 
	{
		
		//New Option
		newItem = new JMenuItem("New");
		fileMenu.add(newItem);
		
		newItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				txtArea.setText("");
				frame.setTitle("Untitled-Notepad");
				openPath = null;
				openFileName = null;
			}
		});
		
		//New Window
		newWindowItem = new JMenuItem("New Window");
		fileMenu.add(newWindowItem);
		
		newWindowItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				Notepad1 n1 = new Notepad1();//Relaunch the Application
				n1.frame.setTitle("Untitled");
				
			}
		});
		
		
		//Open the File
		openItem = new JMenuItem("Open...");
		fileMenu.add(openItem);
		
		openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog(frame, "Open",FileDialog.LOAD); //To Create File Dailog File Explorer
				
				fd.setVisible(true);
				
				String path = fd.getDirectory();
				String file = fd.getFile();
				
				if(file != null)
				{
					frame.setTitle(path+file);
					
					openPath = path;
					openFileName = file;
				}	
				
				System.out.println(path+file);
				
				BufferedReader bf1 = null;
				
				try 
				{	
					bf1 = new BufferedReader(new FileReader(path+file));
					
					String sentence = bf1.readLine();
					txtArea.setText("");
					while(sentence != null)
					{
						txtArea.append(sentence + "\n");
						sentence = bf1.readLine();
					}
					
				} 
				catch (FileNotFoundException e1)
				{
					System.out.println("File Not Found");
				} 
				catch (IOException e1) 
				{
					System.out.println("Data could not be read");
				}
				catch (NullPointerException e2) 
				{

				}
				finally
				{
				  try 
				  {
					bf1.close();
				  } 
				  catch (IOException e1)
				  {
					  System.out.println("File could not be Closed");	
				  }	
				}
				
			}
		});
		
		
		//Save the File In the Current Directory
		saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(openPath != null && openFileName != null)
				{
					//If Path & File are present then write in the same File
					writeDataToFile(openPath, openFileName);
				}	
				else
				{
					//If Path & File are not present then open the File Dialog to select the Directory & File
					FileDialog fd = new FileDialog(frame, "Save As",FileDialog.SAVE);
					fd.setVisible(true);
					
					String path = fd.getDirectory();
					String file = fd.getFile();
					
					if(file != null && path != null)
					{	
						//method to write the data of text area to the File
						writeDataToFile(path, file);
						
						openFileName = file;
						openPath = path;
						
						frame.setTitle(openPath);
					}	
				}	
				
			}
		});
		
		
		//Save the File in the Users Choosen Directory
		saveAsItem = new JMenuItem("Save As");
		fileMenu.add(saveAsItem);
		
		saveAsItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				FileDialog fd = new FileDialog(frame, "Save As",FileDialog.SAVE);
				fd.setVisible(true);
				
				String path = fd.getDirectory();
				String file = fd.getFile();
				
				if(file != null && path != null)
				{	
					
					writeDataToFile(path, file);
					
					openFileName = file;
					openPath = path;
					
					frame.setTitle(openPath+openFileName); 
				}	
			}
		});
		
		
		//To Close the Application
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				frame.dispose();
			}
		});
	}
	
	//Method to Add Format Menu Items
	public void createformatItems()
	{
		//Wrapping the words in the Text Area 
		wordWrapItem = new JMenuItem("Word Wrap : Off");
		formatMenu.add(wordWrapItem);
	
		
		wordWrapItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(wrap == false)
				{
					txtArea.setLineWrap(true);
					txtArea.setWrapStyleWord(true);
				
					wrap = true;
					
					wordWrapItem.setText("Word Wrap : On");
				}
				else
				{
					txtArea.setLineWrap(false);
					txtArea.setWrapStyleWord(false);
				
					wrap = false;
					
					wordWrapItem.setText("Word Wrap : Off");
				}
				
			}
		});
		
		
		//Creating the Font Menu
		fontItem = new JMenu("Font");
		formatMenu.add(fontItem);
		
		//Creating the Font Menu Items
		JMenuItem itemArial  = new JMenuItem("Arail");
		JMenuItem itemTimesNewRoman = new JMenuItem("Times New Roman");
		JMenuItem itemConsolas = new JMenuItem("Consolas");
 		
		fontItem.add(itemArial);
		fontItem.add(itemTimesNewRoman);
		fontItem.add(itemConsolas);
		
		
		itemArial.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Arial");
			}
		});
		
		
		itemTimesNewRoman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Times new Roman");
			}
		});
		
		itemConsolas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontType("Consolas");
			}
		});
		
		
		//Font Size Menu
		fontSizeItem = new JMenu("Font Size :");
		formatMenu.add(fontSizeItem);
		
		//Creating the Font Sizes
		JMenuItem size10 = new JMenuItem("10");
		fontSizeItem.add(size10);
		
		size10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(10);
			}
		});
		
		
		JMenuItem size14 = new JMenuItem("14");
		fontSizeItem.add(size14);
		
		size14.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(14);
			}
		});
		
		
		JMenuItem size18 = new JMenuItem("18");
		fontSizeItem.add(size18);
		
		size18.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(18);
			}
		});
		
		
		JMenuItem size22 = new JMenuItem("22");
		fontSizeItem.add(size22);
		
		size22.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(22);
			}
		});
		
		
		
		JMenuItem size26 = new JMenuItem("26");
		fontSizeItem.add(size26);
		
		size26.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(26);
			}
		});
		
		
		JMenuItem size30 = new JMenuItem("30");
		fontSizeItem.add(size30);
		
		size30.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(30);
			}
		});
		
		
		JMenuItem size34 = new JMenuItem("34");
		fontSizeItem.add(size34);
		
		size34.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setFontSize(34);
			}
		});
		
		
		
	}
	
	
	//Method for the Font Size
	public void setFontSize(int fontSize)
	{
		arial = new Font("Arial",Font.PLAIN , fontSize);
		newRoman = new Font("Times new Roman", Font.PLAIN, fontSize);
		consolas = new Font("Consolas", Font.PLAIN, fontSize);
		
		setFontType(fontStyle);
	}
	
	//Method for the Font
	public void setFontType(String name)
	{
		fontStyle = name;
		switch (name) 
		{
			case "Arial": 
			{
				txtArea.setFont(arial);
				break;
			}
			
			case "Times new Roman" :
			{
				txtArea.setFont(newRoman);
				break;
			}	
			
			case "Consolas":
			{
				txtArea.setFont(consolas);
				break;
			}
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + name);
		}
	}
	
	//Method to create Command Prompt
	public void createCommandPrompt()
	{
		openCommandPrompt = new JMenuItem("Open Command Prompt");
		
		
		openCommandPrompt.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				try
				{
					if(openPath!= null)
					{
						Runtime.getRuntime().exec(new String[] {"cmd","/K","start"},null,new File(openPath));
					}		
					
				} 
				catch (IOException e1) 
				{
					System.out.println("Could not launch cmd");
				}
				catch (NullPointerException e2) 
				{
					
				}
				
			}
		});
		
		
		commandPrompt.add(openCommandPrompt);
		
		
	}
	
	//method to Write Data From Text Area To the File
	public void writeDataToFile(String path,String file)
	{
		BufferedWriter bw = null ;
		try 
		{
			bw = new BufferedWriter(new FileWriter(path+file));
			
			String text = txtArea.getText();
			
			bw.write(text);
			
		} 
		catch (IOException e1) 
		{
			System.out.println("File Not Found !");
		}
		catch (NullPointerException e2) 
		{	
			System.out.println("File Not Found to write");
		}
		finally 
		{
			try 
			{
				bw.close();
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	
	//Method To Add Language Menu
	public void createLanguageItems()
	{
		//Java
		JMenuItem itemJava = new JMenuItem("Java");
		itemJava.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("Java");
				openPath = null;
				openFileName = null;
			}
		});
		
		languageMenu.add(itemJava);
		
		//C
		JMenuItem itemC  = new JMenuItem("C");
		languageMenu.add(itemC);
		
		itemC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("C");
				openPath = null;
				openFileName = null;
			}
		});
		
		
		//Html
		JMenuItem itemHtml  = new JMenuItem("HTML");
		languageMenu.add(itemHtml);
		
		itemHtml.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("HTML");
				openPath = null;
				openFileName = null;
			}
		});
		
		//C++
		JMenuItem itemCpp  = new JMenuItem("C++");
		languageMenu.add(itemCpp);
		
		itemCpp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				setLanguage("CPP");
				openPath = null;
				openFileName = null;
			}
		});
		
	}
	
	//Methods To Open the Boiler Plate Templates in Text Area
	public void setLanguage(String lang)
	{
		BufferedReader br=null;
        try
        {
			 br=new BufferedReader(new FileReader("C:\\Users\\avish\\OneDrive\\Desktop\\BoilerPlate Templates\\"+lang+"Format.txt"));
			 
			   String sentence = br.readLine();
			   txtArea.setText(null);
			   
			   while(sentence!=null)
			   {
				   txtArea.append(sentence+"\n");
				   sentence=br.readLine();
			   }
			   
		} 
        catch (FileNotFoundException e1)
        {
        	System.out.println("File not Found");
		} 
        catch (IOException e1)
        {
			System.out.println("Data  could not be read ");
		}
        catch (NullPointerException e2) {
			
        	
		}
        finally
        {
        	try
        	{
				br.close();
			} catch (IOException e1) 
        	{
				System.out.println("File could  not be close");
			}
        	catch (NullPointerException e2) 
        	{
				
			}
        }
	}
	
}
