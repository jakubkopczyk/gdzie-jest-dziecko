package core;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MultiOutputStream extends OutputStream
{
    OutputStream[] outputStreams;

    public MultiOutputStream(OutputStream... outputStreams)
    {
        this.outputStreams= outputStreams;
    }

    @Override
    public void write(int b) throws IOException
    {
        for (OutputStream out: outputStreams)
            out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException
    {
        for (OutputStream out: outputStreams)
            out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        for (OutputStream out: outputStreams)
            out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException
    {
        for (OutputStream out: outputStreams)
            out.flush();
    }

    @Override
    public void close() throws IOException
    {
        for (OutputStream out: outputStreams)
            out.close();
    }
    public static void setLogFile(){
        try
        {
            final String logFileName = "log-"+ ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            FileOutputStream fout= new FileOutputStream(new File(System.getProperty("user.dir")+"/results/logs/"+logFileName+".log"));
            //FileOutputStream ferr= new FileOutputStream("stderr.log");

            MultiOutputStream multiOut= new MultiOutputStream(System.out, fout);
            MultiOutputStream multiErr= new MultiOutputStream(System.err, fout);

            PrintStream stdout= new PrintStream(multiOut);
            //PrintStream stderr= new PrintStream(multiErr);

            System.setOut(stdout);
            System.setErr(stdout);
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}