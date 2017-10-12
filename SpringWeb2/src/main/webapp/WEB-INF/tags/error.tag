<%@ tag pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
  
<c:if test="${not empty message}">  
    <script type="text/javascript">  
        $(function() {  
            var msg = {};  
            try {  
                msg = $.parseJSON('${message}');  
            } catch (e) {  
                msg.content = '${message}';  
                msg.result = true;  
            }  
              
            if (msg.result == true) {  
                $.growl({  
                    title: '成功提示：',  
                    message: msg.content  
                },{  
                    type:"success"  
                });  
            } else {  
                $.growl({  
                    title: '失败提示：',  
                    message: msg.content  
                },{  
                    type:"danger"  
                });  
            }  
        });  
    </script>  
</c:if>  
