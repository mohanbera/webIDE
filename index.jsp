<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>codingGround</title>
    <link rel="shortcut icon" href="https://webiconspng.com/wp-content/uploads/2017/01/Programming-Coding-Icon.png" type="image/png" />
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.8/ace.js"></script>
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
<body style="background-color: #010C04">


<div id="codeEditor" style="left:5%; height: 90%; width: 65%;">
</div>
<div id="inputEditor" style="position:absolute; top:1%; left:70%; height: 43%; width: 30%;"></div>
<div id="outputEditor" style="position: absolute; top: 44.3%; left:70%; height: 45%; width: 30%;"></div>

<button id="Run" style="position: absolute; top: 90%; left:58.3%; height: 3%; width: 11.5%; background-color: #166D08; border: #166D08">Run</button>

<button id="CBtn" style="position: absolute; top: 1%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #201C1D; outline: none;">C</button>
<button id="cppBtn" style="position: absolute; top: 4.3%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #201C1D; outline: none;">CPP</button>
<button id="javaBtn" style="position: absolute; top: 7.6%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #201C1D; outline: none;">Java</button>
<button id="pyBtn" style="position: absolute; top: 10.9%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #201C1D; outline: none; ">Py2</button>

<label style="position: fixed; top: 95.7%; left: 95.5%; font-size: small; color: #201C1D">
  A Project By Mohan Bera
</label>

<script type="text/javascript">


  var code =ace.edit("codeEditor");
  code.getSession().setMode("ace/mode/c_pp");
  code.setTheme("ace/theme/monokai");

  var input =ace.edit("inputEditor");
  input.getSession().setMode("ace/mode/c_pp");
  input.setTheme("ace/theme/monokai");

  var output =ace.edit("outputEditor");
  output.getSession().setMode("ace/mode/c_pp");
  output.setTheme("ace/theme/monokai");

  code.setOptions({
    fontSize: "11pt"
  });

  var curLang="C";

  var cCode='#include<stdio.h>\n' +
          '\n' +
          'int main()\n' +
          '{\n' +
          '    printf("Hello World!");\n' +
          '    return 0;\n' +
          '}';

  var cppCode='#include<iostream>\n' +
          'using namespace std;\n' +
          '\n' +
          'int main()\n' +
          '{\n' +
          '    cout<<"Hello World!"<<endl;\n' +
          '    return 0;\n' +
          '}';

  var javaCode='public class Main\n' +
          '{\n' +
          '    public static void main(String[] mb)\n' +
          '    {\n' +
          '        System.out.println("Hello World!");\n' +
          '    }\n' +
          '}\n';

  var pyCode='print ("Hello World!")';

  code.setValue(cCode);
  code.$blockScrolling = Infinity;
  output.$blockScrolling = Infinity;
  input.$blockScrolling = Infinity;

  $(document).ready(function () {

      code.$blockScrolling = Infinity;
      output.$blockScrolling = Infinity;
      input.$blockScrolling = Infinity;
    firstChange();

    $('#Run').click(function()
    {
      var code =ace.edit("codeEditor");
      var outPut=ace.edit("outputEditor");
      var input=ace.edit("inputEditor");
      outPut.setValue("",1);
      outPut.$blockScrolling = Infinity;
      input.$blockScrolling = Infinity;
      code.$blockScrolling = Infinity;
      $.ajax({
        type: 'POST',
        url: "myServletClass",
        data: {
          status: 'code',
          lang: curLang,
          code: code.getValue(),
          input: input.getValue(),
        },
        success:
                function(data)
                {
                  outPut.setValue(data,-1);
                },
      });
    });

    $('#CBtn').click(function()
    {
      code.getSession().setMode("ace/mode/c_cpp");
      code.setTheme("ace/theme/monokai");

      input.getSession().setMode("ace/mode/c_cpp");
      input.setTheme("ace/theme/monokai");

      output.getSession().setMode("ace/mode/c_cpp");
      output.setTheme("ace/theme/monokai");
      saveChange();
      curLang="C";

      changelang();
      code.setValue(cCode,1);
      $("#CBtn").css("background-color","#414446");
    });

    $('#cppBtn').click(function()
    {
      code.getSession().setMode("ace/mode/c_cpp");
      code.setTheme("ace/theme/monokai");

      input.getSession().setMode("ace/mode/c_cpp");
      input.setTheme("ace/theme/monokai");

      output.getSession().setMode("ace/mode/c_cpp");
      output.setTheme("ace/theme/monokai");
      saveChange();
      curLang="cpp";

      changelang();
      code.setValue(cppCode,1);
      $("#cppBtn").css("background-color","#414446");
    });

    $('#javaBtn').click(function()
    {
      code.getSession().setMode("ace/mode/java");
      code.setTheme("ace/theme/monokai");

      input.getSession().setMode("ace/mode/java");
      input.setTheme("ace/theme/monokai");

      output.getSession().setMode("ace/mode/java");
      output.setTheme("ace/theme/monokai");
      saveChange();
      curLang="java";

      changelang();
      code.setValue(javaCode,1);
      $("#javaBtn").css("background-color","#414446");
    });

    $('#pyBtn').click(function()
    {
      code.getSession().setMode("ace/mode/python");
      code.setTheme("ace/theme/monokai");

      input.getSession().setMode("ace/mode/python");
      input.setTheme("ace/theme/monokai");

      output.getSession().setMode("ace/mode/python");
      output.setTheme("ace/theme/monokai");
      saveChange();
      curLang="py";
      changelang();
      code.setValue(pyCode,1);
      $("#pyBtn").css("background-color","#414446");
    });

    function changelang()
    {
      $("#CBtn").css("background-color","#201C1D");
      $("#cppBtn").css("background-color","#201C1D");
      $("#javaBtn").css("background-color","#201C1D");
      $("#pyBtn").css("background-color","#201C1D");
    }

    function saveChange()
    {
      if(curLang==='C')
      {
        cCode = code.getValue();
      }
      else if(curLang==='cpp')
      {
        cppCode=code.getValue();
      }
      else if(curLang==='java')
      {
        javaCode=code.getValue();
      }
      else
      {
        pyCode=code.getValue();
      }
    }

    function firstChange()
    {
      code.getSession().setMode("ace/mode/c_cpp");
      code.setTheme("ace/theme/monokai");

      input.getSession().setMode("ace/mode/c_cpp");
      input.setTheme("ace/theme/monokai");

      output.getSession().setMode("ace/mode/c_cpp");
      output.setTheme("ace/theme/monokai");
      saveChange();
      curLang="C";
        code.$blockScrolling = Infinity;
        output.$blockScrolling = Infinity;
        input.$blockScrolling = Infinity;
      changelang();
      code.setValue(cCode,1);
      $("#CBtn").css("background-color","#414446");
    }

  });

</script>
</body>
</html>