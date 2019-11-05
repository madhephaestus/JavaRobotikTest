package rbe.twoKtwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JavaRobotFk {
	public static double x,y,theta=0;
	public static double lastEncoder0,lastEncoder1,lastIMUHeading=0;
	public static double lastTimestamp=-1;
	// Read the csv file into a list of primitive data
	public static ArrayList<double[]> todata(String filename){
		ArrayList<double[]> alldata = new ArrayList<double[]>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				// read next line
				line = reader.readLine();
				if(line!=null) {
					String[] fields = line.split(",");
					double [] parsed= new double[fields.length];
					for(int i=0;i<parsed.length;i++)
						try {
							parsed[i]= Double.parseDouble(fields[i]);
						}catch(NumberFormatException ex) {}
					if(parsed.length==17)
						alldata.add(parsed);	
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alldata;
	}
	
	public static void updateEncoderData(double timestamp, double encoder0, double encoder1, double IMUheading) {
		if(lastTimestamp>0) {
			double deltaTime = timestamp-lastTimestamp;
			double deltaEncoder0 =encoder0-lastEncoder0;
			double deltaEncoder1 =encoder1-lastEncoder1;
			double deltaIMU =IMUheading- lastIMUHeading;
			System.out.println("Delta Time="+deltaTime+" enc0="+deltaEncoder0+" enc1="+deltaEncoder1+" heading="+deltaIMU);
			// do the FK update
		}// else store the last position data for next loop
		lastEncoder0=encoder0;
		lastEncoder1=encoder1;
		lastTimestamp=timestamp;
		lastIMUHeading=IMUheading;
		return;
	}
	
	public static void main(String[] args) {
		ArrayList<double[]> alldata = todata("Motor-Data.csv");
		for(double[] line:alldata) {
			updateEncoderData(line[0], line[1], line[2], line[16]);
		}
		System.out.println("Final pose x="+x+" y="+y+" theta="+theta);
	}

}
