import java.awt.*;
import java.io.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel {
    
    private boolean hasExported = false;  // Moved outside the method to keep track globally
    
    public Panel() {
        setOpaque(false);  // Make the panel transparent
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Always call the super method

        Graphics2D g2d = (Graphics2D) g;  // Cast to Graphics2D for better control
        
        // Example string, replace with actual data
        //color Image level blackCost redCost greenCost blueCost whiteCost value
        try {
	        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\User\\OneDrive - Katy Independent School District\\Desktop\\Data.txt"));
	        String line;
	        while((line=reader.readLine())!=null) {
	        	System.out.println(line);
	        	hasExported=false;
	        	decrypt(g2d,line);
	        }
	        
	
	        reader.close();
        }catch(Exception E) {
        	System.out.println(E.getMessage());
        }
    }
    private void decrypt(Graphics2D g2d,String str) {
        String[] arr = str.split(" ");

        drawCardContent(g2d, arr);

        // Export the image only once
        if (!hasExported) {
            exportPanelAsPNG(str, arr);
            hasExported = true;  // Prevent re-exporting
            System.out.println("Exported Card");
        }

    }

    private void drawCardContent(Graphics2D g2d, String[] arr) {
        // Draw background image
        BufferedImage background = getBackground(arr[0], arr[1]);
        if (background != null) {
            g2d.drawImage(background, 0, 0, 40 * 8, 56 * 8, null);
        }

        // Draw value number if it is not zero
        if (!arr[8].equals("0")) {
            BufferedImage valueImage = getValue(Integer.parseInt(arr[8]));
            if (valueImage != null) {
                g2d.drawImage(valueImage, 24, 24, 6 * 6, 10 * 6, null);
            }
        }

        // Draw symbol
        BufferedImage symbol = getSymbol(arr[0]);
        if (symbol != null) {
            g2d.drawImage(symbol, (40 - 12) * 8, 16, 80, 80, null);
        }

        // Draw costs
        int pos = (56 * 8) - 70;
        String[] lookUp = new String[]{"", "", "", "black", "red", "green", "blue", "white", ""};
        for (int i = 3; i < 8; i++) {
            if (Integer.parseInt(arr[i]) != 0) {
                BufferedImage scoreBack = getScoreBack(lookUp[i]);
                BufferedImage scoreNum = getCostNum(Integer.parseInt(arr[i]));
                if (scoreBack != null) {
                    g2d.drawImage(scoreBack, 8, pos, 60, 60, null);
                    g2d.drawImage(scoreNum, 8, pos, 60, 60, null);
                }
                pos = pos - 65;
            }
        }
    }

    private void exportPanelAsPNG(String s, String[] arr) {
        try {
            // Create a BufferedImage of the panel's content
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            // Use the helper method to draw content into the BufferedImage
            drawCardContent(g2d, arr);

            g2d.dispose();  // Always dispose of the graphics object

            // Save the image to a PNG file
            File outputFile = new File("C:\\Users\\User\\OneDrive - Katy Independent School District\\Desktop\\SplendorCards\\" + s + ".png"); // Dynamic filename
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image exported to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper methods to load images (unchanged)
    public BufferedImage getBackground(String color, String back) {
        try {
            return ImageIO.read(Panel.class.getResource("/BackgroundImages/" + color + back + ".png"));
        } catch (IOException e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }
        return null;
    }

    public BufferedImage getValue(int num) {
        try {
            return ImageIO.read(Panel.class.getResource("/ValueNums/" + num + ".png"));
        } catch (IOException e) {
            System.err.println("Error loading value image: " + e.getMessage());
        }
        return null;
    }

    public BufferedImage getSymbol(String str) {
        try {
            return ImageIO.read(Panel.class.getResource("/Symbols/" + str + ".png"));
        } catch (Exception e) {
            System.err.println("Error loading symbol: " + e.getMessage());
        }
        return null;
    }

    public BufferedImage getScoreBack(String str) {
        try {
            return ImageIO.read(Panel.class.getResource("/ScoreBackground/" + str + ".png"));
        } catch (Exception e) {
            System.err.println("Error loading score background: " + e.getMessage());
        }
        return null;
    }

    public BufferedImage getCostNum(int num) {
        try {
            return ImageIO.read(Panel.class.getResource("/ScoreNums/" + num + ".png"));
        } catch (Exception e) {
            System.err.println("Error loading cost number: " + e.getMessage());
        }
        return null;
    }
}
