<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
  <head>
    <title>文件跨域上传</title>
    <script src="/static/js/jquery.min.js?v=1"></script>
  </head>
  
  <body>
    <form id="testForm" enctype="multipart/form-data" >
      <input name="upfile" id="upfile" type="file" />
       <input type="hidden" name="upfileElemName" value="upfile">
      <input type="hidden" name="module" value="admin">
      <input type="button" value="Upload" onclick="ajaxUpload('admin', 'testForm');"/>
    </form>
    
    <input id="upElem" type="file" onchange="ajaxUpload('admin','upElem')"/>
    <input id="val_upElem" type="hidden"/>
    <img id="prev_upElem">
    
    <script type="text/javascript">
      /* function initImg(elemId, module){
    	  var elemStr = $("#"+elemId)[0].outerHTML;
    	  var fileName = $("#"+elemId).attr("name");
    	  var formId = "form_"+new Date().getTime();
    	  var formstr = '<form id="'+formId+'" enctype="multipart/form-data">' +
    	                 '<input name="'+fileName+'" type="file" onchange="ajaxUpload(\''+formId +'\', \''+module+'\', \''+elemId+'\')"/>' + 
    	                '<input type="hidden" name="upfileElemName" value="'+fileName+'">' +
    	                '<input type="hidden" name="module" value="'+module+'">' +
    	                '</form>';
    	  //$("body").append(formstr);
    	  $("#"+elemId).replaceWith(formstr);
      }
      
      initImg('upElem', 'admin'); */
      
      function ajaxUpload(module, elemId, success){
    	  var currElem = $("#"+elemId);
    	  var fileName = elemId;
    	  var formId = "form_"+elemId+ "_"+new Date().getTime();
    	  var formstr = '<form id="'+formId+'" enctype="multipart/form-data" style="display:none">' +
    	                 //'<input name="'+fileName+'" type="file" value="'+value+'" />' + 
    	                '<input type="hidden" name="upfileElemName" value="'+fileName+'">' +
    	                '<input type="hidden" name="module" value="'+module+'">' +
    	                '</form>';
    	  $("body").append(formstr);
    	  currElem.after(currElem.clone(true));
    	  currElem.attr("name", elemId);
    	  $("#" + formId).append(currElem);
    	  var formData = new FormData($("#"+formId)[0]);
    	  $.ajax({
    	      url: 'http://upfile-test.baoxianjie.net/file/uploadImg.do',  //Server script to process data
    	      type: 'POST',
    	      //Ajax events
    	      //beforeSend: beforeSendHandler,
    	      success: function(data){
    	    	  alert(JSON.stringify(data));
    	    	  if($("#val_" + elemId).length > 0){
        	          $("#val_" + elemId).val(data.model.fileInfo.accessUri);
        	        }
        	        if($("#prev_" + elemId).length > 0){
            	          $("#prev_" + elemId).attr("src", data.model.fileInfo.accessUrl);
            	    }
        	        if(typeof success == 'function'){
        	        	success(data.model.fileInfo);
        	        }
    	    	  
    	    	  //$("#" + formId).remove();
    	      },
    	      //error: errorHandler,
    	      // Form data
    	      data: formData,
    	      //Options to tell jQuery not to process data or worry about content-type.
    	      cache: false,
    	      contentType: false,
    	      processData: false
    	  });
      }
    </script>
  </body>
</html>