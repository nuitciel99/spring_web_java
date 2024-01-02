<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<jsp:include page="../includes/head.jsp" />
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
				str += `<li class="list-group-item" data-uuid="\${arr[i].uuid}" data-origin="\${arr[i].origin}" data-image="\${arr[i].image}" data-path="\${arr[i].path}"><a href="/download\${arr[i].url}"><i class="far fa-file"></i> \${arr[i].origin}</a> 
						<span data-uuid="\${arr[i].uuid}" data-url="\${arr[i].url}" class="float-right"><i class="fas fa-times-circle"></i></span></li>`;
				if(arr[i].image) {
					thumbStr += `<li class="mt-2 col-sm-6 col-lg-4 text-center"><a href="/download\${arr[i].url}"><img src='/display\${arr[i].thumb}' class="img-fluid"></a> 
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
		
		
		//$("#uploadBtn").click()
		
		var uploadProcess = function(){
			//event.preventDefault();
			var formdata = new FormData();
			var inputFile = $("#files");
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
			
		}
	
		$("#files").change(uploadProcess);
	});
</script>
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
		<%-- <jsp:include page="../includes/sidebar.jsp" /> --%>
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">
            <!-- Main Content -->
            <div id="content">
				<jsp:include page="../includes/topbar.jsp" />
                <!-- Begin Page Content -->
                <div class="container">
                    <!-- DataTales Example -->
                    <div class="col-9 mx-auto">
	                    <!-- Page Heading -->
	                    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
	                    <div class="card shadow mb-4">
	                        <div class="card-header py-3">
	                            <h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
	                        </div>
	                        <div class="card-body">
	                        	<form method="post">
	                        	<div class="form-group" action="register">
									<label for="title">Title</label>
									<input class="form-control" id="title" name="title">
								</div>
	                        	<div class="form-group">
									<label for="boardContent">Content</label>
									<textarea class="form-control" id="boardContent" name="content" rows="10"></textarea>
								</div>
	                        	<div class="form-group">
									<label for="writer">Writer</label>
									<input class="form-control" id="writer" name="writer" value='<sec:authentication property="principal.username"/>' readonly>
								</div>
								
								<input type="hidden" name="pageNum" value="1"> 
								<input type="hidden" name="amount" value="${criteria.amount}"> 
								<sec:csrfInput/>
								
								<!-- <input type="hidden" name="attachs[0].uuid" value="myUUid"> -->
								
								<button class="btn btn-primary" id="btnBoardSubmit">Submit Button</button>
								<button class="btn btn-info" type="reset">Reset Button</button>
								</form>
	                        </div>
	                    </div>
	                    <div class="card shadow mb-4">
	                        <div class="card-header py-3">
	                            <h6 class="m-0 font-weight-bold text-primary">File Attach</h6>
	                        </div>
	                        <div class="card-body">
		                        <div class="input-group">
			                        <div class="custom-file">
											<input type="file" class="custom-file-input" id="files" name="files" multiple>
											<label for="files" class="custom-file-label">Choose Files</label>
									</div>
								</div>
								<div class="upload-wrapper">
									<div class="uploadResult mt-3">
										<ul class="list-group">
										</ul>
									</div>
									<div class="thumbResult mt-3">
										<ul class="list-unstyled row">
										</ul>
									</div>
								</div>
		                	</div>
		                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- End of Main Content -->

			<jsp:include page="../includes/footer.jsp" />

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>
	<jsp:include page="../includes/modal.jsp" />


    <!-- Bootstrap core JavaScript-->
    
    <script src="${pageContext.request.contextPath}/resources/sbadmin2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/resources/sbadmin2/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="${pageContext.request.contextPath}/resources/sbadmin2/js/sb-admin-2.min.js"></script>
    
    <script >
    	$(function(){
    		$("#btnBoardSubmit").click(function(){
    			event.preventDefault();
    			var $frm = $(this).closest("form");
    			$(".uploadResult ul li").each(function(i){
    				
    				$("<input>").attr({name : "attachs[" + i + "].uuid", value : $(this).data("uuid"), type : "hidden"}).appendTo($frm);
    				$("<input>").attr({name : "attachs[" + i + "].origin", value : $(this).data("origin"), type : "hidden"}).appendTo($frm);
    				$("<input>").attr({name : "attachs[" + i + "].image", value : $(this).data("image"), type : "hidden"}).appendTo($frm);
    				$("<input>").attr({name : "attachs[" + i + "].path", value : $(this).data("path"), type : "hidden"}).appendTo($frm);
    				
    			})
    			
    			let obj = $frm.serializeArray();
    			console.log(obj);
    			
    			// $(this).closest("form").submit();
    			$frm.submit();
    		})
    		
    		/* $("#files").change(uploadProcess); */
    	})
    </script>

</body>

</html>