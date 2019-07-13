<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>codingGround</title>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.8/ace.js"></script>
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
<body style="background-color: #010C04">


<div id="codeEditor" style="left:5%; height: 90%; width: 65%;">public class Main
print ("HELLO WORLD!")
</div>
<div id="inputEditor" style="position:absolute; top:1%; left:70%; height: 43%; width: 30%;"></div>
<div id="outputEditor" style="position: absolute; top: 44.3%; left:70%; height: 45%; width: 30%;"></div>

<button id="Run" style="position: absolute; top: 90%; left:58.3%; height: 3%; width: 11.5%; background-color: #166D08; border: #166D08">Run</button>
<button id="Run2" style="position: absolute; top: 90%; left:70%; height: 3%; width: 11.5%; background-color: #166D08; border: #166D08">Run</button>


<button id="CBtn" style="position: absolute; top: 1%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #166D08; outline: none;">C</button>
<button id="cppBtn" style="position: absolute; top: 4.3%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #166D08; outline: none;">CPP</button>
<button id="javaBtn" style="position: absolute; top: 7.6%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #166D08; outline: none;">Java</button>
<button id="pyBtn" style="position: absolute; top: 10.9%; left:1%; height: 3%; width: 5%; color: grey; background-color: #201C1D; border: #166D08; outline: none; ">Py2</button>


<script type="text/javascript">


  var code =ace.edit("codeEditor");
  code.getSession().setMode("ace/mode/python");
  code.setTheme("ace/theme/monokai");

  var input =ace.edit("inputEditor");
  input.getSession().setMode("ace/mode/python");
  input.setTheme("ace/theme/monokai");

  var output =ace.edit("outputEditor");
  output.getSession().setMode("ace/mode/python");
  output.setTheme("ace/theme/monokai");

  var curLang="C";



  $(document).ready(function () {

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

    $('#Run2').click(function()
    {
      var code =ace.edit("codeEditor");
      var outPut=ace.edit("outputEditor");
      var input=ace.edit("inputEditor");
      outPut.setValue("",1);
      outPut.$blockScrolling = Infinity;
      input.$blockScrolling = Infinity;
      code.$blockScrolling = Infinity;
      $.ajax({
        url: "myServletClass",
        data: {
          status: 'check',
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

      curLang="C";

      changelang();
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

      curLang="cpp";

      changelang();
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

      curLang="java";

      changelang();
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

      curLang="py";
      changelang();
      $("#pyBtn").css("background-color","#414446");
    });

    function changelang()
    {
      $("#CBtn").css("background-color","#201C1D");
      $("#cppBtn").css("background-color","#201C1D");
      $("#javaBtn").css("background-color","#201C1D");
      $("#pyBtn").css("background-color","#201C1D");
    }

  });

</script>
</body>
</html>