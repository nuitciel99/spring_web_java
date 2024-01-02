console.log("Reply Module...");

// var f = function(){};
// {}
var replyService = (function(){
	
	function add(reply, success, error){
        console.log("reply add");
        $.post({

            url:'/replies',
            // type:'post',
            data:JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8',
            success,
            error
        })
	
	}

    function getList(param, success, error){

        const bno = param.bno;
        let page;
        if(!param || param.page < 0){
        	page = 1;
        }
        else{
        	page = param.page;
        }
        const url = `/replies/pages/${bno}/${page}`;
        
        console.log(url);
        console.log("reply list");
    
        $.getJSON(url).done(function(a){
        	success(a.replyCnt, a.list);
        }).fail(error);
    }

    function remove(rno, success, error){
        $.ajax("/replies/"+rno, {type:'delete'}).done(success).fail(error);
        console.log("reply remove");
    }

    function modify(reply, success, error){
        console.log("reply modify");
        $.ajax("/replies/"+reply.rno, {
            type:'put',
            data:JSON.stringify(reply),
            contentType: 'application/json; charset=utf-8'
        }).done(success).fail(error);
    }

    function get(rno, success, error){
        console.log("reply get");
        $.getJSON("/replies/"+rno).done(success).fail(error);
    }

    function displayTime(timeValue){
        var gap = new Date().getTime() - timeValue;

        if(gap < 1000 * 60 * 60 * 24){
            return moment(timeValue).format('HH:mm:ss');
        }
        else{
            return moment(timeValue).format('YYYY/MM/DD');
        }
    }

	return {add, getList, remove, modify, get, displayTime};
})();

 
