<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<jsp:include page="../includes/head.jsp" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/reply.js"></script>
	<sec:csrfMetaTags/>
	<script>
	console.log(replyService);
	
	/* replyService.add(
		{
		    reply:'비동기 댓글 테스트',
		    replyer:'작성자',
		    bno:194
		}, function(data){
		    console.log(data)
		},
		function(result){ alert(result) }
		); */
	
	// replyService.getList({bno}, data => console.log(data))
	
	// 댓글 조회
	// replyService.get(39, r=>console.log(r))
	
	// 삭제
	// replyService.remove(39, r=>console.log(r))
	
	// 수정
	// replyService.modify({reply:'수정 댓글', rno:38}, r=>console.log(r))
	
	$(function(){
		
		var csrfHeader = $('meta[name="_csrf_header"]').attr("content");
		var token = $('meta[name="_csrf"]').attr("content");
		console.log(csrfHeader, token);
		$(document).ajaxSend(function(e, xhr){
			xhr.setRequestHeader(csrfHeader, token);
		});
		
		const bno = '${board.bno}';
		var pageNum = 1;
		
		showList(1);
		
		function showList(page){
			
			console.log(page);
			//return;
			
			replyService.getList({bno:bno, page:page}, function(replyCnt, list) {

				console.log(replyCnt);
				console.log(list);

				if(page == -1){
					pageNum = Math.ceil(replyCnt / 10);
					showList(pageNum);
					showReplyPage(pageNum);
					return;
				}				
				
				let str ="";
				for(let r of list){
					str += `<li class="list-group-item px-0" data-rno="\${r.rno}">
            			<div class="header">
        				<strong>\${r.replyer}</strong>
        				<small class="float-right text-muted">\${replyService.displayTime(r.replyDate)}</small>
        			</div>
        			<p>\${r.reply}</p>
        		</li>`;
				}
				$(".chat").html(str);
				showReplyPage(replyCnt);
			})
		}
		
		// 댓글 작성 버튼 클릭 이벤트
		$("#addReplyBtn").click(function(){
			
			$("#replyModal input").val("").prop("readonly", false);
			$("#replyDate").closest("div").hide();
			$("#replyModal").find(".modal-footer button").hide().filter(":gt(1)").show();
			$("#replyModal").modal("show");
		});
		
		// 댓글 작성 이벤트
		$("#replyModal").find(".modal-footer button").eq(2).click(function(){
			
			const reply = $("#reply").val();
			const replyer = $("#replyer").val();
			
			let r = {reply, replyer, bno};
			console.log(r);
			$("#replyModal").modal("hide");
			replyService.add(
					r, function(result){ showList(-1)}); 
		});
		
		// 댓글 클릭 이벤트
		$(".chat").on("click", "li", function(){
			const rno = $(this).data("rno");
			console.log(rno);
			replyService.get(rno, function(data){
				console.log(data);
				$("#reply").val(data.reply); //.prop("readonly", true);
				$("#replyer").val(data.replyer); //.prop("readonly", true);
				$("#replyDate").val(replyService.displayTime(data.replyDate)).prop("readonly", true).closest("div").show();
				$("#replyModal").modal("show").data("rno", rno);
				$("#replyModal").find(".modal-footer button").show().eq(2).hide();
							
			});
		});
		
		// 댓글 수정 버튼 클릭
		$("#replyModal").find(".modal-footer button").eq(0).click(function(){
			if(!confirm("수정 하시겠습니까??")) return;
			
			const rno = $("#replyModal").data("rno");
			const reply = $("#reply").val();
			const r = {rno:rno, reply:reply};
			
			console.log(r);
			console.log(rno);
			replyService.modify(r, function(data){

				$("#replyModal").modal("hide");
				showList();
			});
		});

		// 댓글 삭제 버튼 클릭
		$("#replyModal").find(".modal-footer button").eq(1).click(function(){
			if(!confirm("삭제 하시겠습니까??")) return;
			
			const rno = $("#replyModal").data("rno");
			console.log(rno);
			replyService.remove(rno, function(data){

				$("#replyModal").modal("hide");
				showList(pageNum);
			});
		});
		
		
		function showReplyPage(replyCnt){
			var endNum = Math.ceil(pageNum / 10) * 10;
			var startNum = endNum - 9;

			var prev = startNum != 1;
			var next = false;

			if(endNum * 10 >= replyCnt){
				endNum = Math.ceil(replyCnt / 10);
			}
			if(endNum * 10 < replyCnt){
				next = true;
			}
			var str = `<ul class="pagination justify-content-end">`;
			if(prev){
				str += `<li class="page-item"><a class="page-link" href="\${startNum-1}"><i class="fas fa-angle-left"></i></a></li>`;
			}
			for(var i = startNum; i <= endNum; i++){
				var active = pageNum == i ? 'active' : '';
				str += `<li class="page-item \${active}"><a class="page-link" href="\${i}">\${i}</a></li>`;
			}
			if(next){
				str += `<li class="page-item"><a class="page-link" href="\${endNum+1}"><i class="fas fa-angle-right"></i></a></li>`;
			}

			str += `</ul>`;
			$(".reply-page").html(str);
		}
		
		// 댓글 페이지 버튼 클릭 이벤트
		$(".reply-page").on("click", "a", function(){
			event.preventDefault();
			pageNum = $(this).attr("href");
			showList(pageNum);
		})
		
		function showUploadedFile(arr){
			var str = "";
			var thumbStr = "";
			
			for(var i in arr){
				str += `<li class="list-group-item" data-uuid="\${arr[i].uuid}" data-origin="\${arr[i].origin}" data-image="\${arr[i].image}" data-path="\${arr[i].path}"><a href="/download\${arr[i].url}"><i class="far fa-file"></i> \${arr[i].origin}</a> 
						</li>`;
				if(arr[i].image) {
					thumbStr += `<li class="mt-2 col-sm-6 col-lg-4 text-center"><a href="" data-src='/display\${arr[i].url}'><img src='/display\${arr[i].thumb}' class="img-fluid img-thumbnail"></a> 
						</li>`;
				}
			}
			$(".uploadResult ul").append(str);
			$(".thumbResult ul").append(thumbStr);
		}
		
	
		$.getJSON('/board/getAttachs?bno=' + bno).done(function(data) {
			console.log(data)
			showUploadedFile(data)
		})
		
		// 섬네일 클릭시 원본 high light
		$(".thumbResult").on("click", "a", function(){
			event.preventDefault();
			console.log($(this).data("src"));
			$("#boardModal").find(".modal-body").html(
					$("<div>").addClass("text-center").html(
							$("<img>").attr("src", $(this).data("src")).addClass("img-fluid")
					)
			).end().modal("show");
		});
		
		
		
		// 
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
                    <div class="col-12 col-lg-9 mx-auto">
	                    <!-- Page Heading -->
	                    <h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>
	                    <div class="card shadow mb-4">
	                        <div class="card-header py-3">
	                            <h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
	                        </div>
	                        <div class="card-body">
	                        	<form method="post">
	                        	<div class="form-group">
									<label for="bno">Bno</label>
									<input class="form-control" id="bno" name="bno" value="${board.bno}" readonly>
								</div>
	                        	<div class="form-group">
									<label for="title">Title</label>
									<input class="form-control" id="title" name="title" value="${board.title}" readonly>
								</div>
	                        	<div class="form-group">
									<label for="boardContent">Content</label>
									<textarea class="form-control" id="boardContent" name="content" rows="10" readonly>${board.content}</textarea>
								</div>
	                        	<div class="form-group">
									<label for="writer">Writer</label>
									<input class="form-control" id="writer" name="writer" value="${board.writer}" readonly>
								</div>
								<sec:authorize access="authenticated">
								<sec:authentication property="principal.member" var="member"/>
								<c:if test="${board.writer == member.id }">
								<a class="btn btn-primary" href="modify${criteria.link}&bno=${board.bno}">Modify</a>
								</c:if>
								</sec:authorize>
								<a class="btn btn-info" href="list${criteria.link}">List</a>
								</form>
								</div>
	                        </div>
	                        <div class="card shadow mb-4">
		                        <div class="card-header py-3">
		                            <h6 class="m-0 font-weight-bold text-primary">File Attach</h6>
		                        </div>
		                        <div class="card-body">
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
	                    <div class="card shadow mb-4">
	                        <div class="card-header py-3">
	                            <h6 class="m-0 font-weight-bold text-primary float-left">Reply</h6>
	                            <sec:authorize access="authenticated">
	                            <button type="button" class="btn btn-primary float-right btn-sm" id="addReplyBtn">New Reply</button>
	                            </sec:authorize>
	                        </div>
	                        <div class="card-body py-0">
	                        	<ul class="chat list-group list-group-flush">

								</ul>
	                        </div>
							<div class="card-footer reply-page">Footer</div>
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

</body>

</html>