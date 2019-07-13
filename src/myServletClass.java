
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import static java.nio.file.Files.readAllBytes;


public class myServletClass extends HttpServlet {

    static String javaFile="Main.java";
    static String[] javaRun={"java","-cp","","Main"};
    static String[] javaCompile={"javac","Main.java"};

    static String pythonFile="main.py";
    static String[] pythonCompile={"python","main.py"};

    static String cFile="Main.c";
    static String[] cCompile={"gcc","-o","MainC","Main.c"};
    static String[] cRun={"./MainC"};

    static String cppFile="Main.cpp";
    static String[] cppCompile={"g++","-o","MainCPP","Main.cpp"};
    static String[] cppRun={"./MainCPP"};

    static String errorFile="error1.txt";
    static String directory="";

    static String[] outputArr1=new String[10000];
    static boolean[] successArr1=new boolean[10000];
    static TreeSet<Integer> treeSet=new TreeSet<>();

    public static boolean currentRunning=true;
    Queue<codeInfo> queue=new LinkedList<>();
    static int currentRunningIndex=0;
    static ProcessBuilder processBuilderL=null;
    static HttpServletResponse response1=null;
    static Process processL=null;
    //static String ans1="";

    public void init()
    {
        for(int i=0;i<10000;i++)
        {
            treeSet.add(i);
        }
    }

    static class myThread extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("INSIDE THREAD");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("HELLO");
            }
            //System.out.println("ABOUT TO BREAK");
            if(processL.isAlive())
            {
                //outputArr1[currentRunningIndex]="Time Limit Exist";
                System.out.println("Time Limit Exist from thread");
                processL.destroy();
                processL.destroyForcibly();

            }
            this.interrupt();
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String status=request.getParameter("status");
        System.out.println(status);
        if(status.equals("code"))
        {
            String lang=request.getParameter("lang");
            if(lang.equals("py"))
            {
                currentRunning=true;
                String code=request.getParameter("code");
                String input=request.getParameter("input");
                input+="\n";
                byte[] bytes=code.getBytes();
                processBuilderL=new ProcessBuilder();
                //processBuilderL.directory(new File(directory));
                File file=new File(pythonFile);
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                processBuilderL.redirectError(new File(errorFile));
                processBuilderL.command(pythonCompile);
                String ans1="";
                response1=response;
                myThread myThread1=new myThread();
                myThread1.start();
                processL=processBuilderL.start();
                //num1=p1.waitFor();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(processL.getInputStream()));
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(processL.getOutputStream()));
                bufferedWriter.write(input);
                bufferedWriter.flush();
                bufferedWriter.close();

                int exitCode=0;
                try {
                    exitCode = processL.waitFor();
                    System.out.println("Comming to here");
                } catch (InterruptedException e) {
                    System.out.println("Exception with the process");
                }
                if(exitCode==0)
                {
                    String line;
                    ans1="";
                    while ((line = bufferedReader.readLine()) != null) {
                        ans1+=line;
                        ans1+="\n";
                    }
                }
                bufferedReader.close();
                currentRunning=false;
                System.out.println(exitCode);
                if(exitCode!=0 && exitCode!=143 && exitCode!=137)
                {
                    //System.out.println(exitCode);
                    ans1=new String(readAllBytes(new File(errorFile).toPath()));
                    currentRunning=false;
                    System.out.println(exitCode);
                    if(!Thread.currentThread().isInterrupted())
                    {
                        System.out.println("BREAKING THE CODE");
                        myThread1.interrupt();
                        //outputArr1[currentRunningIndex]=ans1;
                    }
                    response.getWriter().println(ans1);
                }
                else if(exitCode==143 || exitCode==137)
                {
                    System.out.println("HEHE");
                    ans1="TIME LIMIT EXIST";
                    currentRunning=false;
                    System.out.println(exitCode);
                    if(!Thread.currentThread().isInterrupted())
                    {
                        System.out.println("BREAKING THE CODE");
                        myThread1.interrupt();
                        //outputArr1[currentRunningIndex]=ans1;
                    }
                    response.getWriter().println(ans1);
                }
                else
                {
                    currentRunning=false;
                    //System.out.println(ans1);
                    //outputArr1[currentRunningIndex]=ans1;
                    System.out.println(exitCode);
                    if(!Thread.currentThread().isInterrupted())
                    {
                        System.out.println("BREAKING THE CODE");
                        myThread1.interrupt();
                        //outputArr1[currentRunningIndex]=ans1;
                    }
                    //System.out.println(ans1);
                    response.getWriter().println(ans1);
                }
                System.out.println(exitCode);
                System.out.println(Thread.currentThread().isInterrupted()+"");
                currentRunning=false;
            }
            else if(lang.equals("C"))
            {
                String code=request.getParameter("code");
                String input=request.getParameter("input");
                input+="\n";
                byte[] bytes=code.getBytes();
                ProcessBuilder processBuilder1=new ProcessBuilder();
                //processBuilder1.directory(new File(directory));
                File file=new File(cFile);
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                processBuilder1.redirectError(new File(errorFile));
                processBuilder1.command(cCompile);
                Process p1=processBuilder1.start();
                String ans1="";
                int exitCode=0;
                int num1=0;
                try {
                    num1=p1.waitFor();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(num1==0)
                {
                    processBuilderL = new ProcessBuilder();
                    //processBuilderL.directory(new File(directory));
                    processBuilderL.redirectError(new File(errorFile));
                    processBuilderL.command(cRun);
                    ans1="";
                    myThread myThread1=new myThread();
                    myThread1.start();
                    processL=processBuilderL.start();
                    //num1=p1.waitFor();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(processL.getInputStream()));
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(processL.getOutputStream()));
                    bufferedWriter.write(input);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    exitCode=0;
                    try {
                        exitCode = processL.waitFor();
                        System.out.println("Comming to here");
                    } catch (InterruptedException e) {
                        System.out.println("Exception with the process");
                    }
                    if(exitCode==0)
                    {
                        String line;
                        ans1="";
                        while ((line = bufferedReader.readLine()) != null) {
                            ans1+=line;
                            ans1+="\n";
                        }
                    }
                    currentRunning=false;
                    System.out.println(exitCode);
                    bufferedReader.close();
                    if(exitCode!=0 && exitCode!=143 && exitCode!=137)
                    {
                        //System.out.println(exitCode);
                        ans1=new String(readAllBytes(new File(errorFile).toPath()));
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else if(exitCode==143 || exitCode==137)
                    {
                        System.out.println("HEHE");
                        ans1="TIME LIMIT EXIST";
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else
                    {
                        currentRunning=false;
                        //System.out.println(ans1);
                        //outputArr1[currentRunningIndex]=ans1;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        //System.out.println(ans1);
                        response.getWriter().println(ans1);
                    }
                    System.out.println(exitCode);
                    System.out.println(Thread.currentThread().isInterrupted()+"");
                    currentRunning=false;
                }
                else
                {
                    ans1=new String(readAllBytes(new File(errorFile).toPath()));
                    response.getWriter().println(ans1);
                }
                System.out.println(ans1);
                System.out.println(exitCode);
                //response.getWriter().println(ans1);
            }
            else if(lang.equals("cpp"))
            {
                String code=request.getParameter("code");
                String input=request.getParameter("input");
                input+="\n";
                byte[] bytes=code.getBytes();
                ProcessBuilder processBuilder1=new ProcessBuilder();
                //processBuilder1.directory(new File(directory));
                File file=new File(cppFile);
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();
                String ans1="";
                int exitCode=0;
                processBuilder1.command(cppCompile);
                processBuilder1.redirectError(new File(errorFile));
                processL=processBuilder1.start();
                int num1=0;
                try {
                    num1=processL.waitFor();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(num1==0)
                {
                    processBuilderL = new ProcessBuilder();
                    //processBuilderL.directory(new File(directory));
                    processBuilderL.redirectError(new File(errorFile));
                    processBuilderL.command(cppRun);
                    ans1="";
                    myThread myThread1=new myThread();
                    myThread1.start();
                    processL=processBuilderL.start();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(processL.getInputStream()));
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(processL.getOutputStream()));
                    bufferedWriter.write(input);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    exitCode=0;
                    try {
                        exitCode = processL.waitFor();
                        System.out.println("Comming to here");
                    } catch (InterruptedException e) {
                        System.out.println("Exception with the process");
                    }
                    //num1=p1.waitFor();

                    if(exitCode==0)
                    {
                        String line;
                        ans1="";
                        while ((line = bufferedReader.readLine()) != null) {
                            ans1+=line;
                            ans1+="\n";
                        }
                    }
                    bufferedReader.close();
                    currentRunning=false;
                    System.out.println(exitCode);
                    if(exitCode!=0 && exitCode!=143 && exitCode!=137)
                    {
                        //System.out.println(exitCode);
                        ans1=new String(readAllBytes(new File(errorFile).toPath()));
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else if(exitCode==143 || exitCode==137)
                    {
                        System.out.println("HEHE");
                        ans1="TIME LIMIT EXIST";
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else
                    {
                        currentRunning=false;
                        //System.out.println(ans1);
                        //outputArr1[currentRunningIndex]=ans1;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        //System.out.println(ans1);
                        response.getWriter().println(ans1);
                    }
                    System.out.println(exitCode);
                    System.out.println(Thread.currentThread().isInterrupted()+"");
                    currentRunning=false;
                }
                else
                {
                    ans1=new String(readAllBytes(new File(errorFile).toPath()));
                    response.getWriter().println(ans1);
                }
                System.out.println(ans1);
                System.out.println(exitCode);
            }else if(lang.equals("java"))
            {
                String code=request.getParameter("code");
                String input=request.getParameter("input");
                input+="\n";
                byte[] bytes=code.getBytes();
                ProcessBuilder processBuilder1=new ProcessBuilder();
                //processBuilder1.directory(new File(directory));
                File file=new File(javaFile);
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                String ans1="";
                int exitCode=0;
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
                fileOutputStream.close();

                processBuilder1.command(javaCompile);
                processBuilder1.redirectError(new File(errorFile));
                Process p1=processBuilder1.start();
                int num1=0;
                try {
                    num1=p1.waitFor();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if(num1==0)
                {
                    processBuilderL = new ProcessBuilder();
                    //processBuilder2.directory(new File("/home/immohan/Documents/"));
                    processBuilderL.redirectError(new File(errorFile));
                    processBuilderL.command(javaRun);

                    ans1="";
                    myThread myThread1=new myThread();
                    myThread1.start();
                    processL=processBuilderL.start();
                    //num1=p1.waitFor();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(processL.getInputStream()));
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(processL.getOutputStream()));
                    bufferedWriter.write(input);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    exitCode=0;
                    try {
                        exitCode = processL.waitFor();
                        System.out.println("Comming to here");
                    } catch (InterruptedException e) {
                        System.out.println("Exception with the process");
                    }
                    if(exitCode==0)
                    {
                        String line;
                        ans1="";
                        while ((line = bufferedReader.readLine()) != null) {
                            ans1+=line;
                            ans1+="\n";
                        }
                    }
                    bufferedReader.close();
                    currentRunning=false;
                    System.out.println(exitCode);
                    if(exitCode!=0 && exitCode!=143 && exitCode!=137)
                    {
                        //System.out.println(exitCode);
                        ans1=new String(readAllBytes(new File(errorFile).toPath()));
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else if(exitCode==143 || exitCode==137)
                    {
                        System.out.println("HEHE");
                        ans1="TIME LIMIT EXIST";
                        currentRunning=false;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        response.getWriter().println(ans1);
                    }
                    else
                    {
                        currentRunning=false;
                        //System.out.println(ans1);
                        //outputArr1[currentRunningIndex]=ans1;
                        System.out.println(exitCode);
                        if(!Thread.currentThread().isInterrupted())
                        {
                            System.out.println("BREAKING THE CODE");
                            myThread1.interrupt();
                            //outputArr1[currentRunningIndex]=ans1;
                        }
                        //System.out.println(ans1);
                        response.getWriter().println(ans1);
                    }
                    System.out.println(exitCode);
                    System.out.println(Thread.currentThread().isInterrupted()+"");
                    currentRunning=false;
                }
                else
                {
                    ans1=new String(readAllBytes(new File(errorFile).toPath()));
                    response.getWriter().println(ans1);
                }
                System.out.println(ans1);
                System.out.println(exitCode);
            }

        }
        else
        {
            response.getWriter().println("CHECKED SUCCESSFULL");
        }
    }
}

class codeInfo
{
    String code,lang,input;
    int index;
    codeInfo(String code, String lang, String input, int index)
    {
        this.code=code;
        this.lang=lang;
        this.input=input;
        this.index=index;
    }
}

