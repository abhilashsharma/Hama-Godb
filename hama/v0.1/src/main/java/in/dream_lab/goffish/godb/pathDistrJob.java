package in.dream_lab.goffish.godb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hama.HamaConfiguration;
import org.apache.hama.bsp.TextInputFormat;
import org.apache.hama.bsp.TextOutputFormat;

import in.dream_lab.goffish.GraphJob;
import in.dream_lab.goffish.LongMapJSONReader;
import in.dream_lab.goffish.LongMapPartitionJSONReader;
import in.dream_lab.goffish.LongMapPartitionJSONReaderNonEdge;
import in.dream_lab.goffish.LongMapPartitionSubsetJsonReader;
import in.dream_lab.goffish.NonSplitTextInputFormat;

public class pathDistrJob {

	 public static void main(String args[]) throws IOException,InterruptedException, ClassNotFoundException, ParseException
	  {
		  HamaConfiguration conf = new HamaConfiguration();
		  GraphJob job = new GraphJob(conf, pathDistr.class);
		  job.setJobName("Path");
		  job.setInputFormat(TextInputFormat.class);
		  job.setInputKeyClass(LongWritable.class);
		  job.setInputValueClass(LongWritable.class);
		  job.setOutputFormat(TextOutputFormat.class);
		  job.setOutputKeyClass(LongWritable.class);
		  job.setOutputValueClass(LongWritable.class);
		  job.setMaxIteration(20);
		  job.setGraphMessageClass(Text.class);
		  job.setInputPath(new Path(args[0]));
		  job.setOutputPath(new Path(args[1]));
		  job.setInitialInput(readArgsFromFile());
		  job.setSubgraphValueClass(pathDistrSubgraphState.class);
		  job.setInputFormat(NonSplitTextInputFormat.class);
		  job.setInputReaderClass(LongMapPartitionSubsetJsonReader.class);
		  
		  //job.setSubgraphComputeClass(SubgraphComputeReduce.class);
		  job.waitForCompletion(true);
	  }
	
	 static String  readArgsFromFile() throws IOException{
           String Args="";
           String fileName="/home/abhilash/abhilash/ReportQueriescountry.txt";
           FileReader fr = new FileReader(fileName);
           BufferedReader br = new BufferedReader(fr);

           String sCurrentLine;

           

           while ((sCurrentLine = br.readLine()) != null) {
               if(Args.equals(""))
                   Args=sCurrentLine;
               else
                   Args=Args+";" + sCurrentLine;
               
           }
           
           br.close();
           return Args;
         }
}
