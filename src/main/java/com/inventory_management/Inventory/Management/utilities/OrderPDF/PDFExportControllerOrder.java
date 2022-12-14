package com.inventory_management.Inventory.Management.utilities.OrderPDF;


import com.inventory_management.Inventory.Management.dto.PlaceOrderSupplierStocksDTO;
import com.inventory_management.Inventory.Management.error.NotFoundException;
import com.inventory_management.Inventory.Management.repository.OrderRepository;
import com.inventory_management.Inventory.Management.service.OrderService;
import com.lowagie.text.DocumentException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@Controller
public class PDFExportControllerOrder {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private final PDFServiceOrder pdfService1;


    @GetMapping("/pdf/orderInvoice/generate/{orderId}")

    public void generateOrderPDF(HttpServletResponse response
            , @PathVariable Long orderId) throws IOException
            , DocumentException, NotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new NotFoundException("Order with this id does not exist");
        }
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Order_Invoice_" + currentDateTime + ".pdf";


        response.setHeader(headerKey, headerValue);


        List<PlaceOrderSupplierStocksDTO> placeOrderSupplierStocksDTOList = orderService.getByOrderId(orderId);

        PDFServiceOrder exporter = new PDFServiceOrder(placeOrderSupplierStocksDTOList);
        exporter.export(response);

    }
}
