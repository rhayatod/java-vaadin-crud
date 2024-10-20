package com.hayato.vaadin.backend.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {
    @Value("${dir.path-input}")
    private String pathInput;

    @Value("${dir.path-output}")
    private String pathOutput;

    public JasperReportService() {
    }

    public void createPdfFile(String url, Map<String, Object> params, Connection conn, String fileName)
            throws JRException, SQLException {
        try{
//            System.out.println("start generating "+ fileName);
            long startTime = System.nanoTime();
            JasperPrint jasperPrint = JasperFillManager.fillReport(url, params, conn);
            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
            JRPdfExporter exporter = new JRPdfExporter();
            if ( (boolean) params.get("isClientStatement")){
                String notsusUrl = new ClassPathResource("templates/jasper/ClientStatement/"+ "NotasiKhusus" + ".jasper").getURL().getPath();
//                String notsusUrl = new ClassPathResource("templates/jasper/ClientStatement/"+ "NotasiKhusus" + ".jasper").getURL().getPath();
//                JasperPrint notsusPrint = JasperFillManager.fillReport(notsusUrl, params, conn);
                List jprintlist = new ArrayList();
                jprintlist.add(jasperPrint);
//                jprintlist.add(notsusPrint);
                exporter.setExporterInput(SimpleExporterInput.getInstance(jprintlist));
            }

            String filePath = pathOutput + fileName + ".pdf";
            exporter.setConfiguration(config);
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filePath));

            exporter.exportReport();
            long endTime   = System.nanoTime();
            double totalTime = (endTime - startTime)/1000000000;
            System.out.println("done generating "+ fileName + " in " + totalTime + " seconds");

        } catch (Exception e){
            System.out.println("error generating sid " + params.get("sid"));
            System.out.println(e.getMessage());
        }
    }
}
