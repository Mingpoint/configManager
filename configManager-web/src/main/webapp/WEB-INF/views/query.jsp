<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/bootstrap/easyui.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/esayui/themes/icon.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/css/app.css" rel="stylesheet">
	<link href="<%=request.getContextPath() %>/statics/css/button.css" rel="stylesheet">
	<script src="<%=request.getContextPath() %>/statics/jquery/jquery-1.12.3.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/js/common.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/js/jquery.easyui.min.js"></script>
	<script src="<%=request.getContextPath() %>/statics/esayui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var ctp = "<%=request.getContextPath() %>";
	</script>
    <script src="<%=request.getContextPath() %>/statics/js/query.js"></script>
</head>
<body>
  <div class="easyui-layout" style="width:100%;height:100%;" data-options="fit:true,fitColumns:true">
    <div id="search" class="easyui-panel" title="查询条件">
      <form id="searchForm">
        <table>
          <tr>
<!--             <td>groupId:</td> -->
<!--             <td> -->
<!--               <input type="text" id="groupId" name="groupId" /></td> -->
            <td>path:</td>
            <td>
              <input type="text" id="dataId" name="dataId" value="${path}"/></td>
            <td width='20px'>&nbsp;</td>
            <td><input type="button" class="btn-search" onclick="queryData()" value="查询" /></td>
            <td><input type="button" class="btn-clear" onclick="clearText('search','detailForm')" value="清空" /></td>
            <td width='40px'>&nbsp;</td>
<!--             <td><input type="button" class="btn-edit-add" onclick="preInsertData('addwindow')" value="新增" /></td> -->
          </tr>
        </table>
      </form>
    </div>
    <div style="height: 10px; display: block;"></div>
    <div id="search" class="easyui-panel" title="配置详细信息">
      <form id="detailForm">
        <table>
          <tr>
<!--             <td valign="top">配置详情:</td> -->
            <td>
              <textarea id="deatail_conf" rows="" cols="" class="textarea easyui-validatebox" style="height: 550px;width: 970px;resize:none;outline:none;border: 1px solid #E1E1E1;margin: auto 10px">${config}</textarea>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <input type="button" class="btn-ok" onclick="updateData()" value="修改" />
              <input type="button" class="btn-edit-remove" onclick="deleteData()" value="删除" />
            </td>
          </tr>
        </table>
      </form>
    </div>
    <div id="addwindow" class="easyui-dialog" title="添加" style="width: 1000px;height: 520px; display: block;" data-options="resizable:false,modal:true,closed:true">
      <form id="addForm">
        <table>
<!--           <tr> -->
<!--             <td>groupId:</td> -->
<!--             <td><input type="text" id="groupId" name="groupId" class="easyui-textbox" data-options="required:true"/></td> -->
<!--           </tr> -->
          <tr>
            <td>path:</td>
            <td><input type="text" id="dataId" name="dataId" class="easyui-textbox" data-options="required:true"/></td>
          <tr>
<!--             <td valign="top">配置详情:</td> -->
            <td>
              <textarea id="configuration" name="configuration" rows="" cols="" class="textarea easyui-validatebox" data-options="required:true" style="margin: 5px;height: 368px;width: 700px;resize:none;outline:none;border: 1px solid #E1E1E1;"></textarea>
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <input type="button" class="btn-search" onclick="insertData()" value="保存" />
              <input type="button" class="btn-clear" onclick="clearText('addForm')" value="清空" />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</body>
</html>