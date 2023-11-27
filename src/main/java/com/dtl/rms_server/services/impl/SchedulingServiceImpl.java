package com.dtl.rms_server.services.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dtl.rms_server.configuration.FilePropertiesConfig;
import com.dtl.rms_server.constants.ApplyInfoStatus;
import com.dtl.rms_server.constants.Common;
import com.dtl.rms_server.constants.ExcelHeader;
import com.dtl.rms_server.dtos.applyinfo.ApplyInfoExportDTO;
import com.dtl.rms_server.exceptions.RmsException;
import com.dtl.rms_server.models.ApplicantInformation;
import com.dtl.rms_server.models.HiringNews;
import com.dtl.rms_server.repositories.ApplicantInformationRepository;
import com.dtl.rms_server.repositories.HiringNewsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulingServiceImpl {
	private final HiringNewsRepository hiringNewsRepository;
	private final FilePropertiesConfig filePropertiesConfig;
	private final ApplicantInformationRepository applicantInformationRepository;

	@Scheduled(cron = "0 15 9 ? * *")
	public void excelSumUpAndDisableNews() {
		Workbook workbook = new XSSFWorkbook();
		List<HiringNews> allExpiredNews = getAllExpiredNews();
		if (allExpiredNews.isEmpty())
			return;
		allExpiredNews.forEach(n -> exportToExcelSheet(workbook, n.getTitle(),
				applicantInformationRepository.findAllByHiringNewsAndStatusIn(n,
						ApplyInfoStatus.map().keySet().stream().toList())));
		disableNews(allExpiredNews);

		try {

			// Copy file to the target location (Replacing existing file with
			// the same name)
			Path path = Paths.get(filePropertiesConfig.getUploadDirectory())
					.toAbsolutePath().normalize();
			Path targetLocation = path
					.resolve("%s.xlsx".formatted(LocalDate.now()));
			FileOutputStream fileOutputStream = new FileOutputStream(
					targetLocation.toString());
			workbook.write(fileOutputStream);
			log.info("New file directory: {}", targetLocation.toString());
		} catch (IOException ex) {
			throw new RmsException(
					"Không tồn tại đường dẫn: " + ex.getMessage());
		}
		log.info("Export file and disable news successfully!");
	}

	private List<HiringNews> getAllExpiredNews() {
		return hiringNewsRepository.findAllByDueDateAndIsActive(LocalDate.now(),
				Common.ACTIVE.getValue());
	}

	private List<HiringNews> disableNews(List<HiringNews> hiringNews) {
		hiringNews.forEach(t -> t.setIsActive(Common.LOCKED.getValue()));
		return hiringNewsRepository.saveAll(hiringNews);
	}

	private Object callGetter(ApplyInfoExportDTO dto, String fieldName) {
		PropertyDescriptor descriptor;
		try {
			descriptor = new PropertyDescriptor(fieldName, dto.getClass());
			return descriptor.getReadMethod().invoke(dto);
		} catch (IntrospectionException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			log.error("Excel export: Cannot get the value of field  '{}'",
					fieldName);
			return null;
		}
	}

	private void exportToExcelSheet(Workbook workbook, String sheetName,
			List<ApplicantInformation> applyInfos) {
		Sheet sheet = workbook.createSheet(sheetName);
		Font font = workbook.createFont();
		font.setBold(true);

		// headers
		List<String> headerNames = new ArrayList<>();
		List<String> fieldNames = new ArrayList<>();
		Field[] fields = ApplyInfoExportDTO.class.getDeclaredFields();
		for (Field field : fields) {
			headerNames.add(field.getAnnotation(ExcelHeader.class).name());
			fieldNames.add(field.getName());
		}
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headerNames.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headerNames.get(i));
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setFont(font);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		}

		if (applyInfos.isEmpty())
			return;

		List<ApplyInfoExportDTO> exportDTOs = applyInfos.stream()
				.map(ApplicantInformation::toApplyInfoExportDTO).toList();

		// data
		for (int i = 1; i <= applyInfos.size(); i++) {
			Row data = sheet.createRow(i);
			for (int j = 0; j < fieldNames.size(); j++) {
				Cell cell = data.createCell(j);
				cell.setCellValue(String.valueOf(
						callGetter(exportDTOs.get(i - 1), fieldNames.get(j))));
			}
		}

	}
}
