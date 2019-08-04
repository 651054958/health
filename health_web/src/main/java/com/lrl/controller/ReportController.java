package com.lrl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.constant.MessageConstant;
import com.lrl.entity.Result;
import com.lrl.service.MemberService;
import com.lrl.service.OrderService;
import com.lrl.service.PackageService;
import com.lrl.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/31 10:53
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @Reference
    private ReportService reportService;

    @Reference
    private PackageService packageService;

    @Reference
    private OrderService orderService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try {
            Map<String,Object> map = memberService.getMemberReport();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("/getCustomReport")
    public Result getCustomReport(@RequestBody String[] customDay){
        try {
            System.out.println(customDay);
            Map<String,Object> map = memberService.getCustomReport(customDay);
            return new Result(true, MessageConstant.GET_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_FAILED);
        }
    }

    @RequestMapping("/getPackageReport")
    public Result getPackageReport(){
        try {
            Map<String,Object> map = packageService.getPackageReport();
            return new Result(true,MessageConstant.GET_PACKAGE_REPORT_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_PACKAGE_REPORT_FAILED);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() throws Exception {
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            List<Map> hotPackage = orderService.findHotPackage();
            map.put("hotPackage",hotPackage);
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping(value = "/exportBusinessReport",produces = "application/vnd.ms-excel")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //todo :POI
        Map<String, Object> map = reportService.getBusinessReportData();
        List<Map> hotPackage = orderService.findHotPackage();
        String templatePath = request.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        String filename = "运营统计数据.xlsx";
        try {
            // filename.getBytes() UTF-8字符打散，字节数组，ISO-8859-1  latin-1就能支持
            filename = new String(filename.getBytes(),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition","attachment;filename=" + filename);
        // 设置http的内容体的格式
        response.setContentType("application/vnd.ms-excel");

        try (
                XSSFWorkbook wk = new XSSFWorkbook(templatePath);
             OutputStream os = response.getOutputStream();
             ){
            String reportDate = (String) map.get("reportDate");
            Integer todayNewMember = (Integer) map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");

            XSSFSheet sht = wk.getSheetAt(0);
            //日期
            sht.getRow(2).getCell(5).setCellValue(reportDate);
            //新增会员数
            sht.getRow(4).getCell(5).setCellValue(todayNewMember);
            //总会员数
            sht.getRow(4).getCell(7).setCellValue(totalMember);

            //本周新增会员数
            sht.getRow(5).getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员数
            sht.getRow(5).getCell(7).setCellValue(thisMonthNewMember);

            //今日预约数
            sht.getRow(7).getCell(5).setCellValue(todayOrderNumber);
            //今日到诊数
            sht.getRow(7).getCell(7).setCellValue(thisWeekOrderNumber);

            //本周预约数
            sht.getRow(8).getCell(5).setCellValue(thisMonthOrderNumber);
            //本周到诊数
            sht.getRow(8).getCell(7).setCellValue(todayVisitsNumber);

            //本月预约数
            sht.getRow(9).getCell(5).setCellValue(thisWeekVisitsNumber);
            //本月到诊数
            sht.getRow(9).getCell(7).setCellValue(thisMonthVisitsNumber);

            //设置hotpackage
            int rowCount = 12;
            if(null != hotPackage && hotPackage.size() > 0){
                XSSFRow row = null;
                for (Map<String, Object> pkg : hotPackage) {
                    row = sht.getRow(rowCount);
                    row.getCell(4).setCellValue((String)pkg.get("name"));
                    row.getCell(5).setCellValue(pkg.get("package_count").toString());
                    row.getCell(6).setCellValue(((BigDecimal)pkg.get("proportion")).doubleValue());
                    row.getCell(7).setCellValue((String)pkg.get("remark"));
                    rowCount++;
                }
            }
            //输出
            wk.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}