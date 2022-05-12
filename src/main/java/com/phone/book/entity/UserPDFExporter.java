package com.phone.book.entity;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
 
 
public class UserPDFExporter {
    private List<User> listUsers;
     
    public UserPDFExporter(List<User> listUsers) {
        this.listUsers = listUsers;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(4);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("id", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("email", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("phoneNumber", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("passCode", font));
        table.addCell(cell);  
        
        cell.setPhrase(new Phrase("countryCode", font));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("created", font));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("updated", font));
        table.addCell(cell); 
    }
     
    private void writeTableData(PdfPTable table) {
        for (User user : listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getName());
            table.addCell(user.getPhoneNumber());
            table.addCell(user.getPassCode());
            table.addCell(user.getCountryCode());
            table.addCell(user.getCreated().toGMTString());
            table.addCell(user.getUpdated().toGMTString());
            
            
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("List of Users", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 5.0f, 3.0f, 3.5f, 2.5f, 2.5f, 4.0f, 4.0f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}