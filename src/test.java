import java.io.*;

import static java.nio.file.Files.readAllBytes;

class test
{
    static String pythonFile="/home/immohan/Documents/Domain.py";
    static String[] pythonCompile={"python","main.py"};

    static String errorFile="/home/immohan/Documents/error1.txt";
    static String directory="/home/immohan/Documents/";

    public static void main(String[] args) throws IOException {
        ProcessBuilder processBuilderL=new ProcessBuilder();
        processBuilderL.directory(new File(directory));
        processBuilderL.redirectError(new File(errorFile));
        processBuilderL.command(pythonCompile);
        String ans1="";
        Process processL=processBuilderL.start();
        //num1=p1.waitFor();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(processL.getInputStream()));
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(processL.getOutputStream()));
        //bufferedWriter.write(input);
        bufferedWriter.flush();
        bufferedWriter.close();

        int exitCode=0;
        try {
            exitCode = processL.waitFor();
            System.out.println("Comming to here");
        } catch (InterruptedException e) {
            System.out.println("Exception with the process");
        }
        System.out.println("HERE IS THE EXITCODE "+exitCode+"");
        if(exitCode==0)
        {
            String line;
            ans1="";
            while ((line = bufferedReader.readLine()) != null) {
                ans1+=line;
                ans1+="\n";
                System.out.println(line);
            }
            System.out.println(ans1);
        }
        bufferedReader.close();
        System.out.println(exitCode);
        if(exitCode!=0 && exitCode!=143 && exitCode!=137)
        {
            //System.out.println(exitCode);
            ans1=new String(readAllBytes(new File(errorFile).toPath()));
            System.out.println(exitCode);
            if(!Thread.currentThread().isInterrupted())
            {
                System.out.println("BREAKING THE CODE");
                //outputArr1[currentRunningIndex]=ans1;
            }
            System.out.println(ans1);
        }
        else if(exitCode==143 || exitCode==137)
        {
            System.out.println("HEHE");
            ans1="TIME LIMIT EXIST";
            System.out.println(exitCode);
            if(!Thread.currentThread().isInterrupted())
            {
                System.out.println("BREAKING THE CODE");
                //outputArr1[currentRunningIndex]=ans1;
            }
            System.out.println(ans1);
        }
        else
        {
            //System.out.println(ans1);
            //outputArr1[currentRunningIndex]=ans1;
            System.out.println(exitCode);
            if(!Thread.currentThread().isInterrupted())
            {
                System.out.println("BREAKING THE CODE");
                //outputArr1[currentRunningIndex]=ans1;
            }
            //System.out.println(ans1);
            System.out.println(ans1);
        }
        System.out.println(exitCode);
    }
}
