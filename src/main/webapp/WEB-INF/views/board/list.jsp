<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<jsp:include page="../includes/head.jsp" />
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
                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 mt-2 font-weight-bold text-primary float-left">Board List</h6>
                            <a href="register${page.cri.link}" class="btn btn-primary btn-sm float-right ">register</a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일</th>
                                            <th>수정일</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="b">
                                    	<tr>
                                    		<td>${b.bno}</td>
                                    		<td><a href="get${page.cri.link}&bno=${b.bno}">${b.title}</a>[${b.replyCnt}]</td>
                                    		<td>${b.writer}</td>
                                    		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${b.regdate}"/></td>
                                    		<td><fmt:formatDate value="${b.updateDate}"/></td>
                                    	</tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="">
                            <form action="list" class="form-inline justify-content-end my-3"" id="searchForm">
                            	<div class="form-check-inline">
								  <label class="form-check-label">
								    <input type="checkbox" class="form-check-input" name="type" value="T" ${fn:contains(page.cri.type, 'T') ? 'checked' : ''}>제목
								  </label>
								</div>
								<div class="form-check-inline">
								  <label class="form-check-label">
								    <input type="checkbox" class="form-check-input" name="type" value="C" ${fn:contains(page.cri.type, 'C') ? 'checked' : ''}>내용
								  </label>
								</div>
								<div class="form-check-inline">
								  <label class="form-check-label">
								    <input type="checkbox" class="form-check-input" name="type" value="W" ${fn:contains(page.cri.type, 'W') ? 'checked' : ''}>작성자
								  </label>
								</div>
								<div class="input-group">
								  <input type="text" class="form-control" placeholder="Search" name="keyword" value="${page.cri.keyword}">
								  <div class="input-group-append">
								    <button class="btn btn-success" type="submit">Go</button>
								  </div>
								</div>
								<input type="hidden" name="amount" value="${page.cri.amount}">
								</form>
                            </div>
                            <div class="">
                            	<ul class="pagination justify-content-end">
								 	<li class="page-item ${page.prevs ? '' : 'disabled'}"><a class="page-link" href="${page.cri.pageLink}&pageNum=${page.startPage-1}"><i class="fas fa-angle-double-left"></i></a></li>
								 	<li class="page-item ${page.prev ? '' : 'disabled'}"><a class="page-link" href="${page.cri.pageLink}&pageNum=${page.cri.pageNum-1}"><i class="fas fa-angle-left"></i></a></li>
								 	<c:forEach begin="${page.startPage}" end="${page.endPage}" var="i">
								 	<li class="page-item ${page.cri.pageNum == i ? 'active' : ''}"><a class="page-link" href="${page.cri.pageLink }&pageNum=${i}">${i}</a></li>
								 		
								 	</c:forEach>
								 	<li class="page-item ${page.next ? '' : 'disabled'}"><a class="page-link" href="${page.cri.pageLink}&pageNum=${page.cri.pageNum+1}"><i class="fas fa-angle-right"></i></a></li>
								 	<li class="page-item ${page.nexts ? '' : 'disabled'}"><a class="page-link" href="${page.cri.pageLink}&pageNum=${page.endPage+1}"><i class="fas fa-angle-double-right"></i></a></li>
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
	<script>
	$(function() {
		const result = '${result}';
		console.log(result);
		
		chechModal(result);
		history.replaceState({}, null, null);
		
		function chechModal(result){
			console.log(history.state);
			
			if(result === '' || history.state){
				return;
			}
			
			let msg = result === 'success' ? '정상적으로 등록되었습니다' : result+"번 게시글이 등록되었습니다.";
			
			$("#boardModal")
				.find(".modal-body").text(msg) 
			.end().modal("show")
		}
		
		$("#searchForm button").click(function(e){
			if(!$("#searchForm :checkbox:checked").length){ // 0개 일 때
				alert("검색 종류를 선택하세요");
				return false;
			}
			if(!$("#searchForm :text").val().trim()){ // 뭔가가 입력된 내용이 없을 때
				alert("키워드를 입력하세요");
				return false;
			}
		});
		
	});
	
	</script>
</body>

</html>