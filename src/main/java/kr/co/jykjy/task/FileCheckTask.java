package kr.co.jykjy.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.jykjy.controller.UploadController;
import kr.co.jykjy.domain.AttachDTO;
import kr.co.jykjy.domain.AttachVo;
import kr.co.jykjy.mapper.AttachMapper;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class FileCheckTask {
	@Autowired
	private AttachMapper mapper;
//	@Scheduled(cron="0 0 2 * * *") // 留ㅼ썡 留ㅼ씪 �깉踰쎈몢�떆
//	@Scheduled(cron="0/5 * * * * *") here
	public void check(){
//		log.info("done... :: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		// 1. �삤�뒛 �궇吏� yyyy/MM/dd - 1  ex) 2023/10/24
		// 1-1. �궇吏� �뤃�뜑 �깘�깋
		
//		File file = new File(UploadController.UPLOAD_PATH, new SimpleDateFormat("yyyy/MM/dd").format(new Date().getTime() - 1000 * 60 * 60 * 24));
//		log.info(file); // dir
//		here
//		List<File> folderFiles = new ArrayList<>(Arrays.asList(new File(UploadController.UPLOAD_PATH, new SimpleDateFormat("yyyy/MM/dd").format(new Date().getTime() - 1000 * 60 * 60 * 24)).listFiles()));
//		�뤃�뜑�궡�뿉_議댁옱�븯�뒗_�뙆�씪�뱾.forEach(log::info);
		
		// 2. db�뿉�꽌 議고쉶�빐�삩 �젙蹂�
		// >> dto 蹂��솚
//		List<File> db�뿉_議댁옱�븯�뒗_�뙆�씪�뱾 = mapper.getOldFiles().stream().map(vo->new AttachDTO(vo).toFile()).collect(Collectors.toList());
//		List<AttachVo> vos = mapper.getOldFiles();
		
//		here
//		List<AttachDTO> dtos = mapper.getOldFiles().stream().map(AttachDTO::new).collect(Collectors.toList());
//		List<File> dbFiles = new ArrayList<>();
//		
//		dtos.forEach(dto -> {
//			dbFiles.add(dto.toFile());
//			if(dto.isImage()){
//				dbFiles.add(dto.toFile(true));
//			}
//		});
		// 3. 1-2
		
//		db�뿉_議댁옱�븯�뒗_�뙆�씪�뱾.forEach(log::info);
		// 3-1. �뙆�씪�뿉 ���븳 �떎�젣 �궘�젣
		
//		folderFiles.removeAll(dbFiles); here
//		log.info(folderFiles.size());
//		
//		folderFiles.forEach(f->f.delete());
	}
	
}
