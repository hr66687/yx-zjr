package com.cn;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.cn.dao.UserMapper;
import com.cn.entity.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/26
 */


@SpringBootTest
public class TestPoi {



    @Resource
    UserMapper userMapper;


    @Test
    public void TestPoi() {

        // 创建文档
        Workbook workbook = new HSSFWorkbook();

        //创建一个工作表   参数:工作表表明  默认:sheet1,sheet2....
        Sheet sheet = workbook.createSheet("用户信息");

        //设置列宽  参数:列索引,列宽  单位 1/256
        sheet.setColumnWidth(11, 20 * 256);

        //创建合并单元格对象  参数:int firstRow(开始行), int lastRow(结束行), int firstCol(开始单元格), int lastCol(结束单元格)
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 11);

        //合并单元格
        sheet.addMergedRegion(cellRangeAddress);

        //创建字体对象
        Font font = workbook.createFont();

        font.setFontName("微软雅黑"); //字体
        font.setBold(true); //加粗
        font.setColor(IndexedColors.GREEN.getIndex()); //颜色
        font.setFontHeightInPoints((short)15);  //字号
        //font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线


        //创建单元格样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);  //文字居中
        cellStyle1.setFont(font); //设置字体样式


        // 创建标题行
        Row row1 = sheet.createRow(0);
        // 创建标题单元格
        Cell c = row1.createCell(0);
        //设置单元格样式
        c.setCellStyle(cellStyle1);
        //单元格设置数据
        c.setCellValue("用户信息表");


        // 目录行
        String [] titles = {"编号","头像","名字","密码","简介","学分","状态","手机号","注册时间","城市","性别"};
        // 创建一行   参数:行下标(从0开始)
        Row row = sheet.createRow(1);
        //设置行高  参数:行高  单位 1/20
        row.setHeight((short) (20 * 20));

        for (int i = 0; i < titles.length; i++) {

            //创建单元格
            Cell cell = row.createCell(i);

            //设置数据
            cell.setCellValue(titles[i]);
        }



        //创建日期样式对象
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd");

        //创建单元格样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        //将日期样式交给单元格样式对象
        cellStyle.setDataFormat(format);

        // 处理数据
        List<User> users = userMapper.queryAllExport();
        for (int i = 0; i <users.size() ; i++) {

            //创建一行
            Row rows = sheet.createRow(i+2);

            //创建单元格  设置数据
            rows.createCell(0).setCellValue(users.get(i).getId());
            rows.createCell(1).setCellValue(users.get(i).getHead());
            rows.createCell(2).setCellValue(users.get(i).getUsername());
            rows.createCell(3).setCellValue(users.get(i).getBrief());
            rows.createCell(4).setCellValue(users.get(i).getScroe());
            rows.createCell(5).setCellValue(users.get(i).getPassword());
            rows.createCell(6).setCellValue(users.get(i).getState());
            rows.createCell(7).setCellValue(users.get(i).getPhone());
            rows.createCell(8).setCellValue(users.get(i).getDate());
            rows.createCell(9).setCellValue(users.get(i).getCity());
            rows.createCell(10).setCellValue(users.get(i).getSex());


        }







        // 导出Excel文档

        try {
            workbook.write(new FileOutputStream(new File("D://用户信息.xls")));
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @Test
    public void o() {
        List<User> users = userMapper.queryAllExport();
        users.forEach(user -> System.out.println(user));

    }




    @Test
    public void Poiexport(){

        List<User> users = userMapper.queryAllExport();
        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户信息表", "用户");

        //导出工具   参数:导出的参数,对应的实体类,导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,User.class, users);

        try {
            //导出
            workbook.write(new FileOutputStream(new File("E:\\用户信息.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    @Test
    public void testDownLoad(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "";  //存储空间名
        String objectName = "照片.jpg";  //文件名
        String localFile="D:\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }



}
