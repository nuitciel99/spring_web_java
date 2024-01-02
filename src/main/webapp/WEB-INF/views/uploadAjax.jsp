<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>2023. 10. 23.오전 9:55:39</title>
<script src="${pageContext.request.contextPath}/resources/sbadmin2/vendor/jquery/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/sbadmin2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<script>

	$(function(){
		var regex = new RegExp("(.*?)\(.exe|sh|bat|js|jsp|sql)$")
		var maxSize = 2 * 1024 * 1024;
		
		function checkExtension(fileName, fileSize){
			if(fileSize >= maxSize){
				alert("파일 크기 초과");
				return false;
			}
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드 불가합니다.");
				return false;
			}
			return true;
		}
		
		function showUploadedFile(arr){
			var str = "";
			var thumbStr = "";
			
			for(var i in arr){
				str += `<li><a href="/download\${arr[i].url}"><i class="far fa-file"></i> \${arr[i].origin}</a> 
						<span data-uuid="\${arr[i].uuid}" data-url="\${arr[i].url}"><i class="fas fa-times-circle"></i></span></li>`;
				if(arr[i].image) {
					thumbStr += `<li><a href="/download\${arr[i].url}"><img src='display\${arr[i].thumb}'></a> 
						<span data-uuid="\${arr[i].uuid}" data-url="\${arr[i].url}"><i class="fas fa-times-circle"></i></span></li>`;
				}
			}
			$(".uploadResult ul").append(str);
			$(".thumbResult ul").append(thumbStr);
		}
		
		// 이미지 원본 보기
		$(".thumbResult ul").on("click", "a", function(){
			event.preventDefault();
			console.log(this);
			
			// light box
			
		});
		
		// x 클릭 시 이벤트 부여 : 두개 모두 삭제
		$(".upload-wrapper").on("click", "span", function(){
			console.log($(this).data("uuid"));
			//$(this).closest("li").remove();
			var uuid = $(this).data("uuid");
			var url = $(this).data("url");
			var $this = $(this);
			$.post("/deleteFile" + url).done(function(result){
				$this.closest(".upload-wrapper").find("span").each(function(){
					if(uuid === $(this).data("uuid")){
						$(this).closest("li").remove();
					}
				})
			})
		});
		
		
		$("#uploadBtn").click(function(){
			event.preventDefault();
			var formdata = new FormData();
			var inputFile = $(document.frm.files);
			var files = inputFile.get(0).files;
			
			console.log(files);
			
			var filesSize = 0;
			for(var i = 0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
				formdata.append("files", files[i]);
				filesSize += files[i].size;
			}
			
			// 파일 당 크기 2MB 이하, 전체 10MB
			if(filesSize > 10 * 1024 * 1024){
				alert("전체 업로드할 파일의 합계가 10MB 이하로 지정하세요}");
				return false;
			}
			
			$.ajax({
				url : '/uploadAjax',
				processData : false,
				contentType : false,
				data : formdata,
				type : 'post',
				dataType : 'json',
				success : function(result){
					console.log(result);
					inputFile.val("");
					showUploadedFile(result);
				}
			})
			
		})
	});
</script>

</head>
<body>
	<form enctype="multipart/form-data" method="post" name="frm">
		<input name="common" value="1234">
		<input type="file" name="files" multiple>
		<button id="uploadBtn" type="button">파일 업로드</button>
	</form>
	<div class="upload-wrapper">
		<div class="uploadResult">
			<ul></ul>
		</div>
		<div class="thumbResult">
			<ul>
			</ul>
		</div>
	</div>
</body>
</html>