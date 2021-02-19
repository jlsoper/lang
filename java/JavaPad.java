/*
 *  Running Applets as Stand-Alone Applications
 *
 *  If you want to run a program either as an applet or 
 *  as a stand-alone application, you can begin by writing it
 *  just as an applet, then modifying it to run also as a stand-alone application.
 *
 *  The simplest way to modify an applet to run as a stand-alone application
 *  is to add a main() method to the JApplet subclass that defines the applet.
 *
 *  The main() method should do the following:
 *
 *  Create an instance of the applet subclass.
 *  Send the applet an init() message.
 *  Construct a JFrame.
 *  Transfer the applet's context pane to the JFrame.
 *  If the applet has a menu bar then transfer it into the frame.
 *  Make the frame shut down when the user clicks on the close button.
 *  Set the size of the frame.
 *  Set the location of the frame.
 *  Show the frame.
 *  If the applet implements a start() method then invoke it.
 *
 *  https://www.d.umn.edu/~gshute/java/swing/apps.html
 *
 */

/*
 * java.policy.applet
 *

grant {
  permission java.security.AllPermission;
};

 *
 */

/*
 * HTML 4.01 or earlier
 *

<html>

<head>
  <title>JavaPad</title>
</head>

<body>
<h1>Template for JApplet</h1>

  <applet code=JavaPad.class archive="" width=800 height=600>
    <param name=p1 value=1>
    <param name=p2 value=2>
  </applet>

</body>
</html>

 *
 */

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.Scanner;

import javax.swing.*;

/**
 * This class is a Java Swing version of NotePad.
 * @author James Soper
 * @version 1.4
 */
public class JavaPad extends JApplet implements ActionListener
{
  private JScrollPane scrollPane;

  private JMenuBar menuBar;
  private JMenu menu;

  private JMenuItem nuevo, open, save, exit;

  private JTextArea textArea;  // JTextArea uses '\n' character, regardless of platform

  private Scanner scanner;

  public static final String LINESEPARATOR = System.getProperty("line.separator");

  public void init()
  {
    menuBar = new JMenuBar();

    menu = new JMenu("File");

    nuevo = new JMenuItem("New");
    open  = new JMenuItem("Open");
    save  = new JMenuItem("Save");
    exit  = new JMenuItem("Exit");

    textArea = new JTextArea();

    textArea.setCaretColor(Color.BLUE);
    textArea.setForeground(Color.BLACK);
    textArea.setBackground(Color.LIGHT_GRAY);
    textArea.setSelectionColor(Color.GREEN);

    textArea.setFont(new Font("Consolas", Font.BOLD, 14)); // set a default font for the TextArea
//  textArea.setFont(new Font("Century Gothic", Font.BOLD, 14));

    scrollPane = new JScrollPane(textArea);

    add(scrollPane);

    menu.add(nuevo);
    menu.add(open);
    menu.add(save);
    menu.add(exit);

    menuBar.add(menu);

    setJMenuBar(menuBar);


    nuevo.addActionListener(this);
    nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    
    open.addActionListener(this);
    open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    
    save.addActionListener(this);
    save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

    exit.addActionListener(this);
    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
  }

  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == exit)  // if the source was the "exit" option
      System.exit(0);

    if (event.getSource() == nuevo)  // if the source was the "new" option
    {
      textArea.setText("");
      return;
    }

    JFileChooser fileChooser = new JFileChooser();  // open a file chooser

    if (event.getSource() == open)  // if the source was the "open" option
    {
      int option = fileChooser.showOpenDialog(this); // get the option that the user selected (approve or cancel)

      if (option == JFileChooser.APPROVE_OPTION)  // if the user clicked 'OK'
      {
        textArea.setText(""); // clear the TextArea before applying the file contents

        try
        {
          // create a scanner to read the file (getSelectedFile().getPath() will get the path to the file)
          scanner = new Scanner(new FileReader(fileChooser.getSelectedFile().getPath()));

          while (scanner.hasNext())  // while there's still something to read
            textArea.append(scanner.nextLine() + "\n"); // append the line to the TextArea
        }
        catch (Exception e)
        {
          System.out.println(e.getMessage());
        }
        finally
        {
          scanner.close();
        }
      }
    }
    else if (event.getSource() == save)  // if the source was the "save" option
    {
      int option = fileChooser.showSaveDialog(this);

      if (option == JFileChooser.APPROVE_OPTION)  // if the user clicked 'OK'
      {
        try
        {
          BufferedWriter out = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getPath()));

          out.write(textArea.getText().replace("\n", LINESEPARATOR)); // write the contents of the TextArea to the file
          out.close(); // close the file stream
        }
        catch (Exception e)
        {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /**
   * The main method for the JavaPad application.
   * @param args array of strings
   */
  public static void main(String args[])
  {
    // Create an instance of the applet class.
    JApplet applet = new JavaPad();

    // Send the applet an init() message.
    applet.init();

    // Construct a JFrame.
    final JFrame frame = new JFrame(applet.getClass().getSimpleName());

    // Transfer the applet's context pane to the JFrame.
    frame.setContentPane(applet.getContentPane());

    // Transfer the applet's menu bar into the JFrame.
    // This line can be omitted if the applet
    // does not create a menu bar.
    frame.setJMenuBar(applet.getJMenuBar());

    // Make the application shut down when the user clicks
    // on the close button.
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set the size of the frame.
    // To pack the frame as tightly as possible
    // replace the setSize() message with the following.
    // frame.pack();
    frame.setSize(800, 600);

    // Set the location of the frame.
    // frame.setLocation(FrameX, FrameY);

    // Show the frame.
    frame.setVisible(true);

    // Invoke the applet's start() method.
    // This line can be omitted if the applet
    // does not define a start method.
    // applet.start();
  }
}
