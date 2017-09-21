<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/icon.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/css/app.css" rel="stylesheet">
	<script src="<%=request.getContextPath() %>/statics/jquery/jquery-1.12.3.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/js/jquery.easyui.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/locale/easyui-lang-zh_CN.js"></script>
	
	<link href="<%=request.getContextPath() %>/statics/zTree/3.5/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
	<script src="<%=request.getContextPath() %>/statics/zTree/3.5/js/jquery.ztree.all-3.5.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/zTree/3.5/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript">
		var ctp = "<%=request.getContextPath() %>";
	</script>
    <script src="<%=request.getContextPath() %>/statics/js/index.js"></script>
<style type="text/css">
	body {
	    font-size: 12px;
	    font-family: "微软雅黑";
	    background: #FDFDFD;
	    min-width: 1200px;
	    color: #333;
	}
    .pop_table {
	    width: 100%;
	    margin: 0px auto;
	}
	.pop_table tr td {
	    border: 0px;
	    padding: 5px 2px;
	    font-size: 12px;
	    font-family: "微软雅黑";
	    color: #333;
	}
	.pop_td_txt {
	    text-align: right;
	}
	.pop_inp_add {
	    border: 1px solid #E1E1E1;
	    height: 20px;
	    width: 150px;
	}
	.pop_inp {
	    height: 25px;
	    border: 1px solid #E1E1E1;
	    outline: none;
	    width: 180px;
    }
	.pop_but {
	    border: 0px;
	    margin: 0px auto;
	}
</style>
<body>
<!-- 树形目录右键菜单 -->
<div id="rcmenu" class="easyui-menu" style="">
    <div  id="addPath" data-options="iconCls:'icon-add'">
        新增
    </div>
    <div id="deletePath" data-options="iconCls:'icon-cancel'">
        删除
    </div>
    <div class="menu-sep"></div>
</div>
<!-- 新增path windows -->
<div id="path_Window" class="easyui-window" title="新增" icon="icon-save" 
	 data-options="closed:true,modal:true,minimizable:false,maximizable:false,collapsible:false," >
   <form id="path_form" method="post">
	    <table cellpadding="0" cellspacing="0" border="0" class="pop_table">
			<tr>
				<td class="pop_td_txt">节点名称：</td>
				<td><input class="pop_inp" type="text" id="dataId" name="dataId" ></td>
			</tr>
			<tr>
				<td class="pop_td_txt">配置信息：</td>
<!-- 				<td><input class="pop_inp" type="text" id="configuration" name="configuration"></td> -->
				<td><textarea id="configuration" name="configuration" style="width: 600px;height: 150px; border: 1px solid #E1E1E1;outline: none;resize:none;"></textarea></td>
			</tr>
		</table>
	</form> 
	<div class="pop_bot_con">
		<table class="pop_but" cellpadding="0" cellspacing="0" border="0">
	 		<tr>
			  	<td><a class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" onclick="return path_yes()">提交</a> </td>
			  	<td><a class="easyui-linkbutton" icon="icon-undo" href="javascript:void(0)" onclick="$('#path_Window').window('close')">返回</a></td>
		 	</tr>
    	</table>
    </div>
 </div>
	<div style="height:50px;margin-top: -8px;background:#eee; overflow-y:hidden" >
		<span style="font-size: 20px;font-weight: bold;margin: 10px 0px 0px 60px; display: block;">ZK配置管理</span>
		<span style="font-size: 15px;font-weight: bold;color: rgb(47, 102, 61);float: right;margin: -20px 20px 0px 0px;cursor: pointer;display: block;" onclick="loginOut()">注销</span>
	</div>
  <div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'west',split:true" title="业务菜单" style="width:220px;">
      <div id="config" class="easyui-accordion" data-options="fit:true,border:false">
        <div title="配置管理">
          <ul id="tree" class="ztree" style="width:560px; overflow:auto;"></ul>
        </div>
    </div>      
    </div>
    <div data-options="region:'center',iconCls:'icon-ok'">
      <div id="tt" class="easyui-tabs" data-options="fit:true,border:false,plain:true" style="width:100%;height: 100%">
        <div title="我的工作区" style="padding:5px" data-options="iconCls:'icon-man'">
          <p>欢迎来到配置管理系统.</p>
        </div>
      </div>
      </div>
</div>
</body>
<script>
    var zTree, setting, treeNodes,path;
	var PATH_SAVE = '';//右键点击path全路径
    $(function () {
       setting = {
       data: {
	       simpleData: {
		       enable: true,
		       idKey: "id",
		       pIdKey: "parentId",
		       rootPId: null
            }
        },
        callback:{
		   beforeRightClick:function(treeId,treeNode){
			   PATH_SAVE = '';
			   var treeObj=$.fn.zTree.getZTreeObj("tree");
			   getNodePathAll(treeObj,treeNode);
			   PATH_SAVE = '/'+PATH_SAVE+treeNode.name
		   },
           beforeClick :function(){
           },
		   onClick: getQueryPath,
           beforeChange:function(){
               
           },
          }
        };
		ajaxFun();
// 		getQueryPath();
    });
	function ajaxFun(){
		$.ajax({
			url:ctp+'/conf/index/queryData.do',
			type:'post',
			data:{},
			dataType: "json",
			success: function(data){
				if(data.code == '200'){
					initData(data.result);
				}
			},
			error:function(data){
				
			}
		});
	}
	function initData(data){
		treeNodes = new Array();
		for(var i = 0;i < data.length;i++){
			var node = data[i];
			var json = {};
			if(node.pid == null || node.pid == ""){
				json = {id:node.id,name:node.str,open:true}
			}else{
				json = {id:node.id,name:node.str,parentId:node.pid}
			}
		    treeNodes.push(json);
		}
        zTree = $.fn.zTree.init($("#tree"), setting, treeNodes);
// 		zTree.checkNode(node, true, true);//根据节点勾选
	}

	function getQueryPath(){
		path = "";
        var treeObj=$.fn.zTree.getZTreeObj("tree");
        var sNodes = treeObj.getSelectedNodes();
        var nodes = treeObj.getNodesByParam("parentId", sNodes[0].id, null);//根据当前节点id查下级节点parentId
        if(nodes.length>0){
        	return;
        }
        if (sNodes.length > 0) {
        	path = path+sNodes[0].name;
        	getPath(treeObj,sNodes[0]);
        }
        path = "/"+path;
        var tabsData = $('#tt').tabs('tabs');
        if(tabsData.length>=2){
        	for(var i=1;i<tabsData.length;i++){
        		$("#tt").tabs('close',i);
        	}
        }
// 		for(var i=0;i<tabsData.length;i++){
// 			if(tabsData[i].panel('options').id==path){
// 				return;
// 			}
// 		}
       var queryURL = ctp + '/conf/index/query.do?queryParam='+path;
	   var content = '<iframe scrolling="auto" frameborder="0"  src="'+queryURL+'" style="width:100%;height:100%;"></iframe>';
	   $('#tt').tabs('add',{
		    title:sNodes[0].name,
		    content:content,
		    closable:true
		});
// 	   if (!$('#tt').tabs('exists', path)) {  
// 			$('#tt').tabs('add',{
// 			    title:path,
// 			    content:content,
// 			    closable:true
// 			});
// 	   }else{
// 		   $("#tt").tabs("select", path);  
//            var selTab = $('#tt').tabs('getSelected');  
//            $('#tt').tabs('update', {  
//                tab: selTab,  
//                options: {  
//                    content:content  
//                }  
//            })
// 	   }
  }
  function getPath(treeObj,node){
	  if(node.parentId != null){
		  var pid = node.parentId;
		  var pNode = treeObj.getNodeByParam("id",pid);
		  path = pNode.name+"/"+path;
		  getPath(treeObj,pNode);

	  }
  }
  
  //根据当前节点递归拼接路径找到根节点
  function getNodePathAll(treeObj,node){
	  if(node.parentId != null){
		  var pid = node.parentId;
		  var pNode = treeObj.getNodeByParam("id",pid);
		  PATH_SAVE = pNode.name+"/"+PATH_SAVE;
		  getNodePathAll(treeObj,pNode);
	  }
  }
  
  //form验证
  function validataForm(){
	  $('#dataId').validatebox({
		  required: true
// 		  ,
// 		  validType:['length[2,20]']
	  });
	  $('#configuration').validatebox({
		  required: true
// 		  ,
// 		  validType:['length[2,20]']
	  });
  }
  //右键点击事件绑定
  $(document).on('contextmenu', '#tree a', function(e){
      e.preventDefault();
      $('#rcmenu').menu('show', {
          left: e.pageX,
          top: e.pageY
      });
  });
  //新增path事件绑定
  $(document).on('click', '#addPath', function(e){
	  validataForm();
	  $('#path_form').form('clear');
	  $('#path_form').form('disableValidation');
	  $('#path_Window').window({width:'700px',top:"200px"});
	  $('#path_Window').window('open');
	  
  });
  
  //删除节点
  $(document).on('click', '#deletePath', function(e){
	  $.messager.confirm('提示','是否删除该节点?',function(r){
		    if (r){
		    	var obj = {dataId:PATH_SAVE};
		    	$.post( ctp +'/conf/flow/deleteConfiguration.do',obj,function(data){
					  if(data.code=='200'){
						  $.messager.alert('提示','操作成功');
						  ajaxFun();
					  }
				  });
		    }
		});
	  
  });
  
  //保存path
  function path_yes(){
	  $('#path_form').form('enableValidation');
	  var dataId = $('#dataId').val();
	  var configuration = $('#configuration').val();
	  var flag = $('#path_form').form('validate');
	  if(flag){
		  dataId = PATH_SAVE+'/'+dataId;
		  var obj = {dataId:dataId,configuration:configuration};
		  $.post( ctp +'/conf/flow/insertConfiguration.do',obj,function(data){
			  $('#path_Window').window('close');
			  if(data.code=='200'){
				  $.messager.alert('提示','操作成功');
				  ajaxFun();
			  }
		  });
	  }
  }
  
  function loginOut(){
	  $.post( ctp +'/system/loginOut.do',function(data){
			if(data.code=='200'){
				location.href= ctp;
			}else{
				$.messager.alert('提示',data.message);
			}
		});
  }

</script>

</html>
