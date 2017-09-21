<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/icon.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/css/app.css" rel="stylesheet">
	<script src="<%=request.getContextPath() %>/statics/jquery/jquery-1.12.3.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/js/jquery.easyui.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var ctp = "<%=request.getContextPath() %>";
	</script>
	<style type="text/css">
		.spanCls{
			width: 60px;
			height: 25px;
			line-height: 28px;
			display: block;
			float:left;
		}
		.inputCls {
			width: 250px;
			height: 25px;
			line-height: 25px;
		}
		.pop_but_a {
		    display: block;
		    float: left;
		    background: url(statics/images/pop_img_but.png) no-repeat center;
		    height: 26px;
		    line-height: 26px;
		    width: 75px;
		    text-align: center;
		    color: #333;
		    text-decoration: none;
		    border: 0px;
		    outline: none;
	  }
	</style>
</head>
<body>
 <div id="w" class="easyui-window" title="登录"  style="width:400px;height:200px;padding:20px;"
   data-options="closed:false,modal:true,minimizable:false,maximizable:false,collapsible:false,closable:false">
   <p><span class="spanCls">用户名:</span><input type="text" id="userName"  class="inputCls" onkeypress="if(event.keyCode==13){login();return false;}"></p>
   <p><span class="spanCls">密&nbsp;&nbsp;&nbsp;码:</span><input type="password"  id="password" class="inputCls"  onkeypress="if(event.keyCode==13){login();return false;}"></p>
   <a  class="pop_but_a" onclick="login()" style="float: right;margin-right: 30px;cursor: pointer;">登录</a>
</div>
</body>
<script type="text/javascript">
function login(){
	var userName = $('#userName').val();
	var password = $('#password').val();
	var url = ctp+'/system/login.do'
	$.post(url,{loginUser:userName,loginPassword:password},function(data){
		if(data.code=='200'){
			location.href = ctp+'/conf/index.do';
		}else{
			$.messager.alert('提示',data.message);
		}
	});
}
</script>
</html>
